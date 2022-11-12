package com.clm.counselor.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clm.common.utils.Result;
import com.clm.common.utils.ResultCode;
import com.clm.counselor.entity.Reward;
import com.clm.counselor.service.RewardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 奖励表 前端控制器
 * </p>
 *
 * @author su
 * @since 2021-12-07
 */
@Api(tags = "奖励管理")
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/counselor/reward")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @ApiOperation("新增奖励")
    @PostMapping("/add")
    public Result<?> add(@RequestBody Reward reward){
        Result<Reward> result=new Result<>();
        try {
            rewardService.save(reward);
            result.setResult(reward);
            result.success("操作成功");
            return result;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("查询学生奖励情况")
    @GetMapping("/getRewardByStudentId")
    public Result<?> getRewardByStudentId(@RequestParam(value = "id")String id){
        Result<List<Reward>> result=new Result<>();
        try {
            List<Reward> list = rewardService.list(new QueryWrapper<Reward>().lambda().eq(Reward::getStudentId, id));
            result.setResult(list);
            result.success("操作成功");
            return result;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }

}

