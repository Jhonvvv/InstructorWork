package com.clm.system.controller;

import com.clm.common.utils.Result;
import com.clm.common.utils.UploadFileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author su
 * @Date 2021/12/4 11:50
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/system/common")
@Api(tags = "公共接口")
public class CommonController {

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public Result<Map<String,String>> upload(HttpServletRequest request, HttpServletResponse response){
        Result<Map<String,String>> result = new Result<>();
        String bizPath = request.getParameter("biz");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");// 获取上传文件对象
        if (StringUtils.isEmpty(bizPath)){
            bizPath="user";
        }
        Map<String, String> upload = UploadFileUtils.upload(bizPath, file);
        result.setResult(upload);
        result.setMessage("上传成功");
        return result;
    }
}
