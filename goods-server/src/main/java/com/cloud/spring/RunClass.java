package com.cloud.spring;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RunClass {
    public static void main(String[] args) {
        try {
            List<String> warnings = new ArrayList<>();
            boolean overwrite = true;
            File configFile = new ClassPathResource("generatorConfig.xml").getFile();
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            System.out.println("代码生成完毕>>>>>>>>>>>>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
