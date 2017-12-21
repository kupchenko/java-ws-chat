package edu.dmitrii.wschat.web;

import edu.dmitrii.wschat.domain.Conversation;
import edu.dmitrii.wschat.domain.User;
import edu.dmitrii.wschat.services.AuthenticationService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Log4j
@Controller
public class UserController {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/user/get", produces = "application/json")
    public User getUser() {
        User user = authenticationService.getUser("login", "pass");
        Optional<User> userOp = Optional.ofNullable(user);
        log.info("Selected user " + (userOp.isPresent() ? userOp.toString() : "EMPTY"));
        return user;
    }

    @RequestMapping(value = "/user/{username}/conversations", produces = "application/json")
    public List<Conversation> getUserConversations(@PathVariable("username") String username) {
        User user = authenticationService.getUser(username);
        Optional<User> userOp = Optional.ofNullable(user);
        log.info("Selected user has conversions:");
        userOp.ifPresent(user1 -> user1.getConversations().forEach(log::info));
        return user.getConversations();
    }


}
