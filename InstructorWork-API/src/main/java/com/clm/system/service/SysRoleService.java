package com.clm.system.service;

import com.clm.system.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author su
 * @since 2021-11-22
 */
public interface SysRoleService extends IService<SysRole> {
   boolean deleteRole(String roleId);
   boolean deleteBatchRole(String[] roleIds);
   boolean saveRolePermission(String roleId, String permissionIds);
   boolean checkRoleCode(String roleId,String roleCode);
   SysRole queryUserRole(String username);
}
