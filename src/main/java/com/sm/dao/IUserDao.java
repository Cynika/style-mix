package com.sm.dao;


import com.sm.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface IUserDao {
    User findByNameAndPassword(@Param(value = "name") String name, @Param(value = "password") String password);

    int insertUser(User user);

    User findUserByName(String name);

    int get_npy_state(int id, int person);

    int set_npy_state(int id, int person, int state);

    int FreezeUserById(int id);

    int ActiveUserById(int id);

    List<User> queryAllUsers();

}
