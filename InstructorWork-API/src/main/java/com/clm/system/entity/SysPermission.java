package com.clm.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author su
 * @since 2021-11-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_permission")
@ApiModel(value="SysPermission对象", description="菜单权限表")
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "父id")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty(value = "菜单名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "菜单标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "路径")
    @TableField("path")
    private String path;

    @ApiModelProperty(value = "菜单图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "前端组件")
    @TableField("component")
    private String component;

    @ApiModelProperty(value = "一级菜单跳转地址")
    @TableField("redirect")
    private String redirect;

    @ApiModelProperty(value = "菜单类型(0:一级菜单; 1:子菜单)")
    @TableField("menu_type")
    private Integer menuType;

    @ApiModelProperty(value = "是否路由菜单: 0:不是  1:是（默认值1）")
    @TableField("is_route")
    private Integer isRoute;

    @ApiModelProperty(value = "是否叶子节点:  1:是   0:不是")
    @TableField("is_leaf")
    private Integer isLeaf;

    @ApiModelProperty(value = "删除状态 0正常 1已删除")
    @TableField("del_flag")
    @TableLogic
    private Integer delFlag;

    @ApiModelProperty(value = "创建人")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public boolean isLeaf(Integer leaf) {
        if (leaf==0) return false;
        return true;
    }
}
