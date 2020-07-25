package com.sm.dao.impl;

import com.sm.dao.IPhotoDao;
import com.sm.model.Photo;

import com.sm.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class PhotoDaoImpl implements IPhotoDao {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;


    @Override
    public Photo findByid(int id) {
        Photo photo;
        String sql = "SELECT * FROM img WHERE id=?";
        Object[] obj = new Object[]{id};
        RowMapper<Photo> rowMapper = new BeanPropertyRowMapper<>(Photo.class);
        try {
            photo = jdbcTemplate.queryForObject(sql, rowMapper, obj);
        } catch (Exception e) {
            return null;
        }
        return photo;
    }

    @Override
    public List<Photo> findByuserid(int userid) {
        String sql = "SELECT * FROM user where userid = ?";
        List<Photo> list = jdbcTemplate.query(sql, new RowMapper<Photo>() {
            @Override
            public Photo mapRow(ResultSet resultSet, int i) throws SQLException {
                Photo photo = new Photo();
                photo.setId(resultSet.getInt("id"));
                photo.setImg(resultSet.getString("img"));
                photo.setUserid(resultSet.getInt("userid"));
                photo.setDesc(resultSet.getString("userid"));

                return photo;
            }
        }, userid);
        return list;
    }

    @Override
    public int insertPhoto(String img, int userid, String desc) {
        int result = 0;
        String sql = "insert into img(img, userid, description) values(?,?,?)";

        try {
            result = jdbcTemplate.update(sql, img, userid, desc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int deletePhoto(int id) {
        int result = 0;
        String sql = "DELETE FROM img WHERE id = ?";

        try {
            result = jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Photo> queryAllPhoto() {
        String sql = "SELECT * FROM img";
        List<Photo> list = jdbcTemplate.query(sql, new RowMapper<Photo>() {
            @Override
            public Photo mapRow(ResultSet resultSet, int i) throws SQLException {
                Photo photo = new Photo();
                photo.setId(resultSet.getInt("id"));
                photo.setUserid(resultSet.getInt("userid"));
                photo.setImg(resultSet.getString("img"));
                photo.setDesc(resultSet.getString("description"));
                return photo;
            }
        });
        return list;
    }
}
