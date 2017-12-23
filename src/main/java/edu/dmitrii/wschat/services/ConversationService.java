package edu.dmitrii.wschat.services;

import edu.dmitrii.wschat.dao.ConversationDAO;
import edu.dmitrii.wschat.dao.UserDAO;
import edu.dmitrii.wschat.domain.ChatMessage;
import edu.dmitrii.wschat.domain.Conversation;
import edu.dmitrii.wschat.domain.Message;
import edu.dmitrii.wschat.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


@Slf4j
@Service
public class ConversationService {

    @Autowired
    private ConversationDAO conversationDAO;

    @Autowired
    private UserDAO userDAO;

    public Set<Conversation> getConversations(String username) {
        Optional<User> user = userDAO.findUserByUsername(username);
        if (user.isPresent()) {
            Set<Conversation> conversations = user.get().getConversations();
            log.info("User [" + username + "] conversations:");
            conversations.forEach(e -> log.info("- " + e.getName()));
            return conversations;
        }
        return Collections.emptySet();
    }

    public boolean sendMessage(ChatMessage chatMessage) {
        final Long conversationId = Long.valueOf(chatMessage.getConversation());
        log.info("Sending message to [" + conversationId + "], sender [" + chatMessage.getUsername() + "]");

        Optional<User> user = userDAO.findUserByUsername(chatMessage.getUsername());
        if (user.isPresent()) {
            Set<Conversation> conversations = user.get().getConversations();
            Optional<Conversation> conversation = conversations.stream().filter(item -> Objects.equals(item.getId(), conversationId)).findAny();
            if (conversation.isPresent()) {
                Message message = new Message();
                message.setText(chatMessage.getMessage());
                message.setSender(user.get());
                message.setTimestamp(new Timestamp(System.currentTimeMillis()));
                message.setConversation(conversation.get());
                conversation.get().getMessages().add(message);
                Conversation save = conversationDAO.save(conversation.get());
                return Optional.ofNullable(save).isPresent();
            }
        }
        return false;
    }
}
