package com.clm.system.mapper;

import com.clm.system.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-11-22
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    @Delete("delete from sys_user_role where role_id =#{roleId}")
    void deleteRoleUserRelation(@Param("roleId") String roleId);

    void deleteBathRoleUserRelation(@Param("roleIds") String[] roleIds);

    @Delete("delete from sys_role_permission where role_id=#{roleId} ")
    void deleteRolePermissionRelation(@Param("roleId") String roleId);

    void deleteBathRolePermissionRelation(@Param("roleIds") String[] roleIds);

    @Select("select role_code from sys_role where id in (select role_id from sys_user_role where user_id = (select id from sys_user where username=#{username}))")
    List<String> getRoleByUserName(@Param("username") String username);

    @Select("select role_id from sys_user_role where user_id = (select id from sys_user where username=#{username})")
    String getRoleIdByUserName(@Param("username") String username);

}
