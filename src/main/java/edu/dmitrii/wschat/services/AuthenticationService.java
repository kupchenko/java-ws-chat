package edu.dmitrii.wschat.services;

import edu.dmitrii.wschat.dao.UserDAO;
import edu.dmitrii.wschat.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AuthenticationService {

    @Autowired
    private UserDAO userDAO;

    public User getUser(String username, String password) {
        return userDAO.findUserByUsernameAndPassword(username, password);
    }

    public User getUser(String username) {
        return userDAO.findUserByUsername(username);
    }
}
