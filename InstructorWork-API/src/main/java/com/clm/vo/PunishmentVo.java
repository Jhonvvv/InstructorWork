package com.clm.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author su
 * @Date 2021/12/29 14:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PunishmentVo {
    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "学生ID")
    @TableField("student_id")
    private String studentId;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "登录账号")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "真实姓名")
    @TableField("real_name")
    private String realName;

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

    @ApiModelProperty(value = "辅导员姓名")
    @TableField("counselor_name")
    private String counselorName;

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


    @ApiModelProperty(value = "审核前状态(0，辅导员通过 1，辅导员拒绝)")
    @TableField("before_status")
    private Integer beforeStatus;

    @ApiModelProperty(value = "当前审核状态(0，待审核 1，已审核)")
    @TableField("current_status")
    private Integer currentStatus;

    @ApiModelProperty(value = "审核后状态(0,审核通过，1，审核拒绝)")
    @TableField("after_status")
    private Integer afterStatus;

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
