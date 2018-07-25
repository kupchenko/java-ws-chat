package me.kupchenko.wschat.web;

import me.kupchenko.wschat.domain.ChatMessage;
import me.kupchenko.wschat.domain.Conversation;
import me.kupchenko.wschat.domain.SessionProfanity;
import me.kupchenko.wschat.exception.TooMuchProfanityException;
import me.kupchenko.wschat.services.ConversationService;
import me.kupchenko.wschat.services.MessageService;
import me.kupchenko.wschat.util.ProfanityChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Controller
public class ChatController {

    @Autowired
    private ProfanityChecker profanityFilter;

    @Autowired
    private SessionProfanity profanity;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MessageService messageService;

    @SubscribeMapping("/chat.conversations")
    public Collection<Conversation> retrieveParticipants(Principal principal) {
        log.info("Selecting all conversation by user: " + principal.getName());
        return conversationService.getConversations(principal.getName());
    }

    @MessageMapping("/chat.message")
    public ChatMessage filterMessage(@Payload ChatMessage message, Principal principal) {
        checkProfanityAndSanitize(message);
        message.setUsername(principal.getName());
        return message;
    }

    @MessageMapping("/chat.private")
    public void filterPrivateMessage(@Payload ChatMessage message, Principal principal) {
        checkProfanityAndSanitize(message);
        message.setUsername(principal.getName());

        Conversation conversation = messageService.sendMessage(message);
        log.info("Message sent " + (Optional.ofNullable(conversation).isPresent() ? "successfully" : "failed"));
        conversation.getParticipants().stream().filter(user -> !user.getUsername().equals(principal.getName())).forEach(user -> {
            simpMessagingTemplate.convertAndSend("/user/" + user.getUsername() + "/exchange/amq.direct/chat.message", message);
        });
    }

    private void checkProfanityAndSanitize(ChatMessage message) {
        long profanityLevel = profanityFilter.getMessageProfanity(message.getMessage());
        profanity.increment(profanityLevel);
        message.setMessage(profanityFilter.filter(message.getMessage()));
    }

    @MessageExceptionHandler
    @SendToUser(value = "/exchange/amq.direct/errors", broadcast = false)
    public String handleProfanity(TooMuchProfanityException e) {
        return e.getMessage();
    }
}