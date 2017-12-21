package com.sergialmar.wschat.services;

import com.sergialmar.wschat.dao.UserDAO;
import com.sergialmar.wschat.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AuthenticationService {

    @Autowired
    private UserDAO userDAO;

    private User getUser(String username, String password) {
        return userDAO.findUser(username, password);
    }
}
