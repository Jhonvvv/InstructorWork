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
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * <p>
 * 日常工作表
 * </p>
 *
 * @author su
 * @since 2021-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("work")
@ApiModel(value="Work对象", description="日常工作表")
public class Work implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "用户ID")
    @Excel(name = "用户ID", width = 30)
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "工作名称")
    @Excel(name = "工作名称", width = 40)
    @TableField("work_name")
    private String workName;

    @ApiModelProperty(value = "备注")
    @Excel(name = "备注", width = 50)
    @TableField("remake")
    private String remake;

    @ApiModelProperty(value = "工作类型（0:工作,1:会议）")
    @Excel(name = "工作类型（0:工作,1:会议）", width = 30)
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "工作开始时间")
    @Excel(name = "工作开始时间(HH:mm)", width = 20)
    @TableField("start_time")
    private String startTime;

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
