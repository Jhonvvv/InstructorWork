package com.clm.counselor.entity;

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
 * 
 * </p>
 *
 * @author su
 * @since 2021-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("punishment_approve")
@ApiModel(value="PunishmentApprove对象", description="")
public class PunishmentApprove implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "处罚申请表ID")
    @TableField("punishment_id")
    private String punishmentId;

    @ApiModelProperty(value = "辅导员姓名")
    @TableField("counselor_name")
    private String counselorName;

    @ApiModelProperty(value = "审核前状态(0，辅导员通过 1，辅导员拒绝)")
    @TableField("before_status")
    private Integer beforeStatus;

    @ApiModelProperty(value = "当前审核状态(0，待审核 1，已审核)")
    @TableField("current_status")
    private Integer currentStatus;

    @ApiModelProperty(value = "审核后状态(0,审核通过，1，审核拒绝)")
    @TableField("after_status")
    private Integer afterStatus;

    @ApiModelProperty(value = "审核备注")
    @TableField("remake")
    private String remake;

    @ApiModelProperty(value = "删除状态(0-正常,1-已删除)")
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
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


}
