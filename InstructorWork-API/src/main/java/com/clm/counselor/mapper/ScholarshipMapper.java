package com.clm.counselor.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.counselor.entity.Scholarship;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clm.system.entity.SysUser;
import com.clm.vo.ScholarshipVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 奖学金表 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-12-27
 */

@Mapper
public interface ScholarshipMapper extends BaseMapper<Scholarship> {

    IPage<ScholarshipVo> queryScholarshipByCounselor(Page<ScholarshipVo> page, @Param("departIds")String[] departIds);

    IPage<ScholarshipVo> queryScholarshipByCounselorRequirement(Page<ScholarshipVo> page,  @Param("userIds") String[] userIds );
}
