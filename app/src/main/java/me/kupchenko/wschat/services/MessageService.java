package me.kupchenko.wschat.services;

import me.kupchenko.wschat.dao.ConversationDAO;
import me.kupchenko.wschat.dao.MessageDAO;
import me.kupchenko.wschat.dao.UserDAO;
import me.kupchenko.wschat.domain.ChatMessage;
import me.kupchenko.wschat.domain.Conversation;
import me.kupchenko.wschat.domain.Message;
import me.kupchenko.wschat.domain.User;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Log4j
@Service
public class MessageService {

    @Autowired
    private MessageDAO messageDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ConversationDAO conversationDAO;


    public Conversation sendMessage(ChatMessage chatMessage) {
        final Long conversationId = Long.valueOf(chatMessage.getConversation());
        Optional<User> user = userDAO.findUserByUsername(chatMessage.getUsername());
        if (user.isPresent()) {
            Optional<Conversation> userConversation = getUserConversation(user.get(), conversationId);
            if (userConversation.isPresent()) {
                Conversation conversation = userConversation.get();
                conversation.setLastActivity(new Timestamp(System.currentTimeMillis()));
                conversationDAO.save(conversation);
                Message message = new Message();
                message.setText(chatMessage.getMessage());
                message.setSender(user.get());
                message.setTimestamp(new Timestamp(System.currentTimeMillis()));
                message.setConversation(conversation);
                Message save = messageDAO.save(message);
                return Optional.ofNullable(save).isPresent() ? conversation : null;
            }
        }
        return null;
    }

    public List<ChatMessage> getConversationMessages(String username, String conversationIdStr) {
        Long conversationId = Long.valueOf(conversationIdStr);
        log.info("User in principal: [" + username + "], conversation in path: [" + conversationId + "]");
        List<Message> allByConversationId = messageDAO.getAllByConversationId(conversationId);
        return allByConversationId.stream()
                        .map(message -> getChatMessage(conversationId, message))
                        .collect(toList());
//        Optional<User> user = userDAO.findUserByUsername(username);
//        if (user.isPresent()) {
//            Optional<Conversation> userConversation = getUserConversation(user.get(), conversationId);
//            if (userConversation.isPresent()) {
//                List<Message> msgs = new ArrayList<>(userConversation.get().getMessages());
//                msgs.sort(Comparator.comparing(Message::getId));
//                return msgs.stream()
//                        .map(message -> getChatMessage(conversationId, message))
//                        .collect(toList());
//            }
//        }
        //return Collections.emptyList();
    }


    private ChatMessage getChatMessage(Long conversationId, Message message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setConversation(conversationId.toString());
        chatMessage.setUsername(message.getSender().getUsername());
        chatMessage.setMessage(message.getText());
        //log.info(chatMessage.getUsername() + " wrote message " + message.getText());
        return chatMessage;
    }


    private Optional<Conversation> getUserConversation(User user, Long conversationId) {
        return user.getConversations().stream().filter(conversation -> conversation.getId().equals(conversationId)).findAny();
    }
}
