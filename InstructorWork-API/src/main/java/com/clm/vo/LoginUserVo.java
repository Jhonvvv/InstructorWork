package com.clm.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Set;

/**
 * @Author su
 * @Date 2021/11/24 17:54
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginUserVo {
    /**
     * 登录人id
     */
    private String id;

    /**
     * 登录人账号
     */
    private String username;

    /**
     * 登录人名字
     */
    private String realName;

    /**
     * 登录人密码
     */
    private String password;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 性别（1：男 2：女）
     */
    private Integer sex;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 状态(1：正常 2：冻结 ）
     */
    private Integer status;

    private Integer delFlag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     *  身份（1 学生 2 辅导员 3 管理员）
     */
    private Integer userIdentity;

    /**
     * 管理部门ids
     */
    private String departIds;

    /**
     * 角色
     */
    private Set<String> roles;
}
