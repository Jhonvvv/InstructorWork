package com.clm.system.service;

import com.clm.common.utils.Result;
import com.clm.system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clm.vo.ResisterUserVo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author su
 * @since 2021-11-22
 */
public interface SysUserService extends IService<SysUser> {
    SysUser saveUser(ResisterUserVo resisterUserVo);

    SysUser saveUserByRole(ResisterUserVo resisterUserVo);

    Result<?> checkUserIsEffective(SysUser sysUser);

    boolean checkUsername(String userId, String username);

    boolean editUser(SysUser sysUser);

    SysUser  saveUserIdentity(SysUser sysUser);

   boolean deleteUser(String userId);

   SysUser getUserInfoByUsername(String username);
}
