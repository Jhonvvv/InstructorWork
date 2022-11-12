package com.clm.common.base.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clm.common.utils.ExcelListener;
import com.clm.common.utils.Result;
import com.clm.system.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.beanutils.PropertyUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author su
 * @Date 2022/1/30 14:15
 */
@Slf4j
public class ClmController<T,S extends IService<T>> {
    @Autowired
    S service;

    /**
     * 通过Excel 导入数据
     * @param request
     * @param response
     * @param clazz
     * @return
     */
    protected Result<?> importExcel(HttpServletRequest request, HttpServletResponse response,Class<T> clazz) {
        MultipartHttpServletRequest multipartRequest= (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap=multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entry: fileMap.entrySet()){
            MultipartFile file=entry.getValue();//获取文件上传对象
            ImportParams params=new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<T> list= ExcelImportUtil.importExcel(file.getInputStream(),clazz,params);
                int count=0;
                long start=System.currentTimeMillis();
                for (T item: list) {
                    try {
                        if(service.save(item)){
                            count++;
                        }
                    }catch (Exception e){
//                        e.printStackTrace();
                    }
                }
                log.info("消耗时间"+(System.currentTimeMillis()-start)+"毫秒");
                return Result.OK().success("文件导入成功！不重复数据行数：" + count);
            }catch (Exception e){
                log.error(e.getMessage());
                return Result.error("文件导入失败:"+e.getMessage());
            }finally {
                try {
                    file.getInputStream().close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return Result.error("文件导入失败！");
    }


    /**
     * 导出excel
     *
     * @param request
     */
    protected ModelAndView exportXls(HttpServletRequest request, T object, Class<T> clazz, String title) {
        // Step.1 组装查询条件
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();


        // Step.2 获取导出数据
        List<T> pageList = service.list(queryWrapper);
        List<T> exportList = null;

        // 过滤选中数据
        String selections = request.getParameter("selections");
        if (StringUtils.isNotEmpty(selections)) {
            List<String> selectionList = Arrays.asList(selections.split(","));
            exportList = pageList.stream().filter(item -> selectionList.contains(getId(item))).collect(Collectors.toList());
        } else {
            exportList = pageList;
        }

        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, title); //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.CLASS, clazz);
        ExportParams exportParams = new ExportParams(title + "报表", "导出人:" + "管理员", title);
        mv.addObject(NormalExcelConstants.PARAMS, exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }

    /**
     * 模板excel
     *
     * @param request
     */
    protected ModelAndView exportModeXls(HttpServletRequest request, List<T> exportList, Class<T> clazz, String title) {

        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, title); //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.CLASS, clazz);
        ExportParams exportParams = new ExportParams(title + "报表", "导出人:" + "无", title);
        mv.addObject(NormalExcelConstants.PARAMS, exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }


    /**
     * 获取对象ID
     *
     * @return
     */
    private String getId(T item) {
        try {
            return PropertyUtils.getProperty(item, "id").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
