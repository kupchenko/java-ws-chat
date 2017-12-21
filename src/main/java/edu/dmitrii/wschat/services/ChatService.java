package edu.dmitrii.wschat.services;

import edu.dmitrii.wschat.dao.ConversationDAO;
import edu.dmitrii.wschat.domain.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ConversationDAO conversationDAO;

}
