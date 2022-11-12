package com.clm.counselor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.counselor.entity.Punishment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clm.vo.PunishmentVo;
import com.clm.vo.ScholarshipVo;
import com.clm.vo.UserQueryVo;

import java.util.List;

/**
 * <p>
 * 惩罚表 服务类
 * </p>
 *
 * @author su
 * @since 2021-12-08
 */
public interface PunishmentService extends IService<Punishment> {

   Punishment  applyPunishment(Punishment punishment);

   List<Punishment> getPunishmentByUsername(String username);

   List<Punishment> getPunishmentByStudentId(String StudentId);

   Punishment applyRevoke(Punishment punishment);

   Punishment getRevokePunishment(String id);

   IPage<PunishmentVo> getRevokePunishmentByCounselor(Page<PunishmentVo> page, UserQueryVo userQuery);

   PunishmentVo passRevokePunishment(PunishmentVo punishmentVo);

   PunishmentVo rejectRevokePunishment(PunishmentVo punishmentVo);

}
