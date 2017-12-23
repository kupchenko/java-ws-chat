package edu.dmitrii.wschat.web;

import edu.dmitrii.wschat.domain.ChatMessage;
import edu.dmitrii.wschat.domain.Conversation;
import edu.dmitrii.wschat.domain.SessionProfanity;
import edu.dmitrii.wschat.exception.TooMuchProfanityException;
import edu.dmitrii.wschat.services.ConversationService;
import edu.dmitrii.wschat.util.ProfanityChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Collection;

/**
 * Controller that handles WebSocket chat messages
 *
 * @author Sergi Almar
 */
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

        boolean isMessageSent = conversationService.sendMessage(message);
        if (isMessageSent) {
            simpMessagingTemplate.convertAndSend("/user/" + message.getConversation() + "/exchange/amq.direct/chat.message", message);
        }
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