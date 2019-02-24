package com.lance.common.base.model;


/**
 * 返回的基本Model对象
 *
 * @author lance
 */
public class ResultCommunication
{
    private static ResultCommunication resultCommunication = null;

    /**
     * 获取实例
     *
     * @return
     */
    public static ResultCommunication getInstance()
    {
        if (resultCommunication == null)
        {
            resultCommunication = new ResultCommunication();
        }
        return resultCommunication;
    }

    /**
     * 获取成功基础Model对象
     *
     * @return
     */
    public ApiResult getSuccessModel()
    {
        ApiResult resp = new ApiResult();
        resp.setCode(ResponseCode.D9999.getKey());
        resp.setMessage(ResponseCode.D9999.getValue());
        return resp;
    }

    /**
     * 获取执行中的Model对象
     *
     * @return
     */
    public ApiResult getExecutingModel()
    {
        ApiResult resp = new ApiResult();
        resp.setCode(ResponseCode.D8888.getKey());
        resp.setMessage(ResponseCode.D8888.getValue());
        return resp;
    }

    /**
     * 获取成功基础Model对象
     *
     * @return
     */
    public ApiResult getSuccessModel(Object data)
    {
        ApiResult resp = new ApiResult();
        resp.setCode(ResponseCode.D9999.getKey());
        resp.setMessage(ResponseCode.D9999.getValue());
        resp.setData(data);
        return resp;
    }

    /**
     * 获取异常基础Model对象
     *
     * @return
     */
    public ApiResult getErrorModel()
    {
        ApiResult resp = new ApiResult();
        resp.setCode(ResponseCode.D0000.getKey());
        resp.setMessage(ResponseCode.D0000.getValue());
        return resp;
    }

    /**
     * 获取失败基础Model对象
     *
     * @return
     */
    public ApiResult getFailedModel(String rtnCode, String rtnMsg)
    {
        ApiResult resp = new ApiResult();
        resp.setCode(rtnCode);
        resp.setMessage(rtnMsg);
        return resp;
    }

    /**
     * 获取失败基础Model对象
     *
     * @return
     */
    public ApiResult getFailedModel(ResponseCode responseCode)
    {
        ApiResult resp = new ApiResult();
        resp.setCode(responseCode.getKey());
        resp.setMessage(responseCode.getValue());
        return resp;
    }
}
