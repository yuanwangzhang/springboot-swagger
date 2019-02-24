package com.lance.business.controller;

import com.lance.business.service.ApiService;
import com.lance.common.base.model.ApiResult;
import com.lance.common.base.model.JsonResult;
import com.lance.common.base.model.ResponseCode;
import com.lance.common.base.model.ResultCommunication;
import com.lance.common.core.annotation.RequestSign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 接口控制层
 *
 * @author lance
 **/
@Slf4j
@Controller
@RequestMapping("/handler")
@Api("数据接口")
public class ApiController
{
    /**
     * 接口业务处理类
     */
    @Autowired
    private ApiService apiService;


    /**
     * 获取测试信息
     */
    @RequestSign(expire = 300000)
    @ResponseBody
    @RequestMapping(value = "/sampleTest", method = RequestMethod.POST)
    @ApiOperation(value = "测试", notes = "根据标识获取信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "key", value = "唯一标识", required = true, dataType = "String"),
            @ApiImplicitParam(name = "extend", value = "预留", dataType = "String")})
    public ApiResult sampleTest(@RequestParam String key, @RequestParam(required = false) String extend)
    {
        log.info(extend);
        JsonResult result = apiService.getInfoByKey(key);
        if (result.getSuccess())
        {
            return ResultCommunication.getInstance().getSuccessModel(result.getData());
        }
        else
        {
            return ResultCommunication.getInstance().getFailedModel((ResponseCode) result.getData());
        }
    }
}
