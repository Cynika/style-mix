package com.sm.controller;


import com.alibaba.fastjson.JSONObject;
import com.sm.model.Photo;
import com.sm.service.IPhotoService;
import com.sm.service.IUserService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import static com.Util.JsonArrayBack.backArray;
import static com.Util.JsonBack.back;


@Controller
public class PhotoController {
    @Resource(name = "userService")
    IUserService userService;

    @Resource(name = "photoService")
    IPhotoService photoService;

    //分享图片
    @RequestMapping("/sharePhoto")
    @ResponseBody
    public JSONObject share_photo(HttpServletRequest request, @RequestParam("desc") String desc, HttpSession httpSession) throws IOException {
        int state = (int) httpSession.getAttribute("state");
        if (state != 1) {
            return back("-1");
        }
        int id = (int) httpSession.getAttribute("id");
        ResourceBundle resource = ResourceBundle.getBundle("config");
        String share_images = resource.getString("share_images");
        String generated_images = resource.getString("generated_images");
        String pos = resource.getString("pos");
        String share_pos = resource.getString("share_pos");
        File src_file = new File(pos + generated_images + id + "result" + ".png");
        String des_img = id + "_" + System.currentTimeMillis() + ".png";
        File des_file = new File(share_pos + share_images + des_img);
        System.out.println(des_img);
        FileUtils.copyFile(src_file, des_file);
        photoService.insertPhoto(des_img, id, desc);
        return back("1");
    }

    //查询所有图片
    @RequestMapping("/queryAllPhoto")
    @ResponseBody
    public JSONObject query_AllPhoto(HttpServletRequest request, HttpSession httpSession) throws IOException {
        List<Photo> result = photoService.queryAllPhoto();
        return backArray(result);
    }

    //删除图片
    @RequestMapping("/deletePhoto")
    @ResponseBody
    public JSONObject delete_Photo(HttpServletRequest request, @RequestParam("id") int id, HttpSession httpSession) throws IOException {
        int role = (int) httpSession.getAttribute("role");
        if (role != 1) {
            return back("-1");
        }
        int result = photoService.deletePhoto(id);
        if (result > 0)
            return back("1");
        else
            return back("0");
    }

    //显示图片
    @RequestMapping("/pimg")
    @ResponseBody
    public JSONObject pimg(HttpServletRequest request, @RequestParam("photo") int imgid, HttpSession httpSession) throws IOException {

        ResourceBundle resource = ResourceBundle.getBundle("config");
        String share_images = resource.getString("share_images");
        Photo photo = photoService.findByid(imgid);
        String img = photo.getImg();
        return back(share_images + img);
    }

}
