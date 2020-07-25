package com.sm.dao;


import com.sm.model.Photo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface IPhotoDao {
    Photo findByid(@Param(value = "id") int id);

    List<Photo> findByuserid(@Param(value = "userid") int userid);

    int insertPhoto(String img, int userid,String desc);

    int deletePhoto(int id);

    List<Photo> queryAllPhoto();

}
