package edu.dmitrii.wschat.services;

import edu.dmitrii.wschat.dao.UserDAO;
import edu.dmitrii.wschat.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserDAO userDAO;

    public User getUser(String username, String password) {
        return userDAO.findUserByUsernameAndPassword(username, password);
    }

    public User getUser(String username) {
        Optional<User> userByUsername = userDAO.findUserByUsername(username);
        return userByUsername.orElseGet(User::new);
    }
}
