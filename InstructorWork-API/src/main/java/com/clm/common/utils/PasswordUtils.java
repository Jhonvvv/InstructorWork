package com.clm.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * @Author su
 * @Date 2021/11/21 11:23
 */
public class PasswordUtils {
    /**
     * 定义使用的算法为:PBEWITHMD5andDES算法
     */
    public static final String ALGORITHM = "PBEWithMD5AndDES";//加密算法
    public static final String Salt = "63293188";//密钥

    /**
     * 定义迭代次数为1000次，次数越多，运算越大，越不容易被破解
     */
    private static final int ITERATIONCOUNT = 1000;

    /**
     * 获取加密算法中使用的盐值,解密中使用的盐值必须与加密中使用的相同才能完成操作. 盐长度必须为 8 字节的倍数
     *
     * @return byte[] 盐值
     * */
    public static byte[] getSalt() throws Exception {
        // 实例化安全随机数
        SecureRandom random = new SecureRandom();
        // 产出盐
        return random.generateSeed(8);
    }

    public static byte[] getStaticSalt() {
        // 产出盐
        return Salt.getBytes();
    }

    /**
     * 根据PBE密码生成一把密钥
     *
     * @param password
     *            生成密钥时所使用的密码
     * @return Key PBE算法密钥
     * */
    private static Key getPBEKey(String password) {
        // 实例化使用的算法
        SecretKeyFactory keyFactory;
        SecretKey secretKey = null;
        try {
            keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            // 设置PBE密钥参数
            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
            // 生成密钥
            secretKey = keyFactory.generateSecret(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return secretKey;
    }

    /**
     * 加密明文字符串
     *
     * @param plaintext
     *            待加密的明文字符串
     * @param password
     *            生成密钥时所使用的密码
     * @param salt
     *            盐值
     * @return 加密后的密文字符串
     * @throws Exception
     */
    public static String encrypt(String plaintext, String password, String salt) {
        //获取根据PBE口令生成的key
        Key key = getPBEKey(password);
        byte[] encipheredData = null;
        //设置PBE参数的盐和运算次数
        PBEParameterSpec parameterSpec = new PBEParameterSpec(salt.getBytes(), ITERATIONCOUNT);
        try {
            //构建实例化
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //Cipher对象使用之前还需要初始化，共三个参数（"加密模式或者解密模式","密钥","向量"）
            cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
            //数据转换 （中文作为用户名时，加密的密码windows和linux会得到不同的结果）
            encipheredData = cipher.doFinal(plaintext.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64String(encipheredData);
    }
    /**
     * 解密密文字符串
     *
     * @param ciphertext
     *            待解密的密文字符串
     * @param password
     *            生成密钥时所使用的密码(如需解密,该参数需要与加密时使用的一致)
     * @param salt
     *            盐值(如需解密,该参数需要与加密时使用的一致)
     * @return 解密后的明文字符串
     * @throws Exception
     */
    public static String decrypt(String ciphertext, String password, String salt) {
        //转换密钥
        Key key = getPBEKey(password);
        byte[] passDec = null;
        //实例化PBE参数
        PBEParameterSpec parameterSpec = new PBEParameterSpec(salt.getBytes(), ITERATIONCOUNT);
        try {
            //构建实例化
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //初始化
            cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
             //数据转换
            passDec = cipher.doFinal(Base64.decodeBase64(ciphertext));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //返回明文字符
        return new String(passDec);
    }

}
