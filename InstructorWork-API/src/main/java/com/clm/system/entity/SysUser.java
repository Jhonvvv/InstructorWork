package com.clm.system.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author su
 * @since 2021-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
@ApiModel(value = "SysUser对象", description = "用户表")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "登录账号")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "真实姓名")
    @TableField("real_name")
    private String realName;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "md5密码盐")
    @TableField("salt")
    private String salt;

    @ApiModelProperty(value = "所属部门")
    @TableField("depart_id")
    private String departId;

    @ApiModelProperty(value = "所属角色")
    @TableField("role_id")
    private String roleId;

    @ApiModelProperty(value = "负责部门")
    @TableField("depart_ids")
    private String departIds;

    @ApiModelProperty(value = "头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty(value = "生日")
    @TableField("birthday")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    @ApiModelProperty(value = "性别(0-默认未知,1-男,2-女)")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty(value = "电子邮件")

    @TableField("email")
    private String email;

    @ApiModelProperty(value = "电话")
    @Excel(name = "手机号码", width = 15)
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "状态(1-正常,2-冻结)")
    @TableField(value = "status", fill = FieldFill.INSERT)
    private Integer status;

    @ApiModelProperty(value = "身份（1学生 2辅导员 3管理员 4其他）")
    @TableField("user_identity")
    @Excel(name = "身份（1学生 2辅导员 3管理员 4其他）", width = 30)
    private Integer userIdentity;

    @ApiModelProperty(value = "删除状态(0-正常,1-已删除)")
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @TableLogic
    private Integer delFlag;

    @ApiModelProperty(value = "创建人")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
