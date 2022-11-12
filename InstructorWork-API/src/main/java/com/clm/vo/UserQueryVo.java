package com.clm.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author su
 * @Date 2021/11/28 20:06
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserQueryVo {
    private String realName;
    private String username;
    private String userIdentity;
    private String departId;
    private String departIds;
    private String roleId;
}
