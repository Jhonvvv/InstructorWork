package com.clm.counselor.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.common.base.constant.CommonConstant;
import com.clm.common.utils.Result;
import com.clm.counselor.entity.Scholarship;
import com.clm.counselor.service.ScholarshipService;
import com.clm.vo.ResisterStudentVo;
import com.clm.vo.ScholarshipVo;
import com.clm.vo.UserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 奖学金表 前端控制器
 * </p>
 *
 * @author su
 * @since 2021-12-27
 */

@Api(tags = "奖学金申请管理")
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/counselor/scholarship")
public class ScholarshipController {

    @Autowired
    private ScholarshipService scholarshipService;

    @ApiOperation("新增奖学金申请")
    @PostMapping("/add")
    public Result<?> add(@RequestBody Scholarship scholarship) {
        Result<Scholarship> result = new Result<>();
        try {
            Scholarship addScholarship = scholarshipService.addScholarship(scholarship);
            result.setResult(addScholarship);
            result.success("操作成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("修改奖学金申请")
    @PostMapping("/edit")
    public Result<?> edit(@RequestBody Scholarship scholarship) {
        Result<Scholarship> result = new Result<>();
        try {
            scholarshipService.updateById(scholarship);
            result.setResult(scholarship);
            result.success("操作成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("删除奖学金申请")
    @DeleteMapping("/delete")
    public Result<?> delete(@RequestParam(value = "id") String id) {
        Result<Scholarship> result = new Result<>();
        try {
            if (StringUtils.isNotEmpty(id)) {
                Scholarship scholarship = scholarshipService.getById(id);
                if (scholarship.getApproveStatus() == CommonConstant.APPROVE_STATUS_0) {
                    boolean removeById = scholarshipService.removeById(id);
                    if (removeById) {
                        result.success("操作成功");
                        return result;
                    } else {
                        result.error20001("操作失败");
                    }
                }
                result.error20001("操作失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("查询学生的奖学金申请")
    @GetMapping("/queryScholarshipByUsername")
    public Result<?> queryScholarshipByUsername(@RequestParam(value = "username") String username) {
        Result<List<Scholarship>> result=new Result<>();
        if (StringUtils.isNotEmpty(username)){
            List<Scholarship> scholarships = scholarshipService.queryScholarshipByUsername(username);
            result.setResult(scholarships);
            result.success("操作成功");
        }else {
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("查询辅导员管理学生的奖学金申请")
    @PostMapping("/queryScholarshipByCounselor")
    public Result<?> queryScholarshipByCounselor(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                 @RequestBody(required = true) UserQueryVo userQuery) {
        Result<IPage<ScholarshipVo>> result=new Result<>();
        if (StringUtils.isNotEmpty(userQuery.getUsername())){
            Page<ScholarshipVo> page=new Page<>(pageNo,pageSize);
            IPage<ScholarshipVo> scholarshipVoIPage = scholarshipService.queryScholarshipByCounselor(page, userQuery);
            result.setResult(scholarshipVoIPage);
            result.success("操作成功");
        }else {
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("辅导员通过奖学金申请")
    @PostMapping("/passScholarship")
    public Result<?> passScholarship(@RequestBody ScholarshipVo scholarshipVo) {
        Result<ScholarshipVo> result=new Result<>();
        if (scholarshipVo!=null){
            ScholarshipVo passScholarship = scholarshipService.passScholarship(scholarshipVo);
            result.setResult(passScholarship);
            result.success("操作成功");
        }else {
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("辅导员拒绝奖学金申请")
    @PostMapping("/rejectScholarship")
    public Result<?> rejectScholarship(@RequestBody ScholarshipVo scholarshipVo) {
        Result<ScholarshipVo> result=new Result<>();
        if (scholarshipVo!=null){
            ScholarshipVo rejectScholarship = scholarshipService.rejectScholarship(scholarshipVo);
            result.setResult(rejectScholarship);
            result.success("操作成功");
        }else {
            result.error20001("操作失败");
        }
        return result;
    }
}

