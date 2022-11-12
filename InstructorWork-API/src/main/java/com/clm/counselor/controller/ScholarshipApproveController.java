package com.clm.counselor.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.common.utils.Result;
import com.clm.counselor.service.ScholarshipApproveService;
import com.clm.vo.ScholarshipVo;
import com.clm.vo.UserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author su
 * @since 2021-12-27
 */
@Api(tags = "奖学金申请审核管理")
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/counselor/scholarshipApprove")
public class ScholarshipApproveController {

    @Autowired
    private ScholarshipApproveService scholarshipApproveService;

    @ApiOperation("查询辅导员通过的奖学金申请")
    @PostMapping("/queryScholarshipApproveByCounselor")
    public Result<?> queryScholarshipApproveByCounselor(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                 @RequestBody(required = true) UserQueryVo userQuery) {
        Result<IPage<ScholarshipVo>> result=new Result<>();
        if (StringUtils.isNotEmpty(userQuery.getUsername())){
            Page<ScholarshipVo> page=new Page<>(pageNo,pageSize);
            IPage<ScholarshipVo> scholarshipVoIPage = scholarshipApproveService.queryScholarshipApproveByCounselor(page, userQuery);
            result.setResult(scholarshipVoIPage);
            result.success("操作成功");
        }else {
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("通过奖学金申请")
    @PostMapping("/passScholarship")
    public Result<?> passScholarship(@RequestBody ScholarshipVo scholarshipVo) {
        Result<ScholarshipVo> result=new Result<>();
        if (scholarshipVo!=null){
            ScholarshipVo passScholarship = scholarshipApproveService.passScholarship(scholarshipVo);
            result.setResult(passScholarship);
            result.success("操作成功");
        }else {
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("拒绝奖学金申请")
    @PostMapping("/rejectScholarship")
    public Result<?> rejectScholarship(@RequestBody ScholarshipVo scholarshipVo) {
        Result<ScholarshipVo> result=new Result<>();
        if (scholarshipVo!=null){
            ScholarshipVo rejectScholarship = scholarshipApproveService.rejectScholarship(scholarshipVo);
            result.setResult(rejectScholarship);
            result.success("操作成功");
        }else {
            result.error20001("操作失败");
        }
        return result;
    }

}

