package edu.dmitrii.wschat.services;

import edu.dmitrii.wschat.dao.ConversationDAO;
import edu.dmitrii.wschat.dao.MessageDAO;
import edu.dmitrii.wschat.dao.UserDAO;
import edu.dmitrii.wschat.domain.ChatMessage;
import edu.dmitrii.wschat.domain.Conversation;
import edu.dmitrii.wschat.domain.Message;
import edu.dmitrii.wschat.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

import static java.util.stream.Collectors.toList;


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
