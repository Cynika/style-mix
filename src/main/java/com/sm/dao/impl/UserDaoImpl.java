package com.sm.dao.impl;

import com.sm.dao.IUserDao;
import com.sm.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements IUserDao {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public User findByNameAndPassword(String name, String password) {
        User user;
        String sql = "SELECT * FROM user WHERE name=? AND password=?";
        Object[] obj = new Object[]{name, password};
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        try {
            user = jdbcTemplate.queryForObject(sql, rowMapper, obj);
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    @Override
    public int insertUser(User user) {
        int result = 0;
        String sql = "insert into user(name, password, email, role) values(?,?,?,false)";
        Object[] obj = new Object[]{
                user.getName(),
                user.getPassword(),
                user.getEmail(),
        };
        try {
            result = jdbcTemplate.update(sql, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public User findUserByName(String name) {
        User user;
        String sql = "SELECT * FROM user WHERE name=?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        try {
            user = jdbcTemplate.queryForObject(sql, rowMapper, name);
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    @Override
    public int get_npy_state(int id, int person) {
        User user;
        String sql = "SELECT * FROM user WHERE id=?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        user = jdbcTemplate.queryForObject(sql, rowMapper, id);
        int result = 0;
        if (person == 1)
            result = user.getP1_npy_state();
        if (person == 2)
            result = user.getP2_npy_state();
        return result;
    }

    @Override
    public int set_npy_state(int id, int person, int state) {
        String sql1 = "UPDATE user SET p1_npy_state = ? WHERE id = ?";
        String sql2 = "UPDATE user SET p2_npy_state = ? WHERE id = ?";
        int result = 0;
        if (person == 1) {
            result = jdbcTemplate.update(sql1, state, id);
        }
        if (person == 2)
            result = jdbcTemplate.update(sql2, state, id);
        return result;
    }

    @Override
    public int FreezeUserById(int id) {
        int result = 0;
        String sql = "UPDATE user SET state = 0 WHERE id = ?";
        result = jdbcTemplate.update(sql, id);
        return result;
    }


    @Override
    public int ActiveUserById(int id) {
        int result = 0;
        String sql = "UPDATE user SET state = 1 WHERE id = ?";
        result = jdbcTemplate.update(sql, id);
        return result;
    }

    @Override
    public List<User> queryAllUsers() {
        String sql = "SELECT * FROM user";
        List<User> list = jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setState(resultSet.getInt("state"));
                user.setP1_npy_state(resultSet.getInt("p1_npy_state"));
                user.setP2_npy_state(resultSet.getInt("p2_npy_state"));
                user.setRole(resultSet.getInt("role"));
                return user;
            }
        });
        return list;
    }
}
