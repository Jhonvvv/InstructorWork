package com.clm.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.common.base.constant.CommonConstant;
import com.clm.common.base.controller.ClmController;
import com.clm.common.utils.PasswordUtils;
import com.clm.common.utils.Result;
import com.clm.common.utils.OConvertUtils;
import com.clm.common.utils.ResultCode;
import com.clm.system.entity.SysUser;
import com.clm.system.service.SysUserService;
import com.clm.vo.ResisterUserVo;
import com.clm.vo.UserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author su
 * @since 2021-11-22
 */
@CrossOrigin
@Api(tags = "用户管理")
@Slf4j
@RestController
@RequestMapping("/system/user")
public class SysUserController extends ClmController<SysUser, SysUserService> {
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("新增用户")
    @PostMapping("/add")
    public Result<SysUser> add(@RequestBody ResisterUserVo resisterUserVo) {
        Result<SysUser> result = new Result<SysUser>();
        try {
            String salt = OConvertUtils.randomGen(8);
            resisterUserVo.setSalt(salt);
            String passwordEncode = PasswordUtils.encrypt(resisterUserVo.getUsername(), resisterUserVo.getPassword(), salt);
            resisterUserVo.setPassword(passwordEncode);
            resisterUserVo.setStatus(CommonConstant.USER_UNFREEZE);
            // 保存用户走一个service 保证事务
            SysUser user = sysUserService.saveUser(resisterUserVo);
            result.setResult(user);
            result.setCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("新增用户通过身份")
    @PostMapping("/addUser")
    public Result<SysUser> addUser(@RequestBody SysUser sysUser) {
        Result<SysUser> result = new Result<SysUser>();
        try {
            String salt = OConvertUtils.randomGen(8);
            sysUser.setSalt(salt);
            String passwordEncode = PasswordUtils.encrypt(sysUser.getUsername(), sysUser.getPassword(), salt);
            sysUser.setPassword(passwordEncode);
            sysUser.setStatus(CommonConstant.USER_UNFREEZE);
            // 保存用户走一个service 保证事务
            sysUser = sysUserService.saveUserIdentity(sysUser);
            result.setResult(sysUser);
            result.setCode(ResultCode.SUCCESS);
            result.success("操作成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("新增用户通过角色")
    @PostMapping("/addUserByRole")
    public Result<SysUser> addUserByRole(@RequestBody ResisterUserVo resisterUserVo) {
        Result<SysUser> result = new Result<SysUser>();
        try {
            String salt = OConvertUtils.randomGen(8);
            resisterUserVo.setSalt(salt);
            String passwordEncode = PasswordUtils.encrypt(resisterUserVo.getUsername(), resisterUserVo.getPassword(), salt);
            resisterUserVo.setPassword(passwordEncode);
            resisterUserVo.setStatus(CommonConstant.USER_UNFREEZE);
            // 保存用户走一个service 保证事务
            SysUser user = sysUserService.saveUserByRole(resisterUserVo);
            result.setResult(user);
            result.setCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("修改用户")
    @PostMapping("/edit")
    public Result<SysUser> edit(@RequestBody SysUser sysUser) {
        Result<SysUser> result = new Result<SysUser>();
        try {
            SysUser user = sysUserService.getById(sysUser.getId());
            if (user == null) {
                result.error20001("未找到对应实体");
            } else {
                // 修改用户走一个service 保证事务
                sysUserService.editUser(sysUser);
                result.setResult(sysUser);
                result.success("修改成功!");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        if (StringUtils.isNotEmpty(id)) {
            this.sysUserService.deleteUser(id);
        }
        return Result.OK().success("删除用户成功");
    }

    @ApiOperation("查询所有用户")
    @PostMapping("/queryAll")
    public Result<?> queryAll(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                              @RequestBody(required = false) UserQueryVo userQuery) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        String realName = userQuery.getRealName();
        String roleId = userQuery.getRoleId();
        if (!StringUtils.isEmpty(realName)) {
            wrapper.lambda().like(SysUser::getRealName, realName);
        }
        if (!StringUtils.isEmpty(roleId)) {
            wrapper.lambda().eq(SysUser::getRoleId, roleId);
        }
        Page<SysUser> page = new Page<>(pageNo, pageSize);
        IPage<SysUser> sysUserIPage = sysUserService.page(page, wrapper);
        return Result.OK(sysUserIPage);
    }

    @ApiOperation("校验账号唯一")
    @GetMapping("/checkUsername")
    public Result<Boolean> checkUsername(String userId, String username) {
        Result<Boolean> result = new Result<>();
        result.setResult(false);
        log.info("--验证账号是否唯一---id:" + userId + "--roleCode:" + username);
        boolean checkName = sysUserService.checkUsername(userId, username);
        if (checkName) {
            result.setResult(true);
            result.success("账号已存在");
            return result;
        }
        return result;
    }

    @ApiOperation("通过excel导出数据")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SysUser sysUser) {
        return super.exportXls(request, sysUser, SysUser.class, "用户信息");
    }

    @ApiOperation("通过excel导入数据")
    @PostMapping("/importExcel")
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, SysUser.class);
    }


}

