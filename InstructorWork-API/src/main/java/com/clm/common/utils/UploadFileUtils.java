package com.clm.common.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.clm.common.base.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;

/**
 * @Author su
 * @Date 2021/12/4 11:20
 */
@Component
public class UploadFileUtils {
    private static String uploadPath;

    private static String uploadType;


    @Value(value = "${clm.path}")
    public void setUploadPath(String uploadPath) {
        UploadFileUtils.uploadPath = uploadPath;
    }

    @Value(value = "${clm.uploadType}")
    public void setUploadType(String uploadType) {
        UploadFileUtils.uploadType = uploadType;
    }


    public static Map<String, String> upload(String path, MultipartFile file) {
        Map<String, String> uploadMap = new HashMap<>();
        String savePath = "";
        String bizPath = path;
        if (CommonConstant.UPLOAD_TYPE.equals(uploadType)) {
            savePath = uploadLocal(file, bizPath);
        } else {
            savePath = uploadOSS(file);
        }
        if (!StringUtils.isEmpty(savePath)) {
            uploadMap.put("name", file.getOriginalFilename());
            uploadMap.put("path", savePath);
            return uploadMap;
        } else {
            return null;
        }
    }

    public static String uploadOSS(MultipartFile f) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ALiYunOSS.ENDPOINT, ALiYunOSS.ACCESS_KEY, ALiYunOSS.SECRET_KEY);
        try {
            InputStream inputStream = f.getInputStream();
            String original = f.getOriginalFilename();
            String fileType = original.substring(original.lastIndexOf("."));
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String fileName = uuid + fileType;
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath + "/" + fileName;

            // 依次填写Bucket名称和Object完整路径、文件输入流
            ossClient.putObject(ALiYunOSS.BUCKET_NAME, fileName, inputStream);
            String url = "https://" + ALiYunOSS.BUCKET_NAME + "." + ALiYunOSS.ENDPOINT + "/" + fileName;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }

        return null;
    }

    public static String uploadLocal(MultipartFile f, String bizPath) {
        try {
            String ctxPath = uploadPath;
            String fileName = null;
            File file = new File(ctxPath + File.separator + bizPath + File.separator);
            if (!file.exists()) {
                file.mkdirs();// 创建文件根目录
            }
            String orgName = f.getOriginalFilename();// 获取文件名
            orgName = getFileName(orgName);
            if (orgName.indexOf(".") != -1) {
                fileName = orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.lastIndexOf("."));
            } else {
                fileName = orgName + "_" + System.currentTimeMillis();
            }
            String savePath = file.getPath() + File.separator + fileName + ".png";
            File saveFile = new File(savePath);
            FileCopyUtils.copy(f.getBytes(), saveFile);
            String dbpath = null;
            if (StringUtils.isNotEmpty(bizPath)) {
                dbpath = bizPath + File.separator + fileName;
            } else {
                dbpath = fileName;
            }
            if (dbpath.contains("\\")) {
                dbpath = dbpath.replace("\\", "/");
            }
            return dbpath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断文件名是否带盘符，重新处理
     *
     * @param fileName
     * @return
     */
    public static String getFileName(String fileName) {
        //判断是否带有盘符信息
        // Check for Unix-style path
        int unixSep = fileName.lastIndexOf('/');
        // Check for Windows-style path
        int winSep = fileName.lastIndexOf('\\');
        // Cut off at latest possible point
        int pos = (winSep > unixSep ? winSep : unixSep);
        if (pos != -1) {
            // Any sort of path separator found...
            fileName = fileName.substring(pos + 1);
        }
        //替换上传文件名字的特殊字符
        fileName = fileName.replace("=", "").replace(",", "").replace("&", "")
                .replace("#", "").replace("“", "").replace("”", "");
        //替换上传文件名字中的空格
        fileName = fileName.replaceAll("\\s", "");
        return fileName;
    }

}
