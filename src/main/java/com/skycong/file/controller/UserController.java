package com.skycong.file.controller;

import com.skycong.file.dao.model.UserModel;
import com.skycong.file.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author RMC 2018/11/23 12:45
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    String loginSystem(String username, String password, Model model, HttpServletRequest request) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            model.addAttribute("tip", "用户名/密码不能为空");
            return "userLogin";
        }
        UserModel byUsername = userService.findByUsername(username);
        if (byUsername != null && byUsername.getPassword().equals(password)) {
            model.addAttribute("user", byUsername);
            request.getSession().setAttribute("USER", byUsername);
            return "redirect:/interface/findAllInterface";
        } else {
            model.addAttribute("tip", "用户名/密码错误");
            return "userLogin";
        }
    }


    @PostMapping("register")
    String registerSystem(String username, String password, Model model, HttpServletRequest request) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            model.addAttribute("tip", "用户名/密码不能为空");
            return "userRegister";
        }
        //判断重复
        UserModel byUsername1 = userService.findByUsername(username);
        if (byUsername1 != null) {
            model.addAttribute("tip", "该用户名已被注册");
            return "userRegister";
        }
        UserModel save = new UserModel();
        save.setUsername(username);
        save.setPassword(password);
        userService.save(save);
        model.addAttribute("tip", "注册成功");
        return "userLogin";
    }
}
