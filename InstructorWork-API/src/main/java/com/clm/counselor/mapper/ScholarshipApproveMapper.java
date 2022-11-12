package com.clm.counselor.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.counselor.entity.ScholarshipApprove;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clm.vo.ScholarshipVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-12-27
 */
@Mapper
public interface ScholarshipApproveMapper extends BaseMapper<ScholarshipApprove> {

    IPage<ScholarshipVo> queryScholarshipApproveByCounselor(Page<ScholarshipVo> page, @Param("departIds")String[] departIds);

    IPage<ScholarshipVo> queryScholarshipApproveByCounselorRequirement(Page<ScholarshipVo> page,  @Param("userIds") String[] userIds );
}
