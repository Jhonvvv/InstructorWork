package com.clm.counselor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.counselor.entity.ScholarshipApprove;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clm.vo.ScholarshipVo;
import com.clm.vo.UserQueryVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author su
 * @since 2021-12-27
 */
public interface ScholarshipApproveService extends IService<ScholarshipApprove> {

    IPage<ScholarshipVo> queryScholarshipApproveByCounselor(Page<ScholarshipVo> page, UserQueryVo userQuery);

    ScholarshipVo passScholarship(ScholarshipVo scholarshipVo);

    ScholarshipVo rejectScholarship(ScholarshipVo scholarshipVo);

}
