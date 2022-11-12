package com.clm.common.base.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author su
 * @Date 2021/11/21 18:39
 */
@Component
public class FillMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("status",1,metaObject);
        this.setFieldValByName("delFlag",0,metaObject);
        //TODO 当前登录用户
        this.setFieldValByName("createBy","admin",metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime",new Date(),metaObject);
        //TODO 当前登录用户
        this.setFieldValByName("updateBy","admin",metaObject);
    }
}
