package com.clm.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.clm.system.model.PermissionTree;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author su
 * @Date 2021/11/29 21:10
 */
@Data
public class PermissionTreeVo {
    @ApiModelProperty(value = "菜单名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "路径")
    @TableField("path")
    private String path;


    @ApiModelProperty(value = "前端组件")
    @TableField("component")
    private String component;

    @ApiModelProperty(value = "一级菜单跳转地址")
    @TableField("redirect")
    private String redirect;

    private Map<String,String> meta;

    private List<PermissionTreeVo> children;

    public PermissionTreeVo(PermissionTree permissionTree) {
        meta=new HashMap<>();
        this.path=permissionTree.getPath();

        meta.put("title",permissionTree.getTitle());
        meta.put("icon",permissionTree.getIcon());
        this.name = permissionTree.getName();
        this.redirect = permissionTree.getRedirect();
        this.component=permissionTree.getComponent();
        if (!permissionTree.isLeaf(permissionTree.getIsLeaf())) {
            this.children = new ArrayList<PermissionTreeVo>();
        }else {
            this.component="()=>import('@" + permissionTree.getComponent() + "')";
        }

    }

}
