package com.clm.counselor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.counselor.entity.PunishmentApprove;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clm.vo.PunishmentVo;
import com.clm.vo.ScholarshipVo;
import com.clm.vo.UserQueryVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author su
 * @since 2021-12-29
 */
public interface PunishmentApproveService extends IService<PunishmentApprove> {

    IPage<PunishmentVo> queryPunishmentByCounselor(Page<PunishmentVo> page, UserQueryVo userQuery);

    PunishmentVo passPunishment(PunishmentVo punishmentVo);

    PunishmentVo rejectPunishment(PunishmentVo punishmentVo);
}
