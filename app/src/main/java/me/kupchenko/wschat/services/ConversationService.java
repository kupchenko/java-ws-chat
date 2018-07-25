package me.kupchenko.wschat.services;

import me.kupchenko.wschat.dao.ConversationDAO;
import me.kupchenko.wschat.dao.UserDAO;
import me.kupchenko.wschat.domain.Conversation;
import me.kupchenko.wschat.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service
public class ConversationService {

    @Autowired
    private ConversationDAO conversationDAO;

    @Autowired
    private UserDAO userDAO;

    public Set<Conversation> getConversations(String username) {
        Optional<User> user = userDAO.findUserByUsername(username);
        return user.map(User::getConversations).orElseGet(Collections::emptySet);
    }


    public Optional<Conversation> getConversation(Long conversationId) {
        return conversationDAO.findById(conversationId);
    }

}
