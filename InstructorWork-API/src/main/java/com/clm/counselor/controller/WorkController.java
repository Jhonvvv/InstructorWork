package com.clm.counselor.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clm.common.base.controller.ClmController;
import com.clm.common.utils.Result;
import com.clm.counselor.entity.DailyWork;
import com.clm.counselor.entity.Work;
import com.clm.counselor.service.DailyWorkService;
import com.clm.counselor.service.WorkService;
import com.clm.system.entity.SysUser;
import com.clm.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工作表 前端控制器
 * </p>
 *
 * @author su
 * @since 2021-12-09
 */
@Api(tags = "工作管理")
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/counselor/work")
public class WorkController extends ClmController<Work, WorkService> {

    @Autowired
    private WorkService workService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private DailyWorkService dailyWorkService;

    @ApiOperation("添加工作")
    @PostMapping("/add")
    public Result<?> add(@RequestBody Work work) {
        Result<Work> result = new Result<>();
        try {
            Work addWork = workService.addWork(work);
            result.setResult(addWork);
            result.success("操作成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }

        return result;
    }

    @ApiOperation("修改工作")
    @PostMapping("/edit")
    public Result<?> edit(@RequestBody Work work) {
        Result<Work> result = new Result<>();
        try {
            Work editWork = workService.editWork(work);
            result.setResult(editWork);
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
            if (StringUtils.isNotEmpty(id)) {
                boolean deleteWork = workService.deleteWork(id);
                if (deleteWork) {
                    result.success("操作成功");
                } else {
                    result.error20001("操作失败");
                }

            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }

        return result;
    }

    @ApiOperation("查询未完成工作")
    @GetMapping("/queryWorkNotFinish")
    public Result<?> queryWorkNotFinish(@RequestParam(value = "username") String username) {
        Result<List<Work>> result = new Result<>();
        if (StringUtils.isNotEmpty(username)) {
            SysUser user = sysUserService.getOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, username));
            List<Work> works = workService.queryWorkNotFinish(user.getId());
            result.setResult(works);
            result.success("查询成功");
        } else {
            result.error20001("查询失败");
        }
        return result;
    }

    @ApiOperation("查询每日工作模板")
    @GetMapping("/queryWork")
    public Result<?> queryWork(@RequestParam(value = "username") String username) {
        Result<List<Work>> result = new Result<>();
        if (StringUtils.isNotEmpty(username)) {
            SysUser user = sysUserService.getOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, username));
            List<Work> works = workService.list(new QueryWrapper<Work>().lambda().eq(Work::getUserId, user.getId()));
            result.setResult(works);
            result.success("查询成功");
        } else {
            result.error20001("查询失败");
        }
        return result;
    }

    @ApiOperation("查询每日工作流程")
    @GetMapping("/queryWorkFlow")
    public Result<?> queryWorkFlow(@RequestParam(value = "username") String username) {
        Result<Map<String, Object>> result = new Result<>();
        if (StringUtils.isNotEmpty(username)) {
            SysUser user = sysUserService.getOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, username));
            List<Work> workNotFinish = workService.queryWorkNotFinish(user.getId());
            List<DailyWork> finishDailyWork = dailyWorkService.queryFinishDailyWork(user.getId());
            List<DailyWork> provisional = dailyWorkService.queryProvisional(user.getId());
            Map<String, Object> map = new HashMap<>();
            map.put("workNorFinish", workNotFinish);
            map.put("workFinish", finishDailyWork);
            map.put("workProvisional", provisional);
            result.setResult(map);
            result.success("查询成功");
        } else {
            result.error20001("查询失败");
        }
        return result;
    }

    @ApiOperation("通过excel导出数据模板")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Work work) {
        SysUser user = sysUserService.getOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, work.getUserId()));
        QueryWrapper<Work> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Work::getUserId, user.getId()).last("limit 1");
        List<Work> list = workService.list(queryWrapper);
        if (list.size()==0) {
            list = new ArrayList<>();
            work.setUserId(user.getId());
            list.add(work);
        }
        return super.exportModeXls(request, list, Work.class, "工作信息模板");
    }

    @ApiOperation("通过excel导入数据")
    @PostMapping("/importExcel")
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Work.class);
    }
}

