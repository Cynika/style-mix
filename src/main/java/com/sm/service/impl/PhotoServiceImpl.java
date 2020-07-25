package com.sm.service.impl;


import com.sm.dao.IPhotoDao;
import com.sm.dao.IUserDao;
import com.sm.model.Photo;
import com.sm.model.User;

import com.sm.service.IPhotoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("photoService")
public class PhotoServiceImpl implements IPhotoService {

    @Resource(name = "photoDao")
    private IPhotoDao photoDao;


    @Override
    public Photo findByid(int id) {
        return photoDao.findByid(id);
    }

    @Override
    public List<Photo> findByuserid(int userid) {
        return photoDao.findByuserid(userid);
    }

    @Override
    public int insertPhoto(String img, int userid, String desc) {
        return photoDao.insertPhoto(img, userid, desc);
    }

    @Override
    public int deletePhoto(int id) {
        return photoDao.deletePhoto(id);
    }

    @Override
    public List<Photo> queryAllPhoto() {
        return photoDao.queryAllPhoto();
    }
}
