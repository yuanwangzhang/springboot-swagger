package com.lance.common.base.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@ApiModel
public class ApiResult
{
    @ApiModelProperty(value = "返回状态状态码", name = "code")
    private String code;

    @ApiModelProperty(value = "返回信息", name = "message")
    private String message;

    @ApiModelProperty(value = "数据", name = "data")
    private Object data;

    public ApiResult(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
