//package com.clm.common.base.config.shiro;
//
//import com.clm.common.utils.RedisUtils;
//import com.clm.system.mapper.SysRoleMapper;
//import com.clm.vo.LoginUserVo;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * @Author su
// * @Date 2021/12/6 9:28
// */
//@Slf4j
//@Component
//public class ShiroRealm extends AuthorizingRealm {
//
//    @Lazy
//    @Resource
//    private RedisUtils redisUtils;
//
//    @Lazy
//    @Resource
//    private SysRoleMapper sysRoleMapper;
//
//    /**
//     * 必须重写此方法，不然Shiro会报错
//     */
//    @Override
//    public boolean supports(AuthenticationToken token) {
//        return token instanceof JwtToken;
//    }
//
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        log.info("===============Shiro权限认证开始============ [role,permissions]==========");
//        String username = null;
//        if (principalCollection!=null){
//            LoginUserVo loginUser= (LoginUserVo) principalCollection.getPrimaryPrincipal();
//            username=loginUser.getUsername();
//        }
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        // 设置用户拥有的角色集合，比如“admin,test”
//        List<String> roleList = sysRoleMapper.getRoleByUserName(username);
//        Set<String> roleSet=new HashSet<>(roleList);
//        info.setRoles(roleSet);
//        return null;
//    }
//
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        return null;
//    }
//}
