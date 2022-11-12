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
import com.clm.counselor.service.PunishmentApproveService;
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
 * 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-12-29
 */
@Service
public class PunishmentApproveServiceImpl extends ServiceImpl<PunishmentApproveMapper, PunishmentApprove> implements PunishmentApproveService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PunishmentMapper punishmentMapper;
    @Autowired
    private PunishmentApproveMapper punishmentApproveMapper;

    @Override
    public IPage<PunishmentVo> queryPunishmentByCounselor(Page<PunishmentVo> page, UserQueryVo userQuery) {
        SysUser userInfo = getUserInfo(userQuery.getUsername());
        if (StringUtils.isEmpty(userQuery.getRealName()) && StringUtils.isEmpty(userQuery.getDepartId())) {
            return punishmentApproveMapper.queryPunishmentByCounselor(page, userInfo.getDepartIds().split(","));
        }
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(userQuery.getRealName())) {
            queryWrapper.lambda().like(SysUser::getRealName, userQuery.getRealName());
        }
        if (StringUtils.isNotEmpty(userQuery.getDepartId())) {
            queryWrapper.lambda().eq(SysUser::getDepartId, userQuery.getDepartId());
        }
        List<SysUser> sysUsers = sysUserMapper.selectList(queryWrapper);
        if (sysUsers.size() == 0) return new Page<PunishmentVo>();
        List<String> userIds = sysUsers.stream().map(SysUser::getId).collect(Collectors.toList());
        return punishmentApproveMapper.queryPunishmentByCounselorRequirement(page, userIds.toArray(new String[userIds.size()]));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PunishmentVo passPunishment(PunishmentVo punishmentVo) {
        Punishment punishment = new Punishment();
        BeanUtils.copyProperties(punishmentVo, punishment);
        //撤销处罚申请
        if (punishment.getApproveType() == CommonConstant.PUNISHMENT_TYPE_1) {
            Punishment nowPunishment = punishmentMapper.selectById(punishment.getPunishmentId());
            nowPunishment.setIsRevoke(CommonConstant.PUNISHMENT_REVOKE_1);
            punishmentMapper.updateById(nowPunishment);
        }
        punishment.setApproveStatus(CommonConstant.APPROVE_STATUS_2);
        punishmentMapper.updateById(punishment);
        //设置审核记录
        PunishmentApprove approve = punishmentApproveMapper.selectOne(new QueryWrapper<PunishmentApprove>().
                lambda().eq(PunishmentApprove::getPunishmentId, punishment.getId()));
        approve.setCurrentStatus(CommonConstant.APPROVE_STATUS_1);// 当前审核状态 已审核
        approve.setAfterStatus(CommonConstant.APPROVE_STATUS_0);//审核后状态 通过审核
        punishmentApproveMapper.updateById(approve);
        //设置vo
        punishmentVo.setCurrentStatus(CommonConstant.APPROVE_STATUS_1);// 当前审核状态 已审核
        punishmentVo.setAfterStatus(CommonConstant.APPROVE_STATUS_0);//审核后状态 通过审核
        return punishmentVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PunishmentVo rejectPunishment(PunishmentVo punishmentVo) {
        Punishment punishment = new Punishment();
        BeanUtils.copyProperties(punishmentVo, punishment);
        punishment.setApproveStatus(CommonConstant.APPROVE_STATUS_3);
        punishmentMapper.updateById(punishment);
        //设置审核记录
        PunishmentApprove approve = punishmentApproveMapper.selectOne(new QueryWrapper<PunishmentApprove>().
                lambda().eq(PunishmentApprove::getPunishmentId, punishmentVo.getId()));
        approve.setCurrentStatus(CommonConstant.APPROVE_STATUS_1);// 当前审核状态 已审核
        approve.setAfterStatus(CommonConstant.APPROVE_STATUS_1);//审核后状态 拒绝审核
        punishmentApproveMapper.updateById(approve);
        //设置vo
        punishmentVo.setCurrentStatus(CommonConstant.APPROVE_STATUS_1);// 当前审核状态 已审核
        punishmentVo.setAfterStatus(CommonConstant.APPROVE_STATUS_1);//审核后状态 拒绝审核
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
