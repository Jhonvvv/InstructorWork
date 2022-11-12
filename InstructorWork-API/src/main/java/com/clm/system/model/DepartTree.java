package com.clm.system.model;

import com.baomidou.mybatisplus.annotation.*;
import com.clm.system.entity.SysDepart;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author su
 * @Date 2021/12/3 16:15
 */
@Data
public class DepartTree implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("父机构ID")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty("机构/部门名称")
    @TableField("depart_name")
    private String title;

    @ApiModelProperty("排序")
    @TableField("depart_order")
    private Integer departOrder;


    @ApiModelProperty("机构类型 1一级部门 2子部门")
    @TableField("org_type")
    private String orgType;

    @ApiModelProperty("描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("备注")
    @TableField("remake")
    private String remake;

    @ApiModelProperty("状态（1启用，0不启用）")
    @TableField(value = "status",fill = FieldFill.INSERT)
    private Integer status;

    @ApiModelProperty("删除状态（0，正常，1已删除）")
    @TableField(value = "del_flag",fill = FieldFill.INSERT)
    @TableLogic
    private Integer delFlag;

    @ApiModelProperty("创建人")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty("创建日期")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("更新人")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty("更新日期")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private List<DepartTree> children = new ArrayList<>();

    public DepartTree(SysDepart sysDepart) {
        this.title = sysDepart.getDepartName();
        this.id = sysDepart.getId();
        this.parentId = sysDepart.getParentId();
        this.departOrder = sysDepart.getDepartOrder();
        this.description = sysDepart.getDescription();
        this.remake=sysDepart.getRemake();
        this.orgType = sysDepart.getOrgType();
        this.status = sysDepart.getStatus();
        this.delFlag = sysDepart.getDelFlag();
        this.createBy = sysDepart.getCreateBy();
        this.createTime = sysDepart.getCreateTime();
        this.updateBy = sysDepart.getUpdateBy();
        this.updateTime = sysDepart.getUpdateTime();
    }

    public DepartTree() {
    }
}
