package com.clm.counselor.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clm.common.base.constant.CommonConstant;
import com.clm.common.utils.DateUtils;
import com.clm.common.utils.Result;
import com.clm.counselor.entity.DailyWork;
import com.clm.counselor.entity.Work;
import com.clm.counselor.service.DailyWorkService;
import com.clm.system.entity.SysUser;
import com.clm.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 工作表 前端控制器
 * </p>
 *
 * @author su
 * @since 2021-12-09
 */
@Api(tags = "日常工作管理")
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/counselor/dailyWork")
public class DailyWorkController {
    @Autowired
    private DailyWorkService dailyWorkService;
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("添加临时工作")
    @PostMapping("/add")
    public Result<?> add(@RequestBody Work work) {
        Result<DailyWork> result = new Result<>();
        try {
            DailyWork provisional = dailyWorkService.addProvisional(work);
            result.setResult(provisional);
            result.success("操作成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("删除工作")
    @DeleteMapping("/delete")
    public Result<?> delete(@RequestParam(value = "id") String id) {
        Result<Work> result = new Result<>();
        try {
            dailyWorkService.removeById(id);
            result.success("操作成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("修改临时工作")
    @PostMapping("/edit")
    public Result<?> edit(@RequestBody Work work) {
        Result<DailyWork> result = new Result<>();
        try {
            DailyWork provisional = dailyWorkService.editProvisional(work);
            result.setResult(provisional);
            result.success("操作成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("查询当前已完成工作")
    @GetMapping("/queryFinishDailyWork")
    public Result<?> queryFinishDailyWork(@RequestParam(value = "username") String username) {
        Result<List<DailyWork>> result = new Result<>();
        if (StringUtils.isNotEmpty(username)) {
            SysUser user = sysUserService.getOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, username));
            List<DailyWork> dailyWorks = dailyWorkService.queryFinishDailyWork(user.getId());
            result.setResult(dailyWorks);
            result.success("操作成功");
        }
        return result;

    }

    @ApiOperation("完成当前工作")
    @PostMapping("/finishDailyWork")
    public Result<?> finishDailyWork(@RequestParam(value = "id") String id) {
        Result<DailyWork> result = new Result<>();
        if(StringUtils.isNotEmpty(id)){
            DailyWork dailyWork = dailyWorkService.finishDailyWork(id);
            result.setResult(dailyWork);
            result.success("操作成功");
        }else {
            result.error20001("操作失败");
        }

        return result;
    }

    @ApiOperation("取消完成当前工作")
    @PostMapping("/revokeFinishDailyWork")
    public Result<?> revokeFinishDailyWork(@RequestParam(value = "id") String id) {
        Result<DailyWork> result = new Result<>();
        if(StringUtils.isNotEmpty(id)){
            DailyWork dailyWork = dailyWorkService.revokeFinishDailyWork(id);
            result.setResult(dailyWork);
            result.success("操作成功");
        }else {
            result.error20001("操作失败");
        }

        return result;
    }

    @ApiOperation("查询临时工作")
    @GetMapping("/queryProvisional")
    public Result<?> queryProvisional(@RequestParam(value = "username") String username) {
        Result<List<DailyWork>> result = new Result<>();
        if (StringUtils.isNotEmpty(username)) {
            SysUser user = sysUserService.getOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, username));
            List<DailyWork> dailyWorks = dailyWorkService.queryProvisional(user.getId());
            result.setResult(dailyWorks);
            result.success("操作成功");
        }
        return result;
    }


}

