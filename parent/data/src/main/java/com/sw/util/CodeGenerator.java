package com.sw.util;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator generator = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");   //获取用户的当前工作目录
        System.out.println("projectPath:" + projectPath);
        //是否打开输出目录
        gc.setAuthor("chenshiwan");
        gc.setOutputDir(projectPath+"/data/src/main/java");
        gc.setOpen(false);
        //实体属性 Swagger2 注解
        gc.setSwagger2(true);
        gc.setEnableCache(true);
        generator.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8&allowMultiQueries=true&useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root123456");
        generator.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName("");
        pc.setParent("com.sw");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setServiceImpl("service.impl");
        pc.setService("service");
        pc.setXml("xml");

        generator.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String controllerPath = "/templates/controller.java.ftl";
        String servicePath = "/templates/service.java.ftl";
        String serviceImplPath = "/templates/serviceImpl.java.ftl";
        String mapperPath = "/templates/mapper.java.ftl";
        String mapperXmlPath = "/templates/mapper.xml.ftl";
        String entityPath = "/templates/entity.java.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        String basePath = "/src/main/java/com/sw";
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(controllerPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/admin" + basePath + "/" + pc.getController()
                        + "/" + tableInfo.getControllerName() + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(servicePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/admin" + basePath + "/" + pc.getService()
                        + "/" + tableInfo.getServiceName() + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(serviceImplPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/admin" + basePath + "/" + pc.getService() + "/impl"
                        + "/" + tableInfo.getServiceImplName() + StringPool.DOT_JAVA;
            }
        });
//        focList.add(new FileOutConfig(mapperPath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return projectPath + "/data" + basePath + "/" + pc.getMapper()
//                        + "/" + tableInfo.getMapperName() + StringPool.DOT_JAVA;
//            }
//        });
        focList.add(new FileOutConfig(mapperXmlPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/data" + basePath + "/" + pc.getMapper() + "/" + pc.getXml()
                        + "/" + tableInfo.getMapperName() + StringPool.DOT_XML;
            }
        });
//        focList.add(new FileOutConfig(entityPath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return projectPath + "/data" + basePath + "/" + pc.getEntity()
//                        + "/" + tableInfo.getEntityName() + StringPool.DOT_JAVA;
//            }
//        });

//        cfg.setFileCreate(new IFileCreate() {
//            @Override
//            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
//                // 判断自定义文件夹是否需要创建
//                checkDir(filePath);
//                return false;
//            }
//        });
        cfg.setFileOutConfigList(focList);
        generator.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

//        templateConfig.setXml(null);
        templateConfig.setController(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        generator.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model");
        strategy.setSuperServiceClass(null);
        strategy.setCapitalMode(true);
        strategy.setEntityLombokModel(true);

//        strategy.setRestControllerStyle(true);
//        strategy.setSuperControllerClass("com.baomidou.ant.com.sw.common.BaseController");
        strategy.setInclude("role");      //表名
//        strategy.setSuperEntityColumns("id");
//        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");
        generator.setStrategy(strategy);
        generator.setTemplateEngine(new FreemarkerTemplateEngine());
        generator.execute();
    }

}
