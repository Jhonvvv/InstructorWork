package com.clm.system.service;

import com.clm.system.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author su
 * @since 2021-11-29
 */
public interface SysPermissionService extends IService<SysPermission> {

    SysPermission addPermission(SysPermission sysPermission);

    boolean editPermission(SysPermission sysPermission);

    boolean deletePermission(String permissionId);

    List<SysPermission> queryUserRoleList(String username);

}
