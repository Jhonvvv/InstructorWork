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
 * 奖学金表
 * </p>
 *
 * @author su
 * @since 2021-12-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("scholarship")
@ApiModel(value="Scholarship对象", description="奖学金表")
public class Scholarship implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "部门ID")
    @TableField("depart_id")
    private String departId;

    @ApiModelProperty(value = "奖学金等级（0，一等，1，二等，2，三等）")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(value = "综测排名")
    @TableField("ranking")
    private Integer ranking;

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
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
