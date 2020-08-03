package com.cloud.spring;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * spring 主配置
 *
 * @author zhuz
 */
@Configuration
@ComponentScan(basePackages = "com")
@EnableFeignClients("com.cloud.service")
@EnableAutoConfiguration
@EnableScheduling
public class MatrixApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MatrixApplication.class);
    }

    /**
     * 配置RestTemplate
     *
     * @author zhuz
     * @date 2020/8/3
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public feign.Logger.Level multipartLoggerLevel() {
        return feign.Logger.Level.FULL;
    }


}
