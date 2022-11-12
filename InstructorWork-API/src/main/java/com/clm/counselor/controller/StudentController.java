package com.clm.counselor.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clm.common.base.constant.CommonConstant;
import com.clm.common.utils.Result;
import com.clm.common.utils.ResultCode;
import com.clm.counselor.entity.Student;
import com.clm.counselor.service.StudentService;
import com.clm.system.controller.SysUserController;
import com.clm.system.entity.SysUser;
import com.clm.system.service.SysUserService;
import com.clm.vo.ResisterStudentVo;
import com.clm.vo.ResisterUserVo;
import com.clm.vo.UserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 学生信息表 前端控制器
 * </p>
 *
 * @author su
 * @since 2021-12-05
 */
@Api(tags = "学生管理")
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/counselor/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private SysUserController sysUserController;

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("新增学生")
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    public Result<ResisterStudentVo> add(@RequestBody ResisterStudentVo resisterStudentVo) {
        Result<ResisterStudentVo> result = new Result<>();
        try {
            ResisterUserVo resisterUserVo=new ResisterUserVo();
            BeanUtils.copyProperties(resisterStudentVo, resisterUserVo);
            //用户设置
            resisterUserVo.setUserIdentity(CommonConstant.USER_IDENTITY_1);
            resisterUserVo.setPassword(CommonConstant.DEFAULT_PASSWORD);
            Result<SysUser> addUser = sysUserController.add(resisterUserVo);
            if (addUser.getCode() == ResultCode.SUCCESS) {
                //学生设置
                Student student = new Student();
                BeanUtils.copyProperties(resisterStudentVo, student);
                student.setUserId(addUser.getResult().getId());
                studentService.save(student);
                resisterStudentVo.setUserId(addUser.getResult().getId());
                resisterStudentVo.setStudentId(student.getId());
                result.setResult(resisterStudentVo);
                result.success("添加成功!");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }

    @ApiOperation("修改学生")
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    public Result<ResisterStudentVo> edit(@RequestBody ResisterStudentVo resisterStudentVo) {
        Result<ResisterStudentVo> result = new Result<>();
        try {
            SysUser user = new SysUser();
            BeanUtils.copyProperties(resisterStudentVo, user);
            user.setId(resisterStudentVo.getUserId());
            sysUserController.edit(user);
            //学生设置
            Student student = new Student();
            BeanUtils.copyProperties(resisterStudentVo, student);
            student.setId(resisterStudentVo.getStudentId());
            studentService.updateById(student);
            result.success("修改成功!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error20001("操作失败");
        }
        return result;
    }
    @ApiOperation("删除学生")
    @DeleteMapping("/delete")
    public Result<?> delete(@RequestParam(name = "id")String id){
        boolean deleteStudent = studentService.deleteStudent(id);
        if (deleteStudent){
            return Result.OK().success("删除成功！");
        }
        return Result.error("删除失败");
    }

    @ApiOperation("查询通过管理部门查询所拥有的学生")
    @PostMapping("/queryByDepartAll")
    public Result<?> queryAll(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                              @RequestBody(required = true) UserQueryVo userQuery) {
        if (StringUtils.isNotEmpty(userQuery.getUsername())) {
            SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, userQuery.getUsername()));
            Page<ResisterStudentVo> page=new Page<>(pageNo,pageSize);
            IPage<ResisterStudentVo> resisterStudentVoIPage= studentService.queryUserAndStudent(page, sysUser.getDepartIds().split(","),userQuery);
            return Result.OK(resisterStudentVoIPage);
        }
        return Result.error("查询失败");
    }
}

