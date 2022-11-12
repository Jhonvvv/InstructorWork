package com.clm.common.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.clm.system.entity.SysUser;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author su
 * @Date 2022/4/26 15:05
 */
public class ExcelListener extends AnalysisEventListener<SysUser> {
    //创建list集合封装最终的数据
    List<T> list = new ArrayList<>();

    //一行一行去读取excle内容
    @Override
    public void invoke(SysUser user, AnalysisContext analysisContext) {
        System.out.println("***"+user);
//        list.add(user);
    }

    //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    //读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
