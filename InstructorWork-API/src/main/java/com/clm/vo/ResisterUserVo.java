package com.clm.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author su
 * @Date 2021/11/22 9:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ResisterUserVo对象")
public class ResisterUserVo {

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "登录账号")
    private String username;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "md5密码盐")
    private String salt;

    @ApiModelProperty(value = "负责部门")
    private String departIds;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "生日")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    @ApiModelProperty(value = "性别(0-默认未知,1-男,2-女)")
    private Integer sex;

    @ApiModelProperty(value = "电子邮件")
    private String email;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "状态(1-正常,2-冻结)")
    private Integer status;

    @ApiModelProperty(value = "身份（1学生 2辅导员 3管理员 4其他）")
    private Integer userIdentity;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "部门id")
    private String departId;

}
