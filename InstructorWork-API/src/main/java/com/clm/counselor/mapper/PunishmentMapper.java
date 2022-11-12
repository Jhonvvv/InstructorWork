package com.clm.counselor.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.counselor.entity.Punishment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clm.vo.PunishmentVo;
import com.clm.vo.ScholarshipVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 惩罚表 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-12-08
 */
@Mapper
public interface PunishmentMapper extends BaseMapper<Punishment> {

    IPage<PunishmentVo> queryRevokePunishmentByCounselor(Page<PunishmentVo> page, @Param("departIds")String[] departIds);

    IPage<PunishmentVo> queryRevokePunishmentCounselorRequirement(Page<PunishmentVo> page,  @Param("userIds") String[] userIds );

}
