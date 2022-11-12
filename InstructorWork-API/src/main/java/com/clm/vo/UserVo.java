package com.clm.vo;

import com.clm.system.entity.SysDepart;
import com.clm.system.entity.SysUser;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author su
 * @Date 2021/11/24 20:49
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ResisterUserVo对象")
public class UserVo {
    SysUser userInfo;
    SysDepart depart;
    String token;
}
