package com.sm.service;


import com.sm.model.User;

import java.util.List;


public interface IUserService {
    User findByNameAndPassword(String name, String password);

    int insertUser(User user);

    User findUserByName(String name);

    int get_npy_state(int id, int person);

    int set_npy_state(int id, int person, int state);

    int FreezeUser(int id);

    int ActiveUser(int id);

    List<User> queryAllUsers();


}
