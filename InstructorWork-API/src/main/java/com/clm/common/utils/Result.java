package com.clm.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 接口返回数据格式
 * @Author su
 * @Date 2021/11/21 10:00
 */
@Data
@ApiModel(value="接口返回对象", description="接口返回对象")
public class Result<T>  implements Serializable {
    /**
     * 成功标志
     */
    @ApiModelProperty(value = "成功标志")
    private boolean success = true;

    /**
     * 返回处理消息
     */
    @ApiModelProperty(value = "返回处理消息")
    private String message = "操作成功！";

    /**
     * 返回代码
     */
    @ApiModelProperty(value = "返回代码")
    private Integer code = ResultCode.SUCCESS;

    /**
     * 返回数据对象 data
     */
    @ApiModelProperty(value = "返回数据对象")
    private T result;

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    private long timestamp = System.currentTimeMillis();

    public Result() {

    }

    public Result<T> success(String message){
        this.message=message;
        this.code=ResultCode.SUCCESS;
        this.success=true;
        return this;
    }
    public static <T> Result<T> OK(){
        Result<T> r=new Result<T>();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }
    public static <T> Result<T> OK(T data){
        Result<T> r=new Result<T>();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        r.setResult(data);
        return r;
    }
    public static <T> Result<T> OK(String msg,T data){
        Result<T> r=new Result<T>();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage(msg);
        r.setResult(data);
        return r;
    }
    public static Result<Object> error(String msg){
        return error(ResultCode.ERROR,msg);
    }
    public static Result<Object> error(int code,String msg){
        Result<Object> r=new Result<Object>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        return r;
    }
    public Result<T> error20001(String message) {
        this.message = message;
        this.code = ResultCode.ERROR;
        this.success = false;
        return this;
    }
}
