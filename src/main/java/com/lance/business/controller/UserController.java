package com.lance.business.controller;


import com.lance.business.service.UserService;
import com.lance.common.base.model.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sys_user")
public class UserController
{

    @Autowired
    private UserService userService;

    @RequestMapping(name = "获取用户信息", value = "/getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getUserInfo(String userid) throws Exception
    {
        return userService.getUserInfo(userid);
    }

    @RequestMapping(name = "添加或编辑用户", value = "/addOrEdit", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult add(String userid, String username, String password) throws Exception
    {
        return userService.addOrEditUser(userid, username, password);
    }


    @RequestMapping(name = "用户列表", value = "/listUsers", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult list() throws Exception
    {
        return userService.listUsers();
    }

    @RequestMapping(name = "删除用户", value = "/delUser", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult del(String userid) throws Exception
    {
        return userService.removeUser(userid);
    }
}
