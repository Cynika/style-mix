package com.sm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sm.service.IUserService;
import com.sm.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static com.Util.JsonArrayBack.backArray;
import static com.Util.JsonBack.back;

@Controller
public class UserController {
    @Resource(name = "userService")
    IUserService userService;

    protected void writeJSON2Response(Object out, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try {
            //System.out.println("SERVER: " + out);
            response.getWriter().print(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
    @ResponseBody
    public void userLogin(@RequestParam(value = "userName") String userName,
                          @RequestParam(value = "userPassword") String userPassword,
                          HttpServletResponse response, HttpSession httpSession) {
        System.out.println(userName + " " + userPassword);
        Object result = "{\"flag\":false}";
        if (userService.findByNameAndPassword(userName, userPassword) != null)
            result = "{\"flag\":true}";
        User user = userService.findUserByName(userName);
        httpSession.setAttribute("id", user.getId());
        httpSession.setAttribute("role", user.getRole());
        httpSession.setAttribute("state", user.getState());
        writeJSON2Response(result, response);
    }


    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    @ResponseBody
    public void userRegister(@RequestBody User user, HttpServletResponse response) {
        System.out.println(user.toString());
        Object result = "{\"flag\":false}";
        // System.out.println(user);
        if (userService.insertUser(user) > 0)
            result = "{\"flag\":true}";
        writeJSON2Response(result, response);
    }


    @RequestMapping(value = "/isRegistered", method = RequestMethod.POST)
    @ResponseBody
    public void isRegistered(String name, HttpServletResponse response) {
        System.out.println(name);
        Object result = "{\"flag\":false}";
        if (userService.findUserByName(name) == null) {
            result = "{\"flag\":true}";
        } else {
            System.out.println(userService.findUserByName(name).toString());
        }

        writeJSON2Response(result, response);
    }

    @RequestMapping("/FreezeUserById")
    @ResponseBody
    public JSONObject Freeze_UserById(HttpServletRequest request, @RequestParam("who") int id, HttpSession httpSession) throws IOException {
        int role = (int) httpSession.getAttribute("role");
        if (role != 1) {
            return back("-1");
        }
        int result = userService.FreezeUser(id);
        if (result > 0)
            return back("1");
        else
            return back("0");
    }

    @RequestMapping("/ActiveUserById")
    @ResponseBody
    public JSONObject Active_UserById(HttpServletRequest request, @RequestParam("id") int id, HttpSession httpSession) throws IOException {
        int role = (int) httpSession.getAttribute("role");
        if (role != 1) {
            return back("-1");
        }
        int result = userService.ActiveUser(id);
        if (result > 0)
            return back("1");
        else
            return back("0");
    }

    @RequestMapping("/queryAllUsers")
    @ResponseBody
    public JSONObject query_AllUsers(HttpServletRequest request, HttpSession httpSession, Map<String, Object> map) throws IOException {
        int role = (int) httpSession.getAttribute("role");
        if (role != 1) {
            return back("-1");
        }
        List<User> result = userService.queryAllUsers();
        return backArray(result);
    }
}
