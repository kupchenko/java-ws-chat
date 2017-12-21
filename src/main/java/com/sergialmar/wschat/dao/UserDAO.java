package com.sergialmar.wschat.dao;


import com.sergialmar.wschat.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Long> {
    User findUserByUsernameAndPassword(String username, String password);
}
