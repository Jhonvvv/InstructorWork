package com.clm.counselor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clm.common.base.constant.CommonConstant;
import com.clm.common.utils.DateUtils;
import com.clm.counselor.entity.DailyWork;
import com.clm.counselor.entity.Work;
import com.clm.counselor.mapper.DailyWorkMapper;
import com.clm.counselor.mapper.WorkMapper;
import com.clm.counselor.service.DailyWorkService;
import com.clm.counselor.service.WorkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clm.system.entity.SysUser;
import com.clm.system.mapper.SysUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 日常工作表 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-12-09
 */
@Service
public class WorkServiceImpl extends ServiceImpl<WorkMapper, Work> implements WorkService {

    @Autowired
    private DailyWorkMapper dailyWorkMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Work addWork(Work work) {
        if (StringUtils.isNotEmpty(work.getUserId())) {
            SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, work.getUserId()));
            work.setUserId(user.getId());
            baseMapper.insert(work);
            DailyWork dailyWork = new DailyWork(work);
            dailyWork.setStartTime(DateUtils.customNowTime(work.getStartTime()));
            dailyWorkMapper.insert(dailyWork);
            return work;
        } else {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Work editWork(Work work) {
        baseMapper.updateById(work);
        QueryWrapper<DailyWork> queryWrapper=new QueryWrapper<>();
        Date start = DateUtils.customNowTime(CommonConstant.START_DAY);
        Date end = DateUtils.customNowTime(CommonConstant.END_DAY);
        queryWrapper.lambda().between(DailyWork::getStartTime,start,end);
        queryWrapper.lambda().eq(DailyWork::getWorkId,work.getId());
        Integer count = dailyWorkMapper.selectCount(queryWrapper);
        if (count>0) {
            DailyWork dailyWork = dailyWorkMapper.selectOne(queryWrapper);
            String dailyWorkId=dailyWork.getId();
            BeanUtils.copyProperties(work,dailyWork);
            dailyWork.setId(dailyWorkId);
            dailyWorkMapper.updateById(dailyWork);
        }
        return work;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteWork(String workId) {
        QueryWrapper<DailyWork> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(DailyWork::getIsFinish,CommonConstant.Finish_0);
        queryWrapper.lambda().eq(DailyWork::getWorkId,workId);
        Date start = DateUtils.customNowTime(CommonConstant.START_DAY);
        Date end = DateUtils.customNowTime(CommonConstant.END_DAY);
        queryWrapper.lambda().between(DailyWork::getStartTime,start,end);
        DailyWork dailyWork = dailyWorkMapper.selectOne(queryWrapper);
        if (dailyWork!=null){
            dailyWorkMapper.delete(new QueryWrapper<DailyWork>().lambda().eq(DailyWork::getWorkId,workId));
        }
        baseMapper.deleteById(workId);
        return true;
    }

    @Override
    public List<Work> queryWorkNotFinish(String userId) {
        QueryWrapper<DailyWork> wrapper=new QueryWrapper<>();
        Date start = DateUtils.customNowTime(CommonConstant.START_DAY);
        Date end = DateUtils.customNowTime(CommonConstant.END_DAY);
        wrapper.lambda().between(DailyWork::getStartTime,start,end);
        List<Work> works = baseMapper.queryWorkNotFinish(userId,wrapper);
        return works;
    }
}
