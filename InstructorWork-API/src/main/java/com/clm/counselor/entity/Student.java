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
 * 学生信息表
 * </p>
 *
 * @author su
 * @since 2021-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("student")
@ApiModel(value="Student对象", description="学生信息表")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "用户表ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "学生家庭住址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "身份证号")
    @TableField("id_card")
    private String idCard;

    @ApiModelProperty(value = "监护人姓名")
    @TableField("guardian")
    private String guardian;

    @ApiModelProperty(value = "监护人电话")
    @TableField("guardian_tel")
    private String guardianTel;

    @ApiModelProperty(value = "删除状态 0正常 1已删除")
    @TableField(value = "del_flag",fill = FieldFill.INSERT)
    @TableLogic
    private Integer delFlag;

    @ApiModelProperty(value = "创建人")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "创建日期")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty(value = "更新日期")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
