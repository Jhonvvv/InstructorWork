package com.clm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clm.common.base.constant.CommonConstant;
import com.clm.common.utils.PasswordUtils;
import com.clm.common.utils.Result;
import com.clm.counselor.entity.Punishment;
import com.clm.counselor.entity.Reward;
import com.clm.counselor.entity.Student;
import com.clm.counselor.mapper.PunishmentMapper;
import com.clm.counselor.mapper.RewardMapper;
import com.clm.counselor.mapper.StudentMapper;
import com.clm.system.entity.SysRole;
import com.clm.system.entity.SysUser;
import com.clm.system.entity.SysUserDepart;
import com.clm.system.entity.SysUserRole;
import com.clm.system.mapper.SysUserDepartMapper;
import com.clm.system.mapper.SysUserMapper;
import com.clm.system.mapper.SysUserRoleMapper;
import com.clm.system.service.SysRoleService;
import com.clm.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clm.vo.ResisterUserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-11-22
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysUserDepartMapper sysUserDepartMapper;
    @Autowired
    private SysRoleService sysRoleMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private RewardMapper rewardMapper;
    @Autowired
    private PunishmentMapper punishmentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUser saveUser(ResisterUserVo resisterUserVo) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(resisterUserVo, user);
        //step.1 保存用户
        this.save(user);
        //step.2 保存角色
        if (!StringUtils.isEmpty(resisterUserVo.getRoleId())) {
            SysUserRole userRole = new SysUserRole(user.getId(), resisterUserVo.getRoleId());
            sysUserRoleMapper.insert(userRole);
        }
        //step.3 保存部门
        if (!StringUtils.isEmpty(resisterUserVo.getDepartId())) {
            SysUserDepart userDepart = new SysUserDepart(user.getId(), resisterUserVo.getDepartId());
            sysUserDepartMapper.insert(userDepart);
        }
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUser saveUserByRole(ResisterUserVo resisterUserVo) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(resisterUserVo, user);
        SysRole role = null;
        Integer identity = null;
        if (!StringUtils.isEmpty(resisterUserVo.getRoleId())) {
            role = sysRoleMapper.getById(resisterUserVo.getRoleId());
            // 设置身份
            identity = judgeRoleCode(role.getRoleCode());

        }
        //step.1 保存用户
        this.save(user);
        if (identity != null) {
            user.setUserIdentity(identity);
            if (identity == CommonConstant.USER_IDENTITY_1) {
                //向学生表插入数据
                Student student = new Student();
                student.setUserId(user.getId());
                studentMapper.insert(student);
            }
        }
        //step.2 保存角色
        if (role != null) {
            SysUserRole userRole = new SysUserRole(user.getId(), resisterUserVo.getRoleId());
            sysUserRoleMapper.insert(userRole);
        }
        //step.3 保存部门
        if (!StringUtils.isEmpty(resisterUserVo.getDepartId())) {
            SysUserDepart userDepart = new SysUserDepart(user.getId(), resisterUserVo.getDepartId());
            sysUserDepartMapper.insert(userDepart);
        }
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUser saveUserIdentity(SysUser sysUser) {
        //step.1 保存用户
        this.save(sysUser);
        //step.2 保存角色
        if (sysUser.getUserIdentity() != null) {
            //向学生表插入数据
            if (sysUser.getUserIdentity() == CommonConstant.USER_IDENTITY_1) {
                Student student = new Student();
                student.setUserId(sysUser.getId());
                studentMapper.insert(student);
            }
            QueryWrapper<SysRole> wrapper = judgeIdentity(sysUser.getUserIdentity());
            SysRole role = sysRoleMapper.getOne(wrapper);
            if (role != null) {
                SysUserRole userRole = new SysUserRole(sysUser.getId(), role.getId());
                sysUserRoleMapper.insert(userRole);
            }
        }
        //step.3 保存部门
        if (!StringUtils.isEmpty(sysUser.getDepartId())) {
            SysUserDepart userDepart = new SysUserDepart(sysUser.getId(), sysUser.getDepartId());
            sysUserDepartMapper.insert(userDepart);
        }
        return sysUser;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(String userId) {
        SysUser user = baseMapper.selectById(userId);
        if (user != null) {
            baseMapper.deleteById(userId);
            if (user.getUserIdentity() == CommonConstant.USER_IDENTITY_1) {
                Student student = studentMapper.selectOne(new QueryWrapper<Student>().lambda().eq(Student::getUserId, userId));
                studentMapper.delete(new QueryWrapper<Student>().lambda().eq(Student::getUserId, userId));
                rewardMapper.delete(new QueryWrapper<Reward>().lambda().eq(Reward::getStudentId, student.getId()));
                punishmentMapper.delete(new QueryWrapper<Punishment>().lambda().eq(Punishment::getStudentId, student.getId()));
            }
            sysUserRoleMapper.delete(new QueryWrapper<SysUserRole>().lambda().eq(SysUserRole::getUserId, userId));
            sysUserDepartMapper.delete(new QueryWrapper<SysUserDepart>().lambda().eq(SysUserDepart::getUserId, userId));
        }

        return false;
    }

    @Override
    public Result<?> checkUserIsEffective(SysUser sysUser) {
        Result<Object> result = new Result<Object>();
        //情况1：根据用户信息查询，该用户不存在
        if (sysUser == null) {
            result.error20001("该用户不存在，请注册");
            return result;
        }
        //情况2：根据用户信息查询，该用户已注销
        SysUser deleteUser = baseMapper.getDeleteUser(sysUser.getId());
        if (deleteUser == null) {
            result.error20001("该用户已注销");
            return result;
        }
        //情况3：根据用户信息查询，该用户已冻结
        if (CommonConstant.USER_FREEZE.equals(sysUser.getStatus())) {
            result.error20001("该用户已冻结");
            return result;
        }
        return result;
    }

    @Override
    public boolean checkUsername(String userId, String username) {
        SysUser user = null;
        if (!StringUtils.isEmpty(userId)) {
            user = baseMapper.selectById(userId);
        }
        SysUser oldUser = null;
        if (!StringUtils.isEmpty(username)) {
            oldUser = baseMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, username));
        }
        //传入的username查询到信息了，需要判断是新增还是修改
        if (oldUser != null) {
            //user为空=>新增模式=>只要user存在则返回
            if (user == null) {
                return true;
            } else if (!userId.equals(oldUser.getId())) {
                //否则=>编辑模式=>判断两者ID是否一致
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editUser(SysUser sysUser) {
        //step.1 修改用户基础信息
        SysUser user = baseMapper.selectById(sysUser.getId());
        // 用户账号修改 密码未修改 重置成默认密码
        if ((!user.getUsername().equals(sysUser.getUsername())) && user.getPassword().equals(sysUser.getPassword())) {
            String passwordEncode = PasswordUtils.encrypt(sysUser.getUsername(), CommonConstant.DEFAULT_PASSWORD, sysUser.getSalt());
            sysUser.setPassword(passwordEncode);
        } else if (user.getUsername().equals(sysUser.getUsername()) && !user.getPassword().equals(sysUser.getPassword())) { // 密码修改
            String passwordEncode = PasswordUtils.encrypt(sysUser.getUsername(), sysUser.getPassword(), sysUser.getSalt());
            sysUser.setPassword(passwordEncode);
        } else if (!user.getUsername().equals(sysUser.getUsername()) && !user.getPassword().equals(sysUser.getPassword())) { //修改账户和密码
            String passwordEncode = PasswordUtils.encrypt(sysUser.getUsername(), sysUser.getPassword(), sysUser.getSalt());
            sysUser.setPassword(passwordEncode);
        }
        //判断角色身份
        if (sysUser.getRoleId() != null) {
            SysRole role = sysRoleMapper.getById(sysUser.getRoleId());
            Integer identity = judgeRoleCode(role.getRoleCode());
            sysUser.setUserIdentity(identity);
        }
        baseMapper.updateById(sysUser);
        //step.2 修改角色
        //处理用户角色 先删后加
        sysUserRoleMapper.delete(new QueryWrapper<SysUserRole>().lambda().eq(SysUserRole::getUserId, sysUser.getId()));
        if (sysUser.getRoleId() != null) {
            SysUserRole userRole = new SysUserRole(sysUser.getId(), sysUser.getRoleId());
            sysUserRoleMapper.insert(userRole);
        }
        //step.3 修改部门
        //处理用户角色 先删后加
        sysUserDepartMapper.delete(new QueryWrapper<SysUserDepart>().lambda().eq(SysUserDepart::getUserId, sysUser.getId()));
        if (StringUtils.isNotEmpty(sysUser.getDepartId())) {
            SysUserDepart userDepart = new SysUserDepart(sysUser.getId(), sysUser.getDepartId());
            sysUserDepartMapper.insert(userDepart);
        }
        return false;
    }

    private QueryWrapper<SysRole> judgeIdentity(Integer identity) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        switch (identity) {
            case 1:
                wrapper.lambda().eq(SysRole::getRoleCode, "student");
                break;
            case 2:
                wrapper.lambda().eq(SysRole::getRoleCode, "counselor");
                break;
            case 3:
                wrapper.lambda().eq(SysRole::getRoleCode, "admin");
                break;
        }
        return wrapper;
    }

    private Integer judgeRoleCode(String roleCode) {
        int identity = CommonConstant.USER_IDENTITY_4;
        switch (roleCode) {
            case CommonConstant.ROLE_CODE_ADMIN:
                identity = CommonConstant.USER_IDENTITY_3;
                break;
            case CommonConstant.ROLE_CODE_COUNSELOR:
                identity = CommonConstant.USER_IDENTITY_2;
                break;
            case CommonConstant.ROLE_CODE_STUDENT:
                identity = CommonConstant.USER_IDENTITY_1;
                break;
        }
        return identity;
    }

    public SysUser getUserInfoByUsername(String username) {
        if (StringUtils.isNotEmpty(username)) {
            SysUser sysUser = baseMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, username));
            return sysUser;
        } else {
            return null;
        }
    }
}
