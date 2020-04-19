package com.cloud;

import com.cloud.filters.PreAuthFilter;
import com.cloud.filters.RibbonFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
@RefreshScope
public class ZuulServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApplication.class, args);
    }

    @Bean
    public PreAuthFilter PreAuthFilter() {
        return new PreAuthFilter();
    }

    @Bean
    public RibbonFilter RibbonFilter() {
        return new RibbonFilter();
    }
}
