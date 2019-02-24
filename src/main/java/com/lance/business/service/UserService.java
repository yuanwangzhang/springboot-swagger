package com.lance.business.service;


import com.lance.common.base.model.JsonResult;

public interface UserService
{

    JsonResult addOrEditUser(String userid, String username, String password) throws Exception;

    JsonResult removeUser(String userid) throws Exception;

    JsonResult getUserInfo(String userid) throws Exception;

    JsonResult listUsers() throws Exception;
}
