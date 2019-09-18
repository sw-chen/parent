package com.sw;

import com.sw.file.FileUploadConfig;
import com.sw.minitor.MonitorConfig;
import com.sw.minitor.SwaggerAccessLogPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoConfigure implements ApplicationContextAware {
    private final static Logger logger = LoggerFactory.getLogger(AutoConfigure.class);

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Bean
    @ConfigurationProperties(prefix = "dfs.ueFileServer")
    public FileUploadConfig initFileConfigOfLocal() {
        FileUploadConfig config = new FileUploadConfig();
        logger.info("来自手动配置提示--->[ue本地]上传服务配置启动");
        return config;
    }

    @Bean("monitorConfig")
    @ConditionalOnProperty(prefix="monitor", name="swagger-access-log", havingValue="true")
    @ConfigurationProperties(prefix = "monitor")
    public MonitorConfig monitorConfig() {
        return new MonitorConfig();
    }

    @Bean
    @ConditionalOnBean(MonitorConfig.class)
    public SwaggerAccessLogPoint createControllerMonitor(@Qualifier("monitorConfig") MonitorConfig monitorConfig) {
        logger.info("启动Controller访问日志记录器");
        return new SwaggerAccessLogPoint(monitorConfig);
    }


}
