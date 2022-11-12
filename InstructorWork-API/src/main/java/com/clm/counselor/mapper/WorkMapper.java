package com.clm.counselor.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.clm.counselor.entity.DailyWork;
import com.clm.counselor.entity.Work;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clm.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 工作表 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-12-09
 */
@Mapper
public interface WorkMapper extends BaseMapper<Work> {

   List<Work> queryWorkNotFinish(@Param("userId")String userId, @Param(Constants.WRAPPER) Wrapper<DailyWork> wrapper);

}
