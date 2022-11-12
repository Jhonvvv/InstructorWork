package com.clm.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clm.common.base.constant.CommonConstant;
import com.clm.common.utils.Result;
import com.clm.common.utils.ResultCode;
import com.clm.system.entity.SysPermission;
import com.clm.system.entity.SysRolePermission;
import com.clm.system.model.PermissionTree;
import com.clm.system.service.SysPermissionService;
import com.clm.system.service.SysRolePermissionService;
import com.clm.vo.PermissionTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author su
 * @since 2021-11-29
 */
@CrossOrigin
@Api(tags = "权限管理")
@Slf4j
@RestController
@RequestMapping("/system/permission")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;


    @ApiOperation("加载权限节点")
    @GetMapping("/list")
    public Result<List<PermissionTree>> list() {
        long start = System.currentTimeMillis();
        Result<List<PermissionTree>> result = new Result<>();
        try {
            List<SysPermission> permissionList = sysPermissionService.list(null);
            List<PermissionTree> treeList = getTreeList(new ArrayList<PermissionTree>(), permissionList, null);
            result.setResult(treeList);
            result.setCode(ResultCode.SUCCESS);
            result.setMessage("获取成功");
            log.info("======获取全部菜单数据=====耗时:" + (System.currentTimeMillis() - start) + "毫秒");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @ApiOperation("加载用户角色权限节点")
    @GetMapping("/queryUserRoleList")
    public Result<List<PermissionTree>> queryUserRoleList(@RequestParam(name = "username",required = true)String username) {
        long start = System.currentTimeMillis();
        Result<List<PermissionTree>> result = new Result<>();
        try {
            if (StringUtils.isNotEmpty(username)){
                List<SysPermission> sysPermissionList = sysPermissionService.queryUserRoleList(username);
                List<PermissionTree> treeList = getTreeList(new ArrayList<PermissionTree>(), sysPermissionList, null);
                result.setResult(treeList);
                result.setCode(ResultCode.SUCCESS);
                result.setMessage("获取成功");
                log.info("======获取用户角色全部菜单数据=====耗时:" + (System.currentTimeMillis() - start) + "毫秒");
            }else {
                return result.error20001("获取失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }


    @ApiOperation("添加菜单")
    @PostMapping("/add")
    public Result<PermissionTree> add(@RequestBody SysPermission sysPermission) {
        Result<PermissionTree> result = new Result<>();
        SysPermission permission = sysPermissionService.addPermission(sysPermission);
        if (permission!=null) {
            result.setResult(new PermissionTree(permission));
            result.success("添加成功！");
        } else {
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("修改菜单")
    @PostMapping("/edit")
    public Result<?> edit(@RequestBody SysPermission sysPermission) {
        Result<?> result = new Result<>();
        boolean editPermission = sysPermissionService.editPermission(sysPermission);
        if (editPermission) {
            result.setCode(ResultCode.SUCCESS);
            result.setMessage("修改成功！");
        } else {
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("删除权限")
    @DeleteMapping("/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id ) {
        Result<?> result = new Result<>();
        boolean deletePermission = sysPermissionService.deletePermission(id);
        if (deletePermission) {
            result.setCode(ResultCode.SUCCESS);
            result.setMessage("删除成功！");
        } else {
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("查询角色授权")
    @GetMapping("/queryRolePermission")
    public Result<List<String>> queryRolePermission(@RequestParam(name = "roleId", required = true) String roleId){
        Result<List<String>> result = new Result<>();
        try {
            List<SysRolePermission> rolePermissions = sysRolePermissionService.list(new QueryWrapper<SysRolePermission>().lambda().eq(SysRolePermission::getRoleId, roleId));
            List<String> permissionIds = rolePermissions.stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList());
            result.setResult(permissionIds);
            result.setMessage("查询成功");
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    private List<PermissionTree> getTreeList(List<PermissionTree> permissionTreeList, List<SysPermission> permissionList, PermissionTree temp) {
        for (SysPermission permission : permissionList) {
            String parentId = permission.getParentId();
            PermissionTree tree = new PermissionTree(permission);
            if (temp == null && StringUtils.isEmpty(parentId)) {
                permissionTreeList.add(tree);
                if (CommonConstant.LEAF_TYPE_0.equals(tree.getIsLeaf())) {
                    getTreeList(permissionTreeList, permissionList, tree);
                }
            } else if (temp != null && parentId != null && parentId.equals(temp.getId())) {
                temp.getChildren().add(tree);
                if (CommonConstant.LEAF_TYPE_0.equals(tree.getIsLeaf())) {
                    getTreeList(permissionTreeList, permissionList, tree);
                }
            }
        }
        return permissionTreeList;
    }

}

