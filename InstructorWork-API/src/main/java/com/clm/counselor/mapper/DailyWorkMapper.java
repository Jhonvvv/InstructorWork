package com.clm.counselor.mapper;

import com.clm.counselor.entity.DailyWork;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 工作表 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-12-09
 */
@Mapper
public interface DailyWorkMapper extends BaseMapper<DailyWork> {

}
