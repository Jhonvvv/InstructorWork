package com.clm;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author su
 * @Date 2021/11/21 13:02
 */
public class CodeGenerator {
    @Test
    public void generateCode(){
        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("su");
        gc.setOpen(false); //生成后是否打开资源管理器
        gc.setFileOverride(true); //重新生成时文件是否覆盖
        /*
         * mp生成service层代码，默认接口名称第一个字母有 I
         *
         * */
        gc.setServiceName("%sService");	//去掉Service接口的首字母I
        gc.setIdType(IdType.ASSIGN_ID); //主键策略
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
        gc.setSwagger2(true);//开启Swagger2模式

        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://124.71.71.128:3306/clm?serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("password");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("counselor"); //模块名
        pc.setParent("com.clm");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5、自定义需要填充的字段 数据库中的字段
        List<TableFill> tableFillList=new ArrayList<>();
        tableFillList.add(new TableFill("del_flag", FieldFill.INSERT));
        tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
        tableFillList.add(new TableFill("create_by", FieldFill.INSERT));
        tableFillList.add(new TableFill("update_time", FieldFill.INSERT_UPDATE));
        tableFillList.add(new TableFill("update_by", FieldFill.INSERT_UPDATE));

        // 6、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("punishment_approve");
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀
        strategy.setEntityTableFieldAnnotationEnable(true);//属性上加上说明注解
        strategy.setLogicDeleteFieldName("del_flag");//说明逻辑删除字段

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作
        strategy.setTableFillList(tableFillList);//自动填充设置


        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符



        mpg.setStrategy(strategy);

        // 7、执行
        mpg.execute();

    }
}
