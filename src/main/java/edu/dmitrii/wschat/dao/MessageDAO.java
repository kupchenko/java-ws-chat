package edu.dmitrii.wschat.dao;

import edu.dmitrii.wschat.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageDAO extends CrudRepository<Message, Long> {
    List<Message> getAllByConversationId(Long conversationId);


}
