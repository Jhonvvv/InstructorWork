//package com.clm.common.base.config.shiro;
//
//import org.apache.shiro.authc.AuthenticationToken;
//
///**
// * @Author su
// * @Date 2021/12/6 9:33
// */
//public class JwtToken implements AuthenticationToken {
//
//    private static final long serialVersionUID = 1L;
//    private String token;
//
//    public JwtToken(String token) {
//        this.token = token;
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return token;
//    }
//
//    @Override
//    public Object getCredentials() {
//        return token;
//    }
//}
