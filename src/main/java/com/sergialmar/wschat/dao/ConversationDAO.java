package com.sergialmar.wschat.dao;

import com.sergialmar.wschat.domain.Conversation;
import org.springframework.data.repository.CrudRepository;

public interface ConversationDAO extends CrudRepository<Conversation, Long> {
}
