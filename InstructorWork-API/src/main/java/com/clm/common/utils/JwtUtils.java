package com.clm.common.utils;


import com.clm.common.base.exception.ClmException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * @Author su
 * @Date 2021/11/23 19:43
 */
public class JwtUtils {
    // Token过期时间30分钟（用户登录过期时间是此时间的两倍，以token在redis缓存时间为准）
    public static final long EXPIRE_TIME = 30 * 60 * 1000;
    // 需要和加密盐组合（JWT 规范要求 HMAC 密钥长度 >= 256 位）
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    /**
     * 生成签名,根据EXPIRE_TIME的时间过期
     *
     * @param username 用户名
     * @param secret   加密盐
     * @return
     */
    public static String sign(String username, String secret) {
        Key key = new SecretKeySpec(DatatypeConverter.parseBase64Binary(APP_SECRET+secret), SignatureAlgorithm.HS256.getJcaName());
        String jwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("clm-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .claim("username", username)
                .signWith(key)
                .compact();
        return jwtToken;
    }


    /**
     * 校验token是否正确
     *
     * @param token    密钥
     * @param username 用户名
     * @param secret   加密盐
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        if (StringUtils.isEmpty(token)) return false;
        try {
            Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(APP_SECRET+secret))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据request中的token获取用户账号
     *
     * @param token   token
     * @param secret  加密盐
     * @return 用户名
     */
    public static String getUserNameByToken(String token, String secret) {
        Claims body = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(APP_SECRET+secret))
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = (String) body.get("username");
        if (StringUtils.isEmpty(username)) {
            throw new ClmException("未获取到用户");
        }
        return username;
    }
}
