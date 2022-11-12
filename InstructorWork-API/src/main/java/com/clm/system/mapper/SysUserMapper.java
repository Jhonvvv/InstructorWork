package com.clm.system.mapper;

import com.clm.system.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-11-22
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    @Select("select *from sys_user where id=#{userId}")
    SysUser getDeleteUser(@Param("userId") String userId);
}
