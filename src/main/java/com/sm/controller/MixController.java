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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.Util.JsonBack.back;
import static com.Util.valueFilter.filter2f;
import static com.Util.valueFilter.filter2s;

@Controller
public class MixController {
    @Resource(name = "userService")
    IUserService userService;

    ////运行混合任务
    @RequestMapping("/mix")
    @ResponseBody
    public JSONObject mix(HttpServletRequest request, @RequestParam("mode") int mode, @RequestParam(value = "mixs[]") float[] mixs, HttpSession httpSession) throws IOException {
        if (mode != 2 && mode != 1) {
            back("-1");
        }
        String type = ".npy";
        int id = (int) httpSession.getAttribute("id");
        int state1 = userService.get_npy_state(id, 1);
        int state2 = userService.get_npy_state(id, 2);
        if (state1 == 2 || state2 == 2) {
            return back("-2");
        }
        if (mode == 2 && state2 != 3) {
            return back("-2");
        }
        if (state1 != 3) {
            return back("-2");
        }

        ResourceBundle resource = ResourceBundle.getBundle("config");
        String pos = resource.getString("pos");
        String mix = resource.getString("mix");
        for (float a : mixs)
            System.out.println(a);

        String face_mix = filter2s(0, 1, 0, 600, mixs[0]);
        String gender_mix = filter2s(-8, 8, 0, 600, mixs[1]);
        String age_mix = filter2s(-5, 5, 0, 600, mixs[2]);
        String beauty_mix = filter2s(-9, 9, 0, 600, mixs[3]);
        String smile_mix = filter2s(0, 0, 0, 600, mixs[4]);
        String angle_mix = filter2s(-9, 9, 0, 600, mixs[5]);

        String[] com = {"python", pos + mix, String.valueOf(id), id + "_" + "1" + type, id + "_" + "2" + type, String.valueOf(mode), face_mix, gender_mix, age_mix, beauty_mix, smile_mix, angle_mix};

        for (String a : com)
            System.out.println(a);

        int re = Exec.main(com);
        if (re == 1) {
            System.out.println("完成一个生成任务");
            return back("1");
        }
        return back("0");
    }

    @RequestMapping("/mimg")
    @ResponseBody
    public JSONObject mimg(HttpServletRequest request, HttpSession httpSession) throws IOException {
        int id = (int) httpSession.getAttribute("id");

        ResourceBundle resource = ResourceBundle.getBundle("config");
        String generated_images = resource.getString("generated_images");
        String img = generated_images + id + "result" + ".png";
        System.out.println(img);
        return back(img);
    }
}
