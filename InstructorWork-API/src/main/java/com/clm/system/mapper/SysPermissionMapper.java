package com.clm.system.mapper;

import com.clm.system.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-11-29
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    @Update("update sys_permission set is_leaf=#{leaf} where id=#{permissionId}")
    int setMenuLeaf(@Param("permissionId") String permissionId,@Param("leaf") int leaf);

    @Select("select *from sys_permission where id in( select permission_id  from sys_role_permission where role_id=#{roleId})")
    List<SysPermission> getPermissionByRoleId(@Param("roleId") String roleId );

}
