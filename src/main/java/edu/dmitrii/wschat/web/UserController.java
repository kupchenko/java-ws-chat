package edu.dmitrii.wschat.web;

import edu.dmitrii.wschat.domain.ChatMessage;
import edu.dmitrii.wschat.domain.Conversation;
import edu.dmitrii.wschat.domain.Message;
import edu.dmitrii.wschat.domain.User;
import edu.dmitrii.wschat.services.AuthenticationService;
import edu.dmitrii.wschat.services.ConversationService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Log4j
@RestController
public class UserController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ConversationService conversationService;

    @RequestMapping(value = "/user/get", produces = "application/json")
    public User getUser() {
        User user = authenticationService.getUser("login", "pass");
        Optional<User> userOp = Optional.ofNullable(user);
        log.info("Selected user " + (userOp.isPresent() ? userOp.toString() : "EMPTY"));
        return user;
    }

    @RequestMapping(value = "/user/{username}/conversations", produces = "application/json", method = RequestMethod.POST)
    public Set<Conversation> getUserConversations(@PathVariable("username") String username, Principal principal) {
        log.info("User in principal:[" + principal.getName() + "], user in path: [" + username + "]");
        Set<Conversation> conversations = conversationService.getConversations(username);
        log.info("Selected user has conversions:");
        conversations.forEach(log::info);
        return conversations;
    }

    @RequestMapping(value = "/conversation/messages/{conversationId}", produces = "application/json", method = RequestMethod.GET)
    public List<ChatMessage> getConversationMessages(@PathVariable("conversationId") String conversationId, Principal principal) {
        log.info("User in principal:[" + principal.getName() + "], conversation in path: [" + conversationId + "]");
        //TODO add check if it is user conversation else throw AccessDenied
        Optional<Conversation> conversation = conversationService.getConversation(conversationId);
        if (conversation.isPresent()) {
            List<Message> msgs = new ArrayList<>(conversation.get().getMessages());
            msgs.sort(Comparator.comparing(Message::getId));
            log.info("b----Total messages in conversation " + conversationId + ": " + msgs.size());
            List<ChatMessage> messages = msgs.stream()
                    .map(message -> getChatMessage(conversationId, message))
                    .collect(toList());
            log.info("a----Total messages in conversation " + conversationId + ": " + messages.size());
            return messages;
        }
        return Collections.emptyList();
    }

    private ChatMessage getChatMessage(String conversationId, Message message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setConversation(conversationId);
        chatMessage.setUsername(message.getSender().getUsername());
        chatMessage.setMessage(message.getText());
        return chatMessage;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage() {
        return "/registration.html";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register() {
        log.info("Registering user");
        return "/chat.html";
    }

}

