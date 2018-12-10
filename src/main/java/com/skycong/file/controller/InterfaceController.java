package com.skycong.file.controller;

import com.skycong.file.dao.model.InterfaceModel;
import com.skycong.file.dao.model.UserModel;
import com.skycong.file.service.InterfaceService;
import com.skycong.file.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RMC 2018/11/23 12:45
 */
@Controller
@RequestMapping("interface")
public class InterfaceController {


    @Autowired
    private UserService userService;

    @Autowired
    private InterfaceService interfaceService;

    @RequestMapping("findAllInterface")
    String findAll(Model model) {
        //需要登录
        UserModel loginUser1 = userService.getLoginUser();
        if (loginUser1 == null) {
            model.addAttribute("tip", "请先登录");
            return "redirect:/userLogin";
        }
        UserModel loginUser = userService.getLoginUser();
        model.addAttribute("user", loginUser);
        model.addAttribute("interfaceList", interfaceService.findAll());
        return "home";
    }

    @RequestMapping("add")
    String add(InterfaceModel save, Model model) {
        //需要登录
        UserModel loginUser1 = userService.getLoginUser();
        if (loginUser1 == null) {
            model.addAttribute("tip", "请先登录");
            return "redirect:/userLogin";
        }
        if (save.getName() == null || save.getName().isEmpty() || save.getAddress() == null || save.getAddress().isEmpty()) {
            model.addAttribute("tip", "新增接口名称/URI不能为空");
            return "addInterface";
        }
        save.setCreateBy(loginUser1.getUsername());
        interfaceService.save(save);
        return "redirect:findAllInterface";
    }

    @GetMapping("view/{id}")
    String add(@PathVariable("id") Integer id, Model model) {
        InterfaceModel byId = interfaceService.findById(id);
        model.addAttribute("interfaceDetail", byId);
        return "detailInterface";
    }

    @RequestMapping("search")
    String search(String keyword, Model model) {
        //需要登录
        UserModel loginUser1 = userService.getLoginUser();
        if (loginUser1 == null) {
            model.addAttribute("tip", "请先登录");
            return "redirect:/userLogin";
        }
        List<InterfaceModel> search = new ArrayList<>();
        if (keyword != null && !keyword.isEmpty()) {
            search = interfaceService.search(keyword);
        }
        model.addAttribute("interfaceList", search);
        return "search";
    }

    @RequestMapping("deleteOne/{id}")
    String deleteOne(@PathVariable("id") Integer id, Model model) {
        //需要登录
        UserModel loginUser1 = userService.getLoginUser();
        if (loginUser1 == null) {
            model.addAttribute("tip", "请先登录");
            return "redirect:/userLogin";
        }
        interfaceService.delete(id);
        return "redirect:/interface/findAllInterface";
    }

    /**
     * 去编辑页面
     */
    @RequestMapping("toEdit/{id}")
    String toEdit(@PathVariable("id") Integer id, Model model) {
        //需要登录
        UserModel loginUser1 = userService.getLoginUser();
        if (loginUser1 == null) {
            model.addAttribute("tip", "请先登录");
            return "redirect:/userLogin";
        }
        InterfaceModel interfaceModel = interfaceService.findById(id);
        model.addAttribute("interfaceDetail", interfaceModel);
        return "editInterface";
    }


    /**
     * 去编辑页面
     */
    @RequestMapping("editInterface")
    String editInterface(InterfaceModel update, Model model) {
        //需要登录
        UserModel loginUser1 = userService.getLoginUser();
        if (loginUser1 == null) {
            model.addAttribute("tip", "请先登录");
            return "redirect:/userLogin";
        }
        model.addAttribute("interfaceDetail", update);
        Integer id = update.getId();
        if (update.getId() == null) {
            model.addAttribute("tip", "编辑接口ID不能为空");
            return "redirect:/";
        }

        if (update.getName() == null || update.getName().isEmpty() || update.getAddress() == null || update.getAddress().isEmpty()) {
            model.addAttribute("tip", "编辑接口名称/URI不能为空");
            return "redirect:toEdit/" + id;
        }
        update.setUpdateBy(loginUser1.getUsername());
        interfaceService.update(update);
        return "redirect:view/" + update.getId();
    }
}
