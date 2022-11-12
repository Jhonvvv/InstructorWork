package com.clm.counselor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.common.utils.Result;
import com.clm.counselor.entity.Punishment;
import com.clm.counselor.entity.Reward;
import com.clm.counselor.entity.Student;
import com.clm.counselor.mapper.PunishmentMapper;
import com.clm.counselor.mapper.RewardMapper;
import com.clm.counselor.mapper.StudentMapper;
import com.clm.counselor.service.PunishmentService;
import com.clm.counselor.service.RewardService;
import com.clm.counselor.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clm.system.entity.SysUser;
import com.clm.system.mapper.SysUserMapper;
import com.clm.system.service.SysUserService;
import com.clm.vo.ResisterStudentVo;
import com.clm.vo.UserQueryVo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 学生信息表 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-12-05
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RewardMapper rewardMapper;
    @Autowired
    private PunishmentMapper punishmentMapper;

    @Override
    public IPage<ResisterStudentVo> queryUserAndStudent(Page<ResisterStudentVo> page, String[] departIds, UserQueryVo userQuery) {

        if (StringUtils.isEmpty(userQuery.getRealName()) && StringUtils.isEmpty(userQuery.getDepartId())) {
            return baseMapper.queryUserAndStudent(page, departIds);
        }
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(userQuery.getRealName())) {
            queryWrapper.lambda().like(SysUser::getRealName, userQuery.getRealName());
        }
        if (StringUtils.isNotEmpty(userQuery.getDepartId())) {
            queryWrapper.lambda().eq(SysUser::getDepartId, userQuery.getDepartId());
        }
        return baseMapper.queryUserAndStudentByRequirement(page, departIds, queryWrapper);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteStudent(String studentId) {
        Student student = baseMapper.selectById(studentId);
        if (student != null) {
            sysUserService.deleteUser(student.getUserId());
            rewardMapper.delete(new QueryWrapper<Reward>().lambda().eq(Reward::getStudentId,student.getId()));
            punishmentMapper.delete(new QueryWrapper<Punishment>().lambda().eq(Punishment::getStudentId,student.getId()));
            baseMapper.deleteById(studentId);
            return true;
        }
        return false;
    }


}
