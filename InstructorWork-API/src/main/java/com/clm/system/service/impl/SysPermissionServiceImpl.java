package com.clm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clm.common.base.constant.CommonConstant;
import com.clm.system.entity.SysPermission;
import com.clm.system.entity.SysUser;
import com.clm.system.mapper.SysPermissionMapper;
import com.clm.system.mapper.SysRoleMapper;
import com.clm.system.mapper.SysRolePermissionMapper;
import com.clm.system.mapper.SysUserMapper;
import com.clm.system.service.SysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clm.system.service.SysRolePermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-11-29
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public SysPermission addPermission(SysPermission sysPermission) {
        sysPermission.setMenuType(CommonConstant.MENU_TYPE_0);
        String parentId = sysPermission.getParentId();
        if (!StringUtils.isEmpty(parentId)) {
            sysPermission.setMenuType(CommonConstant.MENU_TYPE_1);
            //设置父节点不为叶子节点
            this.baseMapper.setMenuLeaf(parentId, CommonConstant.MENU_TYPE_0);
        }
        sysPermission.setIsLeaf(CommonConstant.MENU_TYPE_1);
        this.save(sysPermission);
        return sysPermission;
    }

    @Override
    public boolean editPermission(SysPermission sysPermission) {
        SysPermission p = this.getById(sysPermission.getId());
        if (p == null) {
            return false;
        } else {
            //Step1.判断是否是一级菜单，是的话清空父菜单ID
            if (CommonConstant.MENU_TYPE_0.equals(sysPermission.getMenuType())) {
                sysPermission.setParentId(null);
            }
            //Step2.判断菜单下级是否有菜单，无则设置为叶子节点
            int submenu = judgeSubmenu(sysPermission.getId());
            if (submenu == 0) {
                sysPermission.setIsLeaf(CommonConstant.LEAF_TYPE_1);
            }
            this.updateById(sysPermission);
            //如果当前菜单的父菜单变了，则需要修改新父菜单和老父菜单的，叶子节点状态
            String parentId = sysPermission.getParentId();
            if (!StringUtils.isEmpty(parentId) && !parentId.equals(p.getParentId())
                    || StringUtils.isEmpty(parentId) && !StringUtils.isEmpty(p.getParentId())) {
                //设置父节点不为叶子节点
                this.baseMapper.setMenuLeaf(parentId, CommonConstant.MENU_TYPE_0);
                //判断老的菜单下是否还有其他子菜单，没有的话则设置为叶子节点
                int oldSubmenu = judgeSubmenu(sysPermission.getId());
                if (oldSubmenu == 0) {
                    if (!StringUtils.isEmpty(p.getParentId())) {
                        this.baseMapper.setMenuLeaf(parentId, CommonConstant.MENU_TYPE_1);
                    }
                }
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePermission(String permissionId) {
        SysPermission permission = baseMapper.selectById(permissionId);
        if (permission == null) {
            return false;
        }
        if (StringUtils.isNotEmpty(permission.getParentId())) {
            int count = baseMapper.selectCount(new QueryWrapper<SysPermission>().lambda().eq(SysPermission::getParentId, permission.getParentId()));
            if (count == 1) {
                //若父节点无其他子节点，则删除后父节点是叶子节点
                baseMapper.setMenuLeaf(permission.getParentId(), CommonConstant.LEAF_TYPE_0);
            }
        }
        baseMapper.deleteById(permission.getId());
        // 该节点可能是子节点但也可能是其它节点的父节点,所以需要级联删除
        removeChildrenBy(permission.getId());
        //关联删除
        Map map = new HashMap<>();
        map.put("permission_id", permissionId);
        //删除角色授权表
        sysRolePermissionMapper.deleteByMap(map);
        return true;
    }

    @Override
    public List<SysPermission> queryUserRoleList(String username) {
        String roleId = sysRoleMapper.getRoleIdByUserName(username);
        List<SysPermission> permissionList = baseMapper.getPermissionByRoleId(roleId);
        return permissionList;
    }


    /**
     * 根据父id删除其关联的子节点数据
     *
     * @return
     */
    public void removeChildrenBy(String parentId) {
        QueryWrapper<SysPermission> wrapper = new QueryWrapper<>();
        // 封装查询条件parentId为主键,
        wrapper.lambda().eq(SysPermission::getParentId, parentId);
        List<SysPermission> permissionList = baseMapper.selectList(wrapper);
        // 查出该主键下的所有子级
        if (permissionList != null && permissionList.size() > 0) {
            String id = ""; // id
            int num = 0; // 查出的子级数量
            // 如果查出的集合不为空, 则先删除所有
            baseMapper.delete(wrapper);
            // 再遍历刚才查出的集合, 根据每个对象,查找其是否仍有子级
            for (int i = 0; i < permissionList.size(); i++) {
                id = permissionList.get(i).getId();
                Map map = new HashMap<>();
                map.put("permission_id", id);
                //删除角色授权表
                sysRolePermissionMapper.deleteByMap(map);
                num = baseMapper.selectCount(new QueryWrapper<SysPermission>().lambda().eq(SysPermission::getParentId, id));
                // 如果有, 则递归
                if (num > 0) {
                    removeChildrenBy(id);
                }
            }
        }
    }

    /**
     * 判断菜单下级是否有菜单
     *
     * @param permissionId
     * @return
     */
    public int judgeSubmenu(String permissionId) {
        return this.count(new QueryWrapper<SysPermission>().lambda().eq(SysPermission::getParentId, permissionId));
    }
}
