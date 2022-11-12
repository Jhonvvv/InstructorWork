package com.clm.counselor.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.counselor.entity.PunishmentApprove;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clm.vo.PunishmentVo;
import com.clm.vo.ScholarshipVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-12-29
 */
@Mapper
public interface PunishmentApproveMapper extends BaseMapper<PunishmentApprove> {

    IPage<PunishmentVo> queryPunishmentByCounselor(Page<PunishmentVo> page, @Param("departIds")String[] departIds);

    IPage<PunishmentVo> queryPunishmentByCounselorRequirement(Page<PunishmentVo> page,  @Param("userIds") String[] userIds );
}
