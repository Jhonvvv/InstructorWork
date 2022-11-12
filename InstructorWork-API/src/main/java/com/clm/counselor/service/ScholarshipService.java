package com.clm.counselor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.counselor.entity.Scholarship;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clm.vo.ScholarshipVo;
import com.clm.vo.UserQueryVo;

import java.util.List;

/**
 * <p>
 * 奖学金表 服务类
 * </p>
 *
 * @author su
 * @since 2021-12-27
 */
public interface ScholarshipService extends IService<Scholarship> {

    Scholarship addScholarship(Scholarship scholarship);

    List<Scholarship> queryScholarshipByUsername(String username);

    IPage<ScholarshipVo> queryScholarshipByCounselor(Page<ScholarshipVo> page, UserQueryVo userQuery);

    ScholarshipVo passScholarship(ScholarshipVo scholarshipVo);

    ScholarshipVo rejectScholarship(ScholarshipVo scholarshipVo);
}
