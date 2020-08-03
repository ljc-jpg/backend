package com.cloud.spring;


import feign.Logger;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
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
 * @author zhu
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
     * @param
     * @return {@link RestTemplate}
     * @author zhuz
     * @date 2020/7/31
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * feign支持multipartFormEncoder支持
     *
     * @param
     * @return {@link Encoder}
     * @author zhuz
     * @date 2020/7/31
     */
    @Bean
    @Primary
    @Scope("prototype")
    public Encoder multipartFormEncoder() {
        return new SpringFormEncoder();
    }

    /**
     * feign日志级别
     *
     * @param
     * @return {@link Logger.Level}
     * @author zhuz
     * @date 2020/7/31
     */
    @Bean
    public feign.Logger.Level multipartLoggerLevel() {
        return feign.Logger.Level.FULL;
    }


}
