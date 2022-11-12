package com.clm.counselor.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.counselor.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clm.system.entity.SysUser;
import com.clm.vo.ResisterStudentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 学生信息表 Mapper 接口
 * </p>
 *
 * @author su
 * @since 2021-12-05
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    IPage<ResisterStudentVo> queryUserAndStudent(Page<ResisterStudentVo> page, @Param("departIds")String[] departIds);

    IPage<ResisterStudentVo> queryUserAndStudentByRequirement(Page<ResisterStudentVo> page, @Param("departIds")String[] departIds, @Param(Constants.WRAPPER) Wrapper<SysUser> wrapper);

}
