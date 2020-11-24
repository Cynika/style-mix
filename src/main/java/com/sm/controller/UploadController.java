package com.sm.controller;

import com.alibaba.fastjson.JSONObject;
import com.cmd.Exec;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.Util.JsonBack.back;

@Controller
public class UploadController {

    //头像上传
    @RequestMapping("/upload")
    @ResponseBody
    public JSONObject FileUpload(HttpServletRequest request, @RequestParam("file") CommonsMultipartFile file, @RequestParam("who") int who, HttpSession httpSession) throws IOException {
        int id = (int) httpSession.getAttribute("id");
        System.out.println(id);
        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(
                    file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            ResourceBundle resource = ResourceBundle.getBundle("config");
            String pos = resource.getString("pos");
            String raw_images = resource.getString("raw_images");
            httpSession.setAttribute(who + "_type", type);
            String filename = id + "_" + who + type;// 取当前时间戳作为文件名
            String path = pos + raw_images + filename;// 存放位置
            File destFile = new File(path);
            //保存到本地
            try {
                // FileUtils.copyInputStreamToFile()这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);// 复制临时文件到指定目录下
                return back("1");
            } catch (IOException e) {
                e.printStackTrace();
                return back("0");
            }
        }
        return back("-1");

    }

    //校准脸部任务
    @RequestMapping("/aligned")
    @ResponseBody
    public JSONObject aligned(HttpServletRequest request, @RequestParam("who") int who, HttpSession httpSession) throws IOException {
        if (who != 1 && who != 2) {
            return back("-1");
        }
        int id = (int) httpSession.getAttribute("id");

        String type = (String) httpSession.getAttribute(who + "_type");
        ResourceBundle resource = ResourceBundle.getBundle("config");
        String pos = resource.getString("pos");
        String align = resource.getString("align");
        String raw_images = resource.getString("raw_images");

        String filePth = pos + raw_images + id + "_" + who + type;
        File file = new File(filePth);
        if (!file.exists()) {
            // 文件不存在
            return back("-2");
        }

        String[] com = {"python", pos + align, id + "_" + who + type};
        int re = Exec.main(com);
        if (re == 1) {
            return back("1");
        }
        return back("0");
    }

    //显示图片
    @RequestMapping("/aimg")
    @ResponseBody
    public JSONObject aimg(HttpServletRequest request, @RequestParam("who") int who, HttpSession httpSession) throws IOException {
        if (who != 1 && who != 2) {
            return back("-1");
        }
        int id = (int) httpSession.getAttribute("id");
        String type = (String) httpSession.getAttribute(who + "_type");
        ResourceBundle resource = ResourceBundle.getBundle("config");
        String aligned_images = resource.getString("aligned_images");
        String img = aligned_images + id + "_" + who + type;
        System.out.println(img);
        return back(img);
    }


}
