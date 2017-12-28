package edu.dmitrii.wschat.dao;

import edu.dmitrii.wschat.domain.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageDAO extends CrudRepository<Message, Long> {
}
