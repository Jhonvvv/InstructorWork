package com.clm.counselor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clm.common.base.constant.CommonConstant;
import com.clm.common.utils.DateUtils;
import com.clm.counselor.entity.DailyWork;
import com.clm.counselor.entity.Work;
import com.clm.counselor.mapper.DailyWorkMapper;
import com.clm.counselor.mapper.WorkMapper;
import com.clm.counselor.service.DailyWorkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clm.counselor.service.WorkService;
import com.clm.system.entity.SysUser;
import com.clm.system.mapper.SysUserMapper;
import com.clm.system.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 工作表 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-12-09
 */
@Service
public class DailyWorkServiceImpl extends ServiceImpl<DailyWorkMapper, DailyWork> implements DailyWorkService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private WorkMapper workMapper;

    @Override
    public List<DailyWork> queryFinishDailyWork(String userId) {
        QueryWrapper<DailyWork> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(DailyWork::getIsFinish, CommonConstant.Finish_1);
        wrapper.lambda().eq(DailyWork::getUserId,userId);
        Date start = DateUtils.customNowTime(CommonConstant.START_DAY);
        Date end = DateUtils.customNowTime(CommonConstant.END_DAY);
        wrapper.lambda().between(DailyWork::getStartTime, start, end);
        List<DailyWork> dailyWorks = baseMapper.selectList(wrapper);
        return dailyWorks;
    }

    @Override
    public List<DailyWork> queryProvisional(String userId) {
        QueryWrapper<DailyWork> wrapper = new QueryWrapper<>();
        Date start = DateUtils.customNowTime(CommonConstant.START_DAY);
        Date end = DateUtils.customNowTime(CommonConstant.END_DAY);
        wrapper.lambda().eq(DailyWork::getIsProvisional, CommonConstant.PROVISIONAL_1);
        wrapper.lambda().eq(DailyWork::getUserId, userId);
        wrapper.lambda().between(DailyWork::getStartTime, start, end);
        wrapper.lambda().orderByAsc(DailyWork::getStartTime);
        List<DailyWork> dailyWorks = baseMapper.selectList(wrapper);
        return dailyWorks;
    }

    @Override
    public DailyWork addProvisional(Work work) {
        if (StringUtils.isNotEmpty(work.getUserId())) {
            SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, work.getUserId()));
            DailyWork dailyWork = new DailyWork(work);
            dailyWork.setUserId(user.getId());
            dailyWork.setIsProvisional(CommonConstant.PROVISIONAL_1);
            dailyWork.setStartTime(DateUtils.customNowTime(work.getStartTime()));
            baseMapper.insert(dailyWork);
            return dailyWork;
        } else {
            return null;
        }

    }

    @Override
    public DailyWork editProvisional(Work work) {
        DailyWork dailyWork = baseMapper.selectById(work.getId());
        BeanUtils.copyProperties(work, dailyWork);
        dailyWork.setStartTime(DateUtils.customNowTime(work.getStartTime()));
        baseMapper.updateById(dailyWork);
        return dailyWork;
    }

    @Override
    public DailyWork finishDailyWork(String dailyWorkId) {
        DailyWork dailyWork = baseMapper.selectById(dailyWorkId);
        if (dailyWork == null) {
            DailyWork toDateDailyWork = toDateDailyWork(dailyWorkId);
            if (toDateDailyWork == null) {
                Work work = workMapper.selectById(dailyWorkId);
                dailyWork = new DailyWork(work);
                dailyWork.setEndTime(DateUtils.getDate());
                dailyWork.setIsFinish(CommonConstant.Finish_1);
                dailyWork.setStartTime(DateUtils.customNowTime(work.getStartTime()));
                baseMapper.insert(dailyWork);
                return dailyWork;
            } else {
                dailyWork = toDateDailyWork;
            }
        }
        dailyWork.setEndTime(DateUtils.getDate());
        dailyWork.setIsFinish(CommonConstant.Finish_1);
        baseMapper.updateById(dailyWork);
        return dailyWork;
    }

    @Override
    public DailyWork revokeFinishDailyWork(String dailyWorkId) {
        DailyWork dailyWork = baseMapper.selectById(dailyWorkId);
        if (dailyWork == null) {
            DailyWork toDateDailyWork = toDateDailyWork(dailyWorkId);
            if (toDateDailyWork != null) {
                 dailyWork=toDateDailyWork;
            }
        }
        dailyWork.setIsFinish(CommonConstant.Finish_0);
        baseMapper.updateById(dailyWork);
        return dailyWork;
    }

    private DailyWork toDateDailyWork(String id) {
        QueryWrapper<DailyWork> queryWrapper = new QueryWrapper<>();
        Date start = DateUtils.customNowTime(CommonConstant.START_DAY);
        Date end = DateUtils.customNowTime(CommonConstant.END_DAY);
        queryWrapper.lambda().between(DailyWork::getStartTime, start, end);
        queryWrapper.lambda().eq(DailyWork::getWorkId, id);
        DailyWork dailyWork = baseMapper.selectOne(queryWrapper);
        return dailyWork;
    }
}
