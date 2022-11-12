package com.clm.counselor.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.common.base.constant.CommonConstant;
import com.clm.common.utils.Result;
import com.clm.counselor.entity.Punishment;
import com.clm.counselor.entity.Reward;
import com.clm.counselor.entity.Scholarship;
import com.clm.counselor.service.PunishmentService;
import com.clm.counselor.service.RewardService;
import com.clm.vo.PunishmentVo;
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
 * 惩罚表 前端控制器
 * </p>
 *
 * @author su
 * @since 2021-12-08
 */
@Api(tags = "惩罚管理")
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/counselor/punishment")
public class PunishmentController {
    @Autowired
    private PunishmentService punishmentService;

    @ApiOperation("新增处罚申请")
    @PostMapping("/add")
    public Result<?> add(@RequestBody Punishment punishment) {
        Result<Punishment> result = new Result<>();
        try {
            Punishment applyPunishment = punishmentService.applyPunishment(punishment);
            result.setResult(applyPunishment);
            result.success("操作成功");
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("修改处罚申请")
    @PostMapping("/update")
    public Result<?> update(@RequestBody Punishment punishment) {
        Result<Punishment> result = new Result<>();
        try {
            if (punishment.getApproveStatus() == CommonConstant.APPROVE_STATUS_0) {
                punishmentService.updateById(punishment);
                result.setResult(punishment);
                result.success("操作成功");
            }else {
                result.error20001("操作失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }


    @ApiOperation("删除撤销处罚申请")
    @DeleteMapping("/delete")
    public Result<?> delete(@RequestParam(value = "id") String id) {
        Result<Punishment> result = new Result<>();
        try {
            if (StringUtils.isNotEmpty(id)) {
                Punishment punishment = punishmentService.getById(id);
                if (punishment.getApproveStatus() == CommonConstant.APPROVE_STATUS_0) {
                    boolean removeById = punishmentService.removeById(id);
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

    @ApiOperation("新增撤销处罚申请")
    @PostMapping("/applyRevoke")
    public Result<?> applyRevoke(@RequestBody Punishment punishment) {
        Result<Punishment> result = new Result<>();
        Punishment revoke = punishmentService.applyRevoke(punishment);
        if (revoke != null) {
            result.setResult(revoke);
            result.success("操作成功");
        } else {
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("查询学生处罚情况通过学生ID")
    @GetMapping("/getPunishmentByStudentId")
    public Result<?> getPunishmentByStudentId(@RequestParam(value = "id") String id) {
        Result<List<Punishment>> result = new Result<>();
        try {
            List<Punishment> list = punishmentService.getPunishmentByStudentId(id);
            result.setResult(list);
            result.success("操作成功");
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }


    @ApiOperation("查询辅导员管理的撤销处罚申请")
    @PostMapping("/getRevokePunishmentByCounselor")
    public Result<?> getRevokePunishmentByCounselor(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    @RequestBody(required = true) UserQueryVo userQuery) {
        Result<IPage<PunishmentVo>> result = new Result<>();
        try {
            if (StringUtils.isNotEmpty(userQuery.getUsername())) {
                Page<PunishmentVo> page = new Page<>(pageNo, pageSize);
                IPage<PunishmentVo> revokePunishment = punishmentService.getRevokePunishmentByCounselor(page, userQuery);
                result.setResult(revokePunishment);
                result.success("操作成功");
            } else {
                result.error20001("操作失败");
            }
            result.success("操作成功");
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }


    @ApiOperation("查询学生处罚情况通过账号")
    @GetMapping("/getPunishmentByUsername")
    public Result<?> getPunishmentByUsername(@RequestParam(value = "username") String username) {
        Result<List<Punishment>> result = new Result<>();
        try {
            List<Punishment> punishments = punishmentService.getPunishmentByUsername(username);
            result.setResult(punishments);
            result.success("操作成功");
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("查询学生撤销处罚情况")
    @GetMapping("/getRevokePunishment")
    public Result<?> getRevokePunishmentByUsername(@RequestParam(value = "id") String id) {
        Result<Punishment> result = new Result<>();
        try {
            Punishment revokePunishment = punishmentService.getRevokePunishment(id);
            result.setResult(revokePunishment);
            result.success("操作成功");
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }


    @ApiOperation("查询学生历史处罚情况")
    @GetMapping("/getPunishmentHisByStudentId")
    public Result<?> getPunishmentHisByStudentId(@RequestParam(value = "id") String id) {
        Result<List<Punishment>> result = new Result<>();
        try {
            List<Punishment> list = punishmentService.list(new QueryWrapper<Punishment>().lambda().eq(Punishment::getStudentId, id));
            result.setResult(list);
            result.success("操作成功");
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("撤销学生处罚情况")
    @GetMapping("/revokePunishment")
    public Result<?> revokePunishment(@RequestParam(value = "id") String id) {
        Punishment punishment = punishmentService.getById(id);
        if (punishment != null) {
            punishment.setIsRevoke(CommonConstant.PUNISHMENT_REVOKE_1);
            punishmentService.updateById(punishment);
        } else {
            return Result.error("修改失败");
        }
        return Result.OK().success("修改成功");
    }

    @ApiOperation("辅导员通过奖学金申请")
    @PostMapping("/passRevokePunishment")
    public Result<?> passRevokePunishment(@RequestBody PunishmentVo punishmentVo) {
        Result<PunishmentVo> result=new Result<>();
        if (punishmentVo!=null){
            PunishmentVo passRevokePunishment = punishmentService.passRevokePunishment(punishmentVo);
            result.setResult(passRevokePunishment);
            result.success("操作成功");
        }else {
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("辅导员拒绝奖学金申请")
    @PostMapping("/rejectRevokePunishment")
    public Result<?> rejectRevokePunishment(@RequestBody PunishmentVo punishmentVo) {
        Result<PunishmentVo> result=new Result<>();
        if (punishmentVo!=null){
            PunishmentVo rejectRevokePunishment = punishmentService.rejectRevokePunishment(punishmentVo);
            result.setResult(rejectRevokePunishment);
            result.success("操作成功");
        }else {
            result.error20001("操作失败");
        }
        return result;
    }

}

