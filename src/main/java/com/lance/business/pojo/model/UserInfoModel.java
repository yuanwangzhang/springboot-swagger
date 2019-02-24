package com.lance.business.pojo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用戶信息类
 *
 * @author lance
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class UserInfoModel
{
    /**
     * 标识
     */
    public String userid;

    /**
     * 用戶名
     */
    public String username;

    /**
     * 密码
     */
    public String password;
}
