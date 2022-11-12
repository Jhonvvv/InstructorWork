package com.clm.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 组织机构表
 * </p>
 *
 * @author su
 * @since 2021-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_depart")
@ApiModel(value = "SysDepart对象", description = "组织机构表")
public class SysDepart implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("父机构ID")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty("机构/部门名称")
    @TableField("depart_name")
    private String departName;

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


}
