package com.clm.counselor.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 惩罚表
 * </p>
 *
 * @author su
 * @since 2021-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("punishment")
@ApiModel(value="Punishment对象", description="惩罚表")
public class Punishment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "学生ID")
    @TableField("student_id")
    private String studentId;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "部门ID")
    @TableField("depart_id")
    private String departId;

    @ApiModelProperty(value = "部门ID")
    @TableField("punishment_id")
    private String punishmentId;

    @ApiModelProperty(value = "处罚申请类型(0:处罚申请,1:撤销处罚申请)")
    @TableField("approve_type")
    private Integer approveType;

    @ApiModelProperty(value = "撤销处罚（0:正常,1:撤销）")
    @TableField("is_revoke")
    private Integer isRevoke;


    @ApiModelProperty(value = "处罚类型（0:警告,1:严重警告,2:记过,3:留校查看,4:劝退,5:开除学籍）")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "处罚原因")
    @TableField("reason")
    private String reason;

    @ApiModelProperty(value = "撤销申请原因")
    @TableField("objection")
    private String objection;

    @ApiModelProperty(value = "证明材料")
    @TableField("prove")
    private String prove;

    @ApiModelProperty(value = "辅导员意见")
    @TableField("counselor_opinion")
    private String counselorOpinion;

    @ApiModelProperty(value = "审核意见")
    @TableField("audit_opinion")
    private String auditOpinion;

    @ApiModelProperty(value = "审核状态（0，待审核，1，审核中，2，通过  3，拒绝）")
    @TableField("approve_status")
    private Integer approveStatus;

    @ApiModelProperty(value = "删除状态(0-正常,1-已删除)")
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @TableLogic
    private Integer delFlag;

    @ApiModelProperty(value = "创建人")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
