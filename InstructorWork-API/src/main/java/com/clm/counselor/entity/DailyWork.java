package com.clm.counselor.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.clm.common.base.constant.CommonConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 工作表
 * </p>
 *
 * @author su
 * @since 2021-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("daily_work")
@ApiModel(value="DailyWork对象", description="工作表")
public class DailyWork implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "工作ID")
    @TableField("work_id")
    private String workId;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "工作名称")
    @TableField("work_name")
    private String workName;

    @ApiModelProperty(value = "备注")
    @TableField("remake")
    private String remake;

    @ApiModelProperty(value = "工作类型（0:工作,1:会议）")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "是否临时工作（0:否,1:是）")
    @TableField("is_provisional")
    private Integer isProvisional;

    @ApiModelProperty(value = "完成工作（0:未完成,1:完成）")
    @TableField("is_finish")
    private Integer isFinish;

    @ApiModelProperty(value = "工作开始时间")
    @JsonFormat(timezone = "GMT+8",pattern = "HH:mm")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("start_time")
    private Date startTime;

    @ApiModelProperty(value = "工作结束时间")
    @JsonFormat(timezone = "GMT+8",pattern = "HH:mm")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("end_time")
    private Date endTime;

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

    public DailyWork(Work work) {
        this.workId = work.getId();
        this.workName=work.getWorkName();
        this.userId = work.getUserId();
        this.remake = work.getRemake();
        this.type = work.getType();
        this.setIsFinish(CommonConstant.Finish_0);
        this.setIsProvisional(CommonConstant.PROVISIONAL_0);
    }

    public DailyWork() {
    }
}
