package com.clm.counselor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.common.base.constant.CommonConstant;
import com.clm.counselor.entity.Punishment;
import com.clm.counselor.entity.PunishmentApprove;
import com.clm.counselor.entity.Scholarship;
import com.clm.counselor.entity.ScholarshipApprove;
import com.clm.counselor.mapper.PunishmentApproveMapper;
import com.clm.counselor.mapper.PunishmentMapper;
import com.clm.counselor.service.PunishmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clm.system.entity.SysUser;
import com.clm.system.mapper.SysUserMapper;
import com.clm.vo.PunishmentVo;
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
 * 惩罚表 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-12-08
 */
@Service
public class PunishmentServiceImpl extends ServiceImpl<PunishmentMapper, Punishment> implements PunishmentService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PunishmentMapper punishmentMapper;
    @Autowired
    private PunishmentApproveMapper punishmentApproveMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Punishment applyPunishment(Punishment punishment) {
        punishment.setApproveStatus(CommonConstant.APPROVE_STATUS_1);
        punishment.setApproveType(CommonConstant.PUNISHMENT_TYPE_0);
        punishment.setIsRevoke(CommonConstant.PROVISIONAL_0);
        SysUser counselor = sysUserMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, punishment.getCounselorOpinion()));
        punishment.setCounselorOpinion(null);
        this.save(punishment);
        //设置审核记录
        PunishmentApprove approve=new PunishmentApprove();
        approve.setPunishmentId(punishment.getId());
        approve.setCounselorName(counselor.getRealName());
        approve.setBeforeStatus(CommonConstant.APPROVE_STATUS_0);//审核前状态 辅导员通过审核
        approve.setCurrentStatus(CommonConstant.APPROVE_STATUS_0);// 上级审核 未审核
        punishmentApproveMapper.insert(approve);
        return punishment;
    }

    @Override
    public List<Punishment> getPunishmentByUsername(String username) {
        SysUser user = getUserInfo(username);
        QueryWrapper<Punishment> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Punishment::getUserId, user.getId());
        wrapper.lambda().eq(Punishment::getApproveStatus, CommonConstant.APPROVE_STATUS_2);
        wrapper.lambda().eq(Punishment::getApproveType,CommonConstant.PUNISHMENT_REVOKE_0);
        List<Punishment> list = this.list(wrapper);
        return list;
    }

    @Override
    public List<Punishment> getPunishmentByStudentId(String StudentId) {
        QueryWrapper<Punishment> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Punishment::getStudentId, StudentId);
        wrapper.lambda().eq(Punishment::getApproveType,CommonConstant.PUNISHMENT_TYPE_0);
        List<Punishment> list = this.list(wrapper);
        return list;
    }

    @Override
    public Punishment applyRevoke(Punishment punishment) {
        QueryWrapper<Punishment> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Punishment::getPunishmentId, punishment.getId());
        wrapper.lambda().eq(Punishment::getApproveType, CommonConstant.PUNISHMENT_REVOKE_1);
        Punishment revokePunishment = this.getOne(wrapper);
        //判断是新增还是修改
        if (revokePunishment == null) {
            revokePunishment = new Punishment();
            Punishment noRevokePunishment = this.getById(punishment.getId());
            BeanUtils.copyProperties(noRevokePunishment, revokePunishment);
            revokePunishment.setId(null);
            revokePunishment.setObjection(punishment.getObjection());
            revokePunishment.setAuditOpinion(null);
            revokePunishment.setApproveType(CommonConstant.PUNISHMENT_TYPE_1);
            revokePunishment.setPunishmentId(noRevokePunishment.getId());
            revokePunishment.setApproveStatus(CommonConstant.APPROVE_STATUS_0);
            this.save(revokePunishment);
        } else if (revokePunishment != null) {
            revokePunishment.setObjection(punishment.getObjection());
            revokePunishment.setProve(punishment.getProve());
            this.updateById(revokePunishment);
        }
        return revokePunishment;
    }

    @Override
    public Punishment getRevokePunishment(String id) {
        QueryWrapper<Punishment> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Punishment::getPunishmentId, id);
        wrapper.lambda().eq(Punishment::getApproveType, CommonConstant.PUNISHMENT_REVOKE_1);
        Punishment one = this.getOne(wrapper);
        return one;
    }

    @Override
    public IPage<PunishmentVo> getRevokePunishmentByCounselor(Page<PunishmentVo> page, UserQueryVo userQuery) {
        SysUser userInfo = getUserInfo(userQuery.getUsername());
        if (StringUtils.isEmpty(userQuery.getRealName()) && StringUtils.isEmpty(userQuery.getDepartId())) {
            return punishmentMapper.queryRevokePunishmentByCounselor(page, userInfo.getDepartIds().split(","));
        }
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        if (StringUtils.isNotEmpty(userQuery.getRealName())) {
            queryWrapper.lambda().like(SysUser::getRealName, userQuery.getRealName());
        }
        if (StringUtils.isNotEmpty(userQuery.getDepartId())){
            queryWrapper.lambda().eq(SysUser::getDepartId, userQuery.getDepartId());
        }
        List<SysUser> sysUsers = sysUserMapper.selectList(queryWrapper);
        if (sysUsers.size()==0) return new Page<PunishmentVo>();
        List<String> userIds=sysUsers.stream().map(SysUser::getId).collect(Collectors.toList());
        return punishmentMapper.queryRevokePunishmentCounselorRequirement(page,userIds.toArray(new String[userIds.size()]));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PunishmentVo passRevokePunishment(PunishmentVo punishmentVo) {
        Punishment punishment=new Punishment();
        BeanUtils.copyProperties(punishmentVo,punishment);
        punishment.setApproveStatus(CommonConstant.APPROVE_STATUS_1);
        this.updateById(punishment);
        PunishmentApprove approve=new PunishmentApprove();
        //设置审核记录
        approve.setPunishmentId(punishment.getId());
        SysUser userInfo = getUserInfo(punishmentVo.getCounselorName());
        approve.setCounselorName(userInfo.getRealName());
        approve.setBeforeStatus(CommonConstant.APPROVE_STATUS_0);//审核前状态 辅导员通过审核
        approve.setCurrentStatus(CommonConstant.APPROVE_STATUS_0);// 上级审核 未审核
        punishmentApproveMapper.insert(approve);
        //设置vo
        punishmentVo.setApproveStatus(CommonConstant.APPROVE_STATUS_1);
        return punishmentVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PunishmentVo rejectRevokePunishment(PunishmentVo punishmentVo) {
        Punishment punishment=new Punishment();
        BeanUtils.copyProperties(punishmentVo,punishment);
        punishment.setApproveStatus(CommonConstant.APPROVE_STATUS_3);
        this.updateById(punishment);
        PunishmentApprove approve=new PunishmentApprove();
        //设置审核记录
        approve.setPunishmentId(punishment.getId());
        SysUser userInfo = getUserInfo(punishmentVo.getCounselorName());
        approve.setCounselorName(userInfo.getRealName());
        approve.setBeforeStatus(CommonConstant.APPROVE_STATUS_1);//审核前状态 辅导员拒绝通过审核
        approve.setCurrentStatus(CommonConstant.APPROVE_STATUS_0);// 上级审核 未审核
        approve.setAfterStatus(CommonConstant.APPROVE_STATUS_1); //申请拒绝
        punishmentApproveMapper.insert(approve);
        //设置vo
        punishmentVo.setApproveStatus(CommonConstant.APPROVE_STATUS_3);
        return punishmentVo;
    }

    /**
     * 查询用户信息
     *
     * @param username
     */
    private SysUser getUserInfo(String username) {
        SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, username));
        return user;
    }
}
