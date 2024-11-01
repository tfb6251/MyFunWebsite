package com.ufund.api.ufundapi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.UserDAO;


import java.io.IOException;
/*
 * User Service class for data transfer to and from the model/persistance.
 * 
 */



@RestController
public class UserService  {
    private UserDAO UserDao;

    public UserService(UserDAO UserDao) {
        this.UserDao = UserDao;
    }
    User[] getUsers() throws IOException {
        return UserDao.getUsers();
    }
    User getUser(int id) throws IOException {
        return UserDao.getUser(id);
    }
    User[] findUsers(String name) throws IOException {
        return UserDao.findUsers(name);
    }
    User createUser(User user) throws IOException {
        return UserDao.createUser(user);
    }
    User updateUser(User user)throws IOException {
        return UserDao.updateUser(user);
    }
    boolean deleteUser(int id) throws IOException {
        return UserDao.deleteUser(id);
    }  
}
