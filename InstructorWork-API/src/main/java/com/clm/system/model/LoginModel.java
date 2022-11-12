package com.clm.system.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author su
 * @Date 2021/11/24 18:15
 */
@Data
@ApiModel(value="登录对象", description="登录对象")
public class LoginModel {
    @ApiModelProperty(value = "账号")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
}
