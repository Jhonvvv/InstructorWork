package com.clm.system.controller;


import com.clm.common.utils.Result;
import com.clm.common.utils.ResultCode;
import com.clm.system.entity.SysDepart;
import com.clm.system.entity.SysRole;
import com.clm.system.entity.SysUser;
import com.clm.system.model.DepartTree;
import com.clm.system.service.SysDepartService;
import com.clm.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 组织机构表 前端控制器
 * </p>
 *
 * @author su
 * @since 2021-11-22
 */
@CrossOrigin
@Api(tags = "部门管理")
@Slf4j
@RestController
@RequestMapping("/system/depart")
public class SysDepartController {

    @Autowired
    private SysDepartService sysDepartService;
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("新增部门")
    @PostMapping("/add")
    public Result<SysDepart> add(@RequestBody SysDepart depart) {
        Result<SysDepart> result = new Result<>();
        try {
            // 先判断该对象有无父级ID,有则意味着不是最高级,否则意味着是最高级
            if (StringUtils.isEmpty(depart.getParentId())) {
                depart.setOrgType("1");
            } else {
                depart.setOrgType("2");
            }
            sysDepartService.save(depart);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("新增部门通过树模型")
    @PostMapping("/addDepartTree")
    public Result<DepartTree> addDepartTree(@RequestBody DepartTree departTree) {
        Result<DepartTree> result = new Result<>();
        try {
            SysDepart depart=new SysDepart();
            BeanUtils.copyProperties(departTree,depart);
            depart.setDepartName(departTree.getTitle());
            // 先判断该对象有无父级ID,有则意味着不是最高级,否则意味着是最高级
            if (StringUtils.isEmpty(depart.getParentId())) {
                depart.setOrgType("1");
            } else {
                depart.setOrgType("2");
            }
            sysDepartService.save(depart);
            result.setResult(new DepartTree(depart));
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }


    @ApiOperation("修改部门通过树模型")
    @PostMapping("/editDepartTree")
    public Result<DepartTree> editDepartTree(@RequestBody DepartTree departTree) {
        Result<DepartTree> result = new Result<>();
        SysDepart sysDepart = sysDepartService.getById(departTree.getId());
        if (sysDepart == null) {
            result.error20001("未找到对应实体");
        } else {
            SysDepart depart=new SysDepart();
            BeanUtils.copyProperties(departTree,depart);
            depart.setDepartName(departTree.getTitle());
            boolean ok = sysDepartService.updateById(depart);
            if (ok) {
                result.success("修改成功!");
            } else {
                result.error20001("操作失败");
            }
        }
        return result;
    }

    @ApiOperation("修改部门")
    @PostMapping("/edit")
    public Result<SysDepart> edit(@RequestBody SysDepart depart) {
        Result<SysDepart> result = new Result<SysDepart>();
        SysDepart sysDepart = sysDepartService.getById(depart.getId());
        if (sysDepart == null) {
            result.error20001("未找到对应实体");
        } else {
            boolean ok = sysDepartService.updateById(depart);
            if (ok) {
                result.success("修改成功!");
            } else {
                result.error20001("操作失败");
            }
        }
        return result;
    }

    @ApiOperation("删除部门")
    @DeleteMapping("/delete")
    public Result<SysDepart> delete(@RequestParam(name = "id", required = true) String id) {
        Result<SysDepart> result = new Result<>();
        boolean deleteDepart = sysDepartService.deleteDepart(id);
        if (deleteDepart) {
            return result.success("删除部门成功");
        }
        return result.error20001("操作失败");
    }

    @ApiOperation("批量删除部门")
    @DeleteMapping("/deleteBatch")
    public Result<SysDepart> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<SysDepart> result = new Result<SysDepart>();
        if (StringUtils.isEmpty(ids)) {
            return result.error20001("未选中部门");
        } else {
            boolean deleteDepart = sysDepartService.deleteBatchDepart(ids.split(","));
            if (!deleteDepart) {
                return result.success("批量删除部门成功");
            }
            return result.error20001("操作失败");
        }

    }

    @ApiOperation("加载部门节点")
    @GetMapping("/queryTreeList")
    public Result<List<DepartTree>> queryTreeList(){
        Result<List<DepartTree>> result = new Result<>();
        try {
            List<DepartTree> departTreeList = sysDepartService.queryTreeList();
            result.setResult(departTreeList);
            result.setCode(ResultCode.SUCCESS);
            result.setMessage("获取成功");
        }catch (Exception e){
            log.error(e.getMessage());
            result.error20001("操作失败");
        }
        return result;
    }
    @ApiOperation("通过多个部门id查询")
    @GetMapping("/getDepartByDepartIds")
    public Result<?> getDepartByDepartIds(@RequestParam(value = "departIds") String  departIds){
        List<SysDepart> sysDeparts = sysDepartService.listByIds(Arrays.asList(departIds.split(",")));
        return Result.OK(sysDeparts);
    }


    @ApiOperation("通过用户账号查询所管理的部门")
    @GetMapping("/getDepartByUsername")
    public Result<?> getDepartByUsername(@RequestParam(value = "username") String  username){
        SysUser user = sysUserService.getUserInfoByUsername(username);
        if (user!=null){
            List<SysDepart> sysDeparts = sysDepartService.listByIds(Arrays.asList(user.getDepartIds().split(",")));
            return Result.OK(sysDeparts);
        }else {
            return Result.error("查询失败");
        }
    }
    @ApiOperation("通过多个用户账号查询")
    @GetMapping("/getAllDepart")
    public Result<?> getAllDepart(){
        List<SysDepart> sysDepartList = sysDepartService.list(null);
        if (sysDepartList!=null){
            return Result.OK(sysDepartList);
        }else {
            return Result.error("查询失败");
        }
    }
}

