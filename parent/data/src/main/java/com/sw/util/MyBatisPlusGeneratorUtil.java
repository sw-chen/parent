package com.sw.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Mybatis plus 自动生成代码文件工具类
 *
 * @author xujiping
 * @date 2018/6/11 10:09
 */
public class MyBatisPlusGeneratorUtil {

    /**
     * 自动生成
     *
     * @param url         数据源URL
     * @param username    用户名
     * @param password    密码
     * @param driverName  驱动
     * @param author      作者
     * @param outputDir   输出根目录，当前项目src/main/java目录
     * @param packageName 包名
     * @param superEntity 实体类父类
     * @param tableNames  表名，支持多个表名
     */
    public static void generate(String url, String username, String password, String driverName, String author, String
            outputDir, String packageName, String superEntity, String...
                                        tableNames) {
        System.out.println(">>>自动代码开始");
        AutoGenerator mpg = new AutoGenerator();
        //全局配置
        GlobalConfig gc = new GlobalConfig();
        //生成的文件目录
        gc.setOutputDir(outputDir);
        gc.setFileOverride(false);
        gc.setActiveRecord(true);
        //XML 二级缓存
        gc.setEnableCache(true);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(false);
        //作者
        gc.setAuthor(author);
        gc.setServiceName("%sService");
        mpg.setGlobalConfig(gc);
        //数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(url)
                .setUsername(username)
                .setPassword(password)
                .setDriverName(driverName);
        mpg.setDataSource(dataSourceConfig);
        //策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy
                .setCapitalMode(true)
                .setEntityLombokModel(false)
//                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
                //修改替换成你需要的表名，多个表名传数组
                .setInclude(tableNames)
                .setSuperEntityClass(superEntity);
        mpg.setStrategy(strategy);
        //包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(packageName)
                .setController("controller")
                .setEntity("entity");
        mpg.setPackageInfo(pc);
        mpg.execute();
        System.out.println("自动代码结束<<<");
    }

    /**
     * 修改配置之后再执行
     * 会覆盖原始文件，请谨慎执行
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String path = new MyBatisPlusGeneratorUtil().getClass().getResource("/").toString();
        Pattern pattern = Pattern.compile("[C-G]\\S*site\\/");
        Matcher matcher = pattern.matcher(path);
        String result = null;
        if (matcher.find()) {
            result = matcher.group(0);
            System.out.println("匹配路径为:"+result);
        }

        if (result == null) {
            throw new Exception("文件路径无法匹配");
        }

        String url = "jdbc:mysql://192.168.1.19:3306/lmt_admin?characterEncoding=utf8&useUnicode=true&useSSL=false&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "ssds123456";
        String driverName = "com.mysql.jdbc.Driver";
        String author = "lmt";
        // TODO 检查输出目录是否正确
        String outputDir = result + "src/main/java";
        String packageName = "cn.lmt";
        String superEntity = "com.baomidou.mybatisplus.activerecord.Model";
        generate(url, username, password, driverName, author, outputDir, packageName, superEntity,
                "lmt_market_order");
        System.out.println("代码生成后，记得Entity文件导入Serializable包");
    }
}
