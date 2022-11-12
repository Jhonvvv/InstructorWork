package com.clm.counselor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.common.base.constant.CommonConstant;
import com.clm.counselor.entity.Scholarship;
import com.clm.counselor.entity.ScholarshipApprove;
import com.clm.counselor.mapper.ScholarshipApproveMapper;
import com.clm.counselor.mapper.ScholarshipMapper;
import com.clm.counselor.service.ScholarshipService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clm.system.entity.SysUser;
import com.clm.system.mapper.SysUserMapper;
import com.clm.vo.ScholarshipVo;
import com.clm.vo.UserQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 奖学金表 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-12-27
 */
@Service
public class ScholarshipServiceImpl extends ServiceImpl<ScholarshipMapper, Scholarship> implements ScholarshipService {

    @Autowired
    private ScholarshipMapper scholarshipMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ScholarshipApproveMapper scholarshipApproveMapper;

    @Override
    public Scholarship addScholarship(Scholarship scholarship) {
        SysUser user = getUserInfo(scholarship.getUserId());
        if (user!=null){
            scholarship.setUserId(user.getId());
            scholarship.setDepartId(user.getDepartId());
            scholarship.setApproveStatus(CommonConstant.APPROVE_STATUS_0);
            this.save(scholarship);
            return scholarship;
        }
        return null;
    }

    @Override
    public List<Scholarship> queryScholarshipByUsername(String username) {
        SysUser userInfo = getUserInfo(username);
        if (userInfo!=null){
            List<Scholarship> list = this.list(new QueryWrapper<Scholarship>().lambda().eq(Scholarship::getUserId, userInfo.getId()));
            return list;
        }
        return null;
    }

    @Override
    public IPage<ScholarshipVo> queryScholarshipByCounselor(Page<ScholarshipVo> page, UserQueryVo userQuery) {
        SysUser userInfo = getUserInfo(userQuery.getUsername());
        if (StringUtils.isEmpty(userQuery.getRealName()) && StringUtils.isEmpty(userQuery.getDepartId())) {
            return scholarshipMapper.queryScholarshipByCounselor(page, userInfo.getDepartIds().split(","));
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
        return scholarshipMapper.queryScholarshipByCounselorRequirement(page,userIds.toArray(new String[userIds.size()]));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ScholarshipVo passScholarship(ScholarshipVo scholarshipVo) {
        Scholarship scholarship=new Scholarship();
        BeanUtils.copyProperties(scholarshipVo,scholarship);
        scholarship.setApproveStatus(CommonConstant.APPROVE_STATUS_1);
        this.updateById(scholarship);
        ScholarshipApprove approve=new ScholarshipApprove();
        //设置审核记录
        approve.setScholarshipId(scholarship.getId());
        SysUser userInfo = getUserInfo(scholarshipVo.getCounselorName());
        approve.setCounselorName(userInfo.getRealName());
        approve.setBeforeStatus(CommonConstant.APPROVE_STATUS_0);//审核前状态 辅导员通过审核
        approve.setCurrentStatus(CommonConstant.APPROVE_STATUS_0);// 上级审核 未审核
        scholarshipApproveMapper.insert(approve);
        //设置vo
        scholarshipVo.setApproveStatus(CommonConstant.APPROVE_STATUS_1);
        return scholarshipVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ScholarshipVo rejectScholarship(ScholarshipVo scholarshipVo) {
        Scholarship scholarship=new Scholarship();
        BeanUtils.copyProperties(scholarshipVo,scholarship);
        scholarship.setApproveStatus(CommonConstant.APPROVE_STATUS_3);
        this.updateById(scholarship);
        ScholarshipApprove approve=new ScholarshipApprove();
        //设置审核记录
        approve.setScholarshipId(scholarship.getId());
        SysUser userInfo = getUserInfo(scholarshipVo.getCounselorName());
        approve.setCounselorName(userInfo.getRealName());
        approve.setBeforeStatus(CommonConstant.APPROVE_STATUS_1);//审核前状态 辅导员拒绝通过审核
        approve.setCurrentStatus(CommonConstant.APPROVE_STATUS_0);// 上级审核 未审核
        approve.setAfterStatus(CommonConstant.APPROVE_STATUS_1); //申请拒绝
        scholarshipApproveMapper.insert(approve);
        //设置vo
        scholarshipVo.setApproveStatus(CommonConstant.APPROVE_STATUS_3);
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
