package edu.dmitrii.wschat.dao;

import edu.dmitrii.wschat.domain.Conversation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ConversationDAO extends CrudRepository<Conversation, Long> {
    //List<Conversation> findAllByParticipantsUsername(String username);
}
