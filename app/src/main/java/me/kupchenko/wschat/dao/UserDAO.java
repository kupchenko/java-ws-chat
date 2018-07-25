package me.kupchenko.wschat.dao;


import me.kupchenko.wschat.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserDAO extends CrudRepository<User, Long> {
    User findUserByUsernameAndPassword(String username, String password);

    Optional<User> findUserByUsername(String username);
}
