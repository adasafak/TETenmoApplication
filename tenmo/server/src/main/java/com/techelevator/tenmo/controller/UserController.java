package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {

    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private UserDao userDao;

    @RequestMapping( path = "/Users", method = RequestMethod.GET)
    public List<User> getUsers(Principal principal){
        int userId= userDao.findIdByUsername(principal.getName());
        return userDao.findAll(userId);
    }





}
