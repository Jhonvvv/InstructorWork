package com.clm.counselor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.common.base.constant.CommonConstant;
import com.clm.counselor.entity.Scholarship;
import com.clm.counselor.entity.ScholarshipApprove;
import com.clm.counselor.mapper.ScholarshipApproveMapper;
import com.clm.counselor.mapper.ScholarshipMapper;
import com.clm.counselor.service.ScholarshipApproveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clm.system.entity.SysUser;
import com.clm.system.mapper.SysUserMapper;
import com.clm.vo.ScholarshipVo;
import com.clm.vo.UserQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author su
 * @since 2021-12-27
 */
@Service
public class ScholarshipApproveServiceImpl extends ServiceImpl<ScholarshipApproveMapper, ScholarshipApprove> implements ScholarshipApproveService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ScholarshipApproveMapper scholarshipApproveMapper;
    @Autowired
    private ScholarshipMapper scholarshipMapper;

    @Override
    public IPage<ScholarshipVo> queryScholarshipApproveByCounselor(Page<ScholarshipVo> page, UserQueryVo userQuery) {
        SysUser userInfo = getUserInfo(userQuery.getUsername());
        if (StringUtils.isEmpty(userQuery.getRealName()) && StringUtils.isEmpty(userQuery.getDepartId())) {
            return scholarshipApproveMapper.queryScholarshipApproveByCounselor(page, userInfo.getDepartIds().split(","));
        }
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        if (StringUtils.isNotEmpty(userQuery.getRealName())) {
            queryWrapper.lambda().like(SysUser::getRealName, userQuery.getRealName());
        }
        if (StringUtils.isNotEmpty(userQuery.getDepartId())){
            queryWrapper.lambda().eq(SysUser::getDepartId, userQuery.getDepartId());
        }
        List<SysUser> sysUsers = sysUserMapper.selectList(queryWrapper);
        if (sysUsers.size()==0) return new Page<ScholarshipVo>();
        List<String> userIds=sysUsers.stream().map(SysUser::getId).collect(Collectors.toList());
        return scholarshipApproveMapper.queryScholarshipApproveByCounselorRequirement(page,userIds.toArray(new String[userIds.size()]));
    }

    @Override
    public ScholarshipVo passScholarship(ScholarshipVo scholarshipVo) {
        Scholarship scholarship=new Scholarship();
        BeanUtils.copyProperties(scholarshipVo,scholarship);
        scholarship.setApproveStatus(CommonConstant.APPROVE_STATUS_2);
        scholarshipMapper.updateById(scholarship);
        //设置审核记录
        ScholarshipApprove approve = scholarshipApproveMapper.selectOne(new QueryWrapper<ScholarshipApprove>().
                lambda().eq(ScholarshipApprove::getScholarshipId, scholarshipVo.getId()));
        approve.setCurrentStatus(CommonConstant.APPROVE_STATUS_1);// 当前审核状态 已审核
        approve.setAfterStatus(CommonConstant.APPROVE_STATUS_0);//审核后状态 通过审核
        scholarshipApproveMapper.updateById(approve);
        //设置vo
        scholarshipVo.setCurrentStatus(CommonConstant.APPROVE_STATUS_1);// 当前审核状态 已审核
        scholarshipVo.setAfterStatus(CommonConstant.APPROVE_STATUS_0);//审核后状态 通过审核
        return scholarshipVo;
    }

    @Override
    public ScholarshipVo rejectScholarship(ScholarshipVo scholarshipVo) {
        Scholarship scholarship=new Scholarship();
        BeanUtils.copyProperties(scholarshipVo,scholarship);
        scholarship.setApproveStatus(CommonConstant.APPROVE_STATUS_3);
        scholarshipMapper.updateById(scholarship);
        //设置审核记录
        ScholarshipApprove approve = scholarshipApproveMapper.selectOne(new QueryWrapper<ScholarshipApprove>().
                lambda().eq(ScholarshipApprove::getScholarshipId, scholarshipVo.getId()));
        approve.setCurrentStatus(CommonConstant.APPROVE_STATUS_1);// 当前审核状态 已审核
        approve.setAfterStatus(CommonConstant.APPROVE_STATUS_1);//审核后状态 拒绝审核
        scholarshipApproveMapper.updateById(approve);
        //设置vo
        scholarshipVo.setCurrentStatus(CommonConstant.APPROVE_STATUS_1);// 当前审核状态 已审核
        scholarshipVo.setAfterStatus(CommonConstant.APPROVE_STATUS_1);//审核后状态 拒绝审核
        return scholarshipVo;
    }

    /**
     * 查询用户信息
     * @param username
     */
    private SysUser getUserInfo(String username){
        SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, username));
        return user;
    }
}
