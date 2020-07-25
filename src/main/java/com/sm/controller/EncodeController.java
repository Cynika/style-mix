package com.sm.controller;


import com.alibaba.fastjson.JSONObject;
import com.cmd.Exec;
import com.sm.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.Util.JsonBack.back;


@Controller
public class EncodeController {
    @Resource(name = "userService")
    IUserService userService;

    //运行特征码化任务
    @RequestMapping("/latent")
    @ResponseBody
    public JSONObject latent(HttpServletRequest request, @RequestParam("who") int who, HttpSession httpSession) throws IOException {
        int id = (int) httpSession.getAttribute("id");
        System.out.println(id);
        if (who != 1 && who != 2) {
            return back("-1");
        }
        int state = userService.get_npy_state(id, who);
        if (state == 2) {
            return back("2");
        }

        String type = (String) httpSession.getAttribute(who + "_type");
        ResourceBundle resource = ResourceBundle.getBundle("config");
        String pos = resource.getString("pos");
        String encode = resource.getString("encode");
        String[] com = {"python", pos + encode, id + "_" + who + type};
        if (type == null) {
            return back("-2");
        }

        userService.set_npy_state(id, who, 2);
        int re = Exec.main(com);
        System.out.println(com);
        if (re == 1) {
            userService.set_npy_state(id, who, 3);
            System.out.println("完成一个特征化任务");
            return back("1");
        }
        userService.set_npy_state(id, who, 1);
        return back("0");
    }

    //查看特征码化任务状态
    @RequestMapping("/state")
    @ResponseBody
    public JSONObject GetState(HttpServletRequest request, @RequestParam("who") int who, HttpSession httpSession) throws IOException {
        if (who != 1 && who != 2) {
            return back("-1");
        }
        int id = (int) httpSession.getAttribute("id");
        int state = userService.get_npy_state(id, who);
        return back(Integer.toString(state));
    }
}
