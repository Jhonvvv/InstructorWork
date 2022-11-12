package com.clm.counselor.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.counselor.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clm.system.entity.SysUser;
import com.clm.vo.ResisterStudentVo;
import com.clm.vo.UserQueryVo;

import java.util.List;

/**
 * <p>
 * 学生信息表 服务类
 * </p>
 *
 * @author su
 * @since 2021-12-05
 */
public interface StudentService extends IService<Student> {

    IPage<ResisterStudentVo> queryUserAndStudent(Page<ResisterStudentVo> page, String[] departIds, UserQueryVo userQuery);

    boolean deleteStudent(String studentId);


}
