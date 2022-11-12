package com.clm.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.common.utils.Result;
import com.clm.system.entity.SysRole;
import com.clm.system.entity.SysRolePermission;
import com.clm.system.service.SysRoleService;
import com.clm.vo.RolePermissionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Delete;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author su
 * @since 2021-11-22
 */
@CrossOrigin
@Api(tags = "角色管理")
@Slf4j
@RestController
@RequestMapping("/system/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation("查询所有角色带分页")
    @GetMapping("/queryAll")
    public Result<?> queryAll(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<SysRole> page = new Page<>(pageNo, pageSize);
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        IPage<SysRole> sysRolePage = sysRoleService.page(page, wrapper);
        return Result.OK(sysRolePage);
    }


    @ApiOperation("查询所有角色")
    @GetMapping("/queryRoleAll")
    public Result<?> queryRoleAll() {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        List<SysRole> sysRoles = sysRoleService.list(wrapper);
        return Result.OK(sysRoles);
    }


    @ApiOperation("新增角色")
    @PostMapping("/add")
    public Result<SysRole> add(@RequestBody SysRole role) {
        Result<SysRole> result = new Result<SysRole>();
        try {
            sysRoleService.save(role);
            result.setResult(role);
            result.success("添加成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("修改角色")
    @PostMapping("/edit")
    public Result<SysRole> edit(@RequestBody SysRole role) {
        Result<SysRole> result = new Result<SysRole>();
        SysRole sysRole = sysRoleService.getById(role.getId());
        if (sysRole == null) {
            result.error20001("未找到对应实体");
        } else {
            boolean ok = sysRoleService.updateById(role);
            if (ok) {
                result.success("修改成功!");
            } else {
                result.error20001("操作失败");
            }
        }
        return result;
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/delete")
    public Result<SysRole> delete(@RequestParam(name = "id", required = true) String id) {
        Result<SysRole> result = new Result<SysRole>();
        boolean deleteRole = sysRoleService.deleteRole(id);
        if (deleteRole) {
            return result.success("删除角色成功");
        }
        return result.error20001("操作失败");
    }

    @ApiOperation("批量删除角色")
    @DeleteMapping("/deleteBatch")
    public Result<SysRole> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<SysRole> result = new Result<SysRole>();

        if (StringUtils.isEmpty(ids)) {
            return result.error20001("未选中角色");
        } else {
            boolean batchRole = sysRoleService.deleteBatchRole(ids.split(","));
            if (!batchRole) {
                return result.success("批量删除角色成功!");

            }
            return result.error20001("操作失败");
        }
    }

    @ApiOperation("校验角色编码唯一")
    @GetMapping("/checkRoleCode")
    public Result<Boolean> checkRoleCode(String roleId, String roleCode) {
        Result<Boolean> result = new Result<>();
        result.setResult(false);
        log.info("--验证角色编码是否唯一---id:" + roleId + "--roleCode:" + roleCode);
        boolean checkCode = sysRoleService.checkRoleCode(roleId, roleCode);
        if (checkCode) {
            result.setResult(true);
            result.success("角色编码已存在");
            return result;
        }
        return result;

    }

    @ApiOperation("角色授权功能")
    @PostMapping("/saveRolePermission")
    public Result<?> saveRolePermission(@RequestBody RolePermissionVo rolePermissionVo) {
        Result<?> result = new Result<>();
        boolean saveRolePermission = sysRoleService.saveRolePermission(rolePermissionVo.getRoleId(), rolePermissionVo.getPermissionIds());
        if (saveRolePermission) {
            return result.success("授权成功");
        } else {
            return result.error20001("失败");
        }
    }

    @ApiOperation("角色授权功能")
    @GetMapping("/queryUserRole")
    public Result<?> queryUserRole(@RequestParam(value = "username") String username) {
        Result<SysRole> result = new Result<>();
        SysRole role = sysRoleService.queryUserRole(username);
        if (role != null) {
            result.setResult(role);
            result.success("操作成功");
        } else {
            result.error20001("操作失败");
        }
        return result;
    }

}

