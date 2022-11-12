package com.clm.counselor.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.common.utils.Result;
import com.clm.counselor.entity.PunishmentApprove;
import com.clm.counselor.service.PunishmentApproveService;
import com.clm.counselor.service.ScholarshipApproveService;
import com.clm.vo.PunishmentVo;
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
 * @since 2021-12-29
 */
@Api(tags = "惩罚记录管理")
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/counselor/punishmentApprove")
public class PunishmentApproveController {

    @Autowired
    private PunishmentApproveService punishmentApproveService;

    @ApiOperation("查询处罚的申请")
    @PostMapping("/queryPunishmentByCounselor")
    public Result<?> queryPunishmentByCounselor(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        @RequestBody(required = true) UserQueryVo userQuery) {
        Result<IPage<PunishmentVo>> result=new Result<>();
        if (StringUtils.isNotEmpty(userQuery.getUsername())){
            Page<PunishmentVo> page=new Page<>(pageNo,pageSize);
            IPage<PunishmentVo> punishmentVoIPage = punishmentApproveService.queryPunishmentByCounselor(page, userQuery);
            result.setResult(punishmentVoIPage);
            result.success("操作成功");
        }else {
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("通过处罚申请")
    @PostMapping("/passPunishment")
    public Result<?> passPunishment(@RequestBody PunishmentVo punishmentVo) {
        Result<PunishmentVo> result=new Result<>();
        if (punishmentVo!=null){
            PunishmentVo passPunishment = punishmentApproveService.passPunishment(punishmentVo);
            result.setResult(passPunishment);
            result.success("操作成功");
        }else {
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("拒绝处罚申请")
    @PostMapping("/rejectPunishment")
    public Result<?> rejectPunishment(@RequestBody PunishmentVo punishmentVo) {
        Result<PunishmentVo> result=new Result<>();
        if (punishmentVo!=null){
            PunishmentVo rejectPunishment = punishmentApproveService.rejectPunishment(punishmentVo);
            result.setResult(rejectPunishment);
            result.success("操作成功");
        }else {
            result.error20001("操作失败");
        }
        return result;
    }
}

