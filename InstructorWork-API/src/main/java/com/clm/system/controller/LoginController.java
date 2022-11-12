package com.clm.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clm.common.base.constant.CommonConstant;
import com.clm.common.utils.JwtUtils;
import com.clm.common.utils.PasswordUtils;
import com.clm.common.utils.RedisUtils;
import com.clm.common.utils.Result;
import com.clm.system.entity.SysDepart;
import com.clm.system.entity.SysUser;
import com.clm.system.model.LoginModel;
import com.clm.system.service.SysDepartService;
import com.clm.system.service.SysUserService;
import com.clm.vo.UserVo;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author su
 * @Date 2021/11/24 17:59
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/system")
@Api(tags = "用户登录")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDepartService sysDepartService;
    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result<UserVo> login(@RequestBody LoginModel loginModel){
        Result<UserVo> result=new Result<>();
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",loginModel.getUsername());
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        result =(Result<UserVo>) sysUserService.checkUserIsEffective(sysUser);
        if(!result.isSuccess()) {
            return result;
        }
        // 1.校验用户名或密码是否正确
        String inputPassword = PasswordUtils.encrypt(loginModel.getUsername(), loginModel.getPassword(), sysUser.getSalt());
        if (!sysUser.getPassword().equals(inputPassword)){
            result.error20001("用户名或密码错误");
            return result;
        }
        //用户登录信息
        userInfo(sysUser, result);
        return result;
    }

    public Result<UserVo> userInfo(SysUser sysUser,Result<UserVo> result){
        // 获取用户部门信息
        SysDepart sysDepart = sysDepartService.queryUserDepart(sysUser.getId());
        // 生成token
        String token = JwtUtils.sign(sysUser.getUsername(), sysUser.getPassword()+sysUser.getSalt());
        // 设置token缓存有效时间
        //TODO 需要拦截完成
//        redisUtils.setKey(CommonConstant.PREFIX_USER_TOKEN+token,token);
//        redisUtils.setExpire(CommonConstant.PREFIX_USER_TOKEN+token,JwtUtils.EXPIRE_TIME* 2 * 24 * 7);
        UserVo userVo=new UserVo();
        userVo.setToken(token);
        userVo.setDepart(sysDepart);
        userVo.setUserInfo(sysUser);
        result.setResult(userVo);
        result.success("登录成功");
        return result;
    }
    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public Result<?> logout(@RequestBody UserVo userVo){
        try {
            String token =userVo.getToken();
            String name = userVo.getUserInfo().getUsername();
            SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, name));
            if (StringUtils.isEmpty(token)){
                return Result.error("退出登录失败！");
            }
            String username = JwtUtils.getUserNameByToken(token,sysUser.getPassword()+sysUser.getSalt());
            if (username.equals(name)){
                log.info(" 用户名:  "+sysUser.getRealName()+",退出成功！ ");
//                redisUtils.deleteKeys(CommonConstant.PREFIX_USER_TOKEN + token);
                return Result.OK().success("退出登录成功！");
            }else {
                return Result.error("Token无效!");
            }
        }catch (ExpiredJwtException e){
            log.error(e.getMessage(), e);
            return Result.OK().success("请重新登录!");
        }

    }

}
