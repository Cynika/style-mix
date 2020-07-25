package com.sm.service.impl;


import com.sm.service.IUserService;
import com.sm.dao.IUserDao;
import com.sm.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource(name = "userDao")
    private IUserDao userDao;

    @Override
    public User findByNameAndPassword(String name, String password) {
        return userDao.findByNameAndPassword(name, password);
    }

    @Override
    public int insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public User findUserByName(String name) {
        return userDao.findUserByName(name);
    }


    @Override
    public int get_npy_state(int id, int person) {
        return userDao.get_npy_state(id, person);
    }

    @Override
    public int set_npy_state(int id, int person, int state) {
        return userDao.set_npy_state(id, person, state);
    }

    @Override
    public int FreezeUser(int id) {
        return userDao.FreezeUserById(id);
    }


    @Override
    public int ActiveUser(int id) {
        return userDao.ActiveUserById(id);
    }

    @Override
    public List<User> queryAllUsers() {
        return userDao.queryAllUsers();
    }

}
