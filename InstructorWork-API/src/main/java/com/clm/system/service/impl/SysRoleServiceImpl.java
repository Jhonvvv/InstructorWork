package com.clm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clm.system.entity.SysRole;
import com.clm.system.entity.SysRolePermission;
import com.clm.system.entity.SysUser;
import com.clm.system.entity.SysUserRole;
import com.clm.system.mapper.SysRoleMapper;
import com.clm.system.mapper.SysRolePermissionMapper;
import com.clm.system.mapper.SysUserMapper;
import com.clm.system.mapper.SysUserRoleMapper;
import com.clm.system.service.SysRolePermissionService;
import com.clm.system.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-11-22
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRole(String roleId) {
        //1.删除角色和用户关系
        sysRoleMapper.deleteRoleUserRelation(roleId);
        // 2.删除角色和权限关系
        sysRoleMapper.deleteRolePermissionRelation(roleId);
        //3.删除角色
        this.removeById(roleId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatchRole(String[] roleIds) {
        //1.删除角色和用户关系
        sysRoleMapper.deleteBathRoleUserRelation(roleIds);
        //2.删除角色和权限关系
        sysRoleMapper.deleteBathRolePermissionRelation(roleIds);
        //3.删除角色
        this.removeByIds(Arrays.asList(roleIds));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRolePermission(String roleId, String permissionIds) {
        QueryWrapper<SysRolePermission> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRolePermission::getRoleId, roleId);
        sysRolePermissionService.remove(wrapper);
        List<SysRolePermission> rolePermissionList = new ArrayList<>();
        String[] permissionIdArr = permissionIds.split(",");
        for (String pId:permissionIdArr){
            if (!StringUtils.isEmpty(pId)){
                SysRolePermission sysRolePermission=new SysRolePermission(roleId,pId);
                rolePermissionList.add(sysRolePermission);
            }
        }
        sysRolePermissionService.saveBatch(rolePermissionList);
        return true;
    }

    @Override
    public boolean checkRoleCode(String roleId, String roleCode) {
        SysRole role = null;
        if (!StringUtils.isEmpty(roleId)) {
            role = baseMapper.selectById(roleId);
        }
        SysRole oldRole = null;
        if (!StringUtils.isEmpty(roleCode)) {
            oldRole = baseMapper.selectOne(new QueryWrapper<SysRole>().lambda().eq(SysRole::getRoleCode, roleCode));
        }
        //传入的roleCode查询到信息了，需要判断是新增还是修改
        if (oldRole != null) {
            //role为空=>新增模式=>只要roleCode存在则返回
            if (role==null) {
                return true;
            } else if (!roleId.equals(oldRole.getId())) {
                //否则=>编辑模式=>判断两者ID是否一致
                return true;
            }
        }
        return false;
    }

    @Override
    public SysRole queryUserRole(String username) {
        SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, username));
        if (user!=null){
            SysUserRole userRole = sysUserRoleMapper.selectOne(new QueryWrapper<SysUserRole>().lambda().eq(SysUserRole::getUserId, user.getId()));
            SysRole role = sysRoleMapper.selectById(userRole.getRoleId());
            return role;
        }
        return null;
    }
}
