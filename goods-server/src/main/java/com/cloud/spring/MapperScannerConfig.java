package com.cloud.spring;

import com.cloud.mybatis.CrudMapper;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * 保证在MyBatisConfig实例化之后再实例化该类
 *
 * @author zhuz
 * @date 2020/7/31
 */
@Configuration
@AutoConfigureAfter(MyBatisConfig.class)
public class MapperScannerConfig {
    /**
     * mapper接口的扫描器
     *
     * @param
     * @return {@link MapperScannerConfigurer}
     * @author zhuz
     * @date 2020/7/31
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.cloud.dao");

        Properties properties = new Properties();
        properties.setProperty("mappers", CrudMapper.class.getName());
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");
        properties.setProperty("ORDER", "BEFORE");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }

}
