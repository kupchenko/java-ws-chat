package me.kupchenko.wschat.dao;

import me.kupchenko.wschat.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageDAO extends CrudRepository<Message, Long> {
    List<Message> getAllByConversationId(Long conversationId);


}
