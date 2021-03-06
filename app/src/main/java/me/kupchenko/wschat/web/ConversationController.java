package me.kupchenko.wschat.web;

import me.kupchenko.wschat.domain.ChatMessage;
import me.kupchenko.wschat.domain.Conversation;
import me.kupchenko.wschat.services.ConversationService;
import me.kupchenko.wschat.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/user/conversations", produces = "application/json", method = RequestMethod.POST)
    public Set<Conversation> getUserConversations(Principal principal) {
        return conversationService.getConversations(principal.getName());
    }

    @RequestMapping(value = "/conversation/messages/{conversationId}", produces = "application/json", method = RequestMethod.GET)
    public List<ChatMessage> getConversationMessages(@PathVariable("conversationId") String conversationId, Principal principal) {
        return messageService.getConversationMessages(principal.getName(), conversationId);
    }
}
