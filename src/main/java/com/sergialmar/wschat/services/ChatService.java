package com.sergialmar.wschat.services;

import com.sergialmar.wschat.dao.ConversationDAO;
import com.sergialmar.wschat.domain.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ConversationDAO conversationDAO;

    public List<Conversation> getConversations(String username) {
        return conversationDAO.findAll(username);
    }
}
