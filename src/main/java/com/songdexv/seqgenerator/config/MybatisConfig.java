package com.songdexv.seqgenerator.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by songdexv on 2018/3/1.
 */
@Configuration
@AutoConfigureAfter(DataSourceConfig.class)
public class MybatisConfig {
    @Bean(name = "mapperScanner")
    @Primary
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.songdexv.seqgenerator.dao.mapper");
        return mapperScannerConfigurer;
    }
}
