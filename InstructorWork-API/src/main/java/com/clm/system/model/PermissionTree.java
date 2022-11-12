package com.clm.system.model;

import com.baomidou.mybatisplus.annotation.*;
import com.clm.system.entity.SysPermission;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author su
 * @Date 2021/11/29 8:59
 */
@Data
public class PermissionTree {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "父id")
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

    @ApiModelProperty(value = "是否叶子节点:    1:是   0:不是")
    @TableField("is_leaf")
    private Integer isLeaf;


    private List<PermissionTree> children;

    public PermissionTree(SysPermission permission) {
        this.id = permission.getId();
        this.path=permission.getPath();
        this.title=permission.getTitle();
        this.component=permission.getComponent();
        this.icon = permission.getIcon();
        this.isLeaf = permission.getIsLeaf();
        this.menuType = permission.getMenuType();
        this.name = permission.getName();
        this.parentId = permission.getParentId();
        this.redirect = permission.getRedirect();
        this.isRoute = permission.getIsRoute();
        if (!permission.isLeaf(permission.getIsLeaf())){
            this.children=new ArrayList<PermissionTree>();
        }

    }
    public boolean isLeaf(Integer leaf) {
        if (leaf==0) return false;
        return true;
    }
}
