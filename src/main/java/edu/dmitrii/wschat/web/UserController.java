package edu.dmitrii.wschat.web;

import edu.dmitrii.wschat.domain.Conversation;
import edu.dmitrii.wschat.domain.User;
import edu.dmitrii.wschat.services.AuthenticationService;
import edu.dmitrii.wschat.services.ConversationService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

@Log4j
@Controller
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

