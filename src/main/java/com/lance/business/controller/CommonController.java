package com.lance.business.controller;

import com.lance.common.base.model.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lance
 * @version v0.0.1
 * @project httpdataapi
 * @describe
 * @since 2019/2/24
 **/
@Controller
@RequestMapping(value = "/common")
public class CommonController
{
    @RequestMapping(name = "用户登陆", value = "/login")
    @ResponseBody
    public JsonResult userInfo(HttpServletRequest request, HttpServletResponse response)
    {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        return new JsonResult(true, "登陆成功");
    }
}
