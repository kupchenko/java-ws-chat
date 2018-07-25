package me.kupchenko.wschat.dao;

import me.kupchenko.wschat.domain.Conversation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ConversationDAO extends CrudRepository<Conversation, Long> {
    //List<Conversation> findAllByParticipantsUsername(String username);
}
