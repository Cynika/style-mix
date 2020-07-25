package com.sm.service;


import com.sm.model.Photo;

import java.util.List;


public interface IPhotoService {

    Photo findByid(int id);

    List<Photo> findByuserid(int userid);

    int insertPhoto(String img, int userid, String desc);

    int deletePhoto(int id);

    List<Photo> queryAllPhoto();
}
