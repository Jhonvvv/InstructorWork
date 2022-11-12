package com.clm.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

/**
 * @Author su
 * @Date 2021/11/20 19:50
 */
@Slf4j
public class OConvertUtils {
    public static String getString(String s) {
        return (getString(s, ""));
    }

//    /**
//     * 转义成Unicode编码
//     * @param s
//     * @return
//     */
//	public static String escapeJava(Object s) {
//		return StringEscapeUtils.escapeJava(getString(s));
//	}

    public static String getString(Object object) {
        if (isEmpty(object)) {
            return "";
        }
        return (object.toString().trim());
    }

    public static String getString(int i) {
        return (String.valueOf(i));
    }

    public static String getString(float i) {
        return (String.valueOf(i));
    }

    public static String getString(String s, String defval) {
        if (isEmpty(s)) {
            return (defval);
        }
        return (s.trim());
    }

    public static String getString(Object s, String defval) {
        if (isEmpty(s)) {
            return (defval);
        }
        return (s.toString().trim());
    }
    /**
     * 随机数
     * @param place 定义随机数的位数
     */
    public static String randomGen(int place) {
        String base = "qwertyuioplkjhgfdsazxcvbnmQAZWSXEDCRFVTGBYHNUJMIKLOP0123456789";
        StringBuffer sb = new StringBuffer();
        Random rd = new Random();
        for(int i=0;i<place;i++) {
            sb.append(base.charAt(rd.nextInt(base.length())));
        }
        return sb.toString();
    }
}
