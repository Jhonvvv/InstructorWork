package com.clm.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_depart")
@ApiModel(value="SysUserDepart对象", description="")
public class SysUserDepart implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "部门id")
    @TableField("dep_id")
    private String depId;

    public SysUserDepart(String userId, String depId) {
        this.userId = userId;
        this.depId = depId;
    }
}
