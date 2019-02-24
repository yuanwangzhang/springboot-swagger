package com.lance.business.pojo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lance.common.core.annotation.Validation;
import com.lance.common.core.annotation.impl.ValidationUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基础信息类
 *
 * @author lance
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SampleModel
{
    /**
     * 标识
     */
    public String key;

    /**
     * 是否允许查询
     */
    @Validation(limitValue = {"否"}, limitMsg = "不允许此渠道查询")
    public String allowSearch;

    /**
     * 类型
     */
    @Validation(allowValue = {"A类", "C类"}, limitMsg = "此类型不受理")
    public String kind;

    /**
     * 信息校验
     *
     * @return 检验结果
     */
    public String validateResult()
    {
        return ValidationUtils.validate(this);
    }
}
