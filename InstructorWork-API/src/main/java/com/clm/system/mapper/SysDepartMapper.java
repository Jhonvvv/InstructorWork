package com.clm.system.mapper;

import com.clm.system.entity.SysDepart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 组织机构表 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-11-22
 */
@Mapper
public interface SysDepartMapper extends BaseMapper<SysDepart> {

    @Delete("delete from sys_user_depart where dep_id=#{departId}")
    void deleteDepartUserRelation(@Param("departId")String departId);

    void deleteBathDepartUserRelation(@Param("departIds")String[] departIds);

    SysDepart queryUserDepart(@Param("userId")String userId);
}
