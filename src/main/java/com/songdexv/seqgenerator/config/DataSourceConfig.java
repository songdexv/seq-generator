package com.songdexv.seqgenerator.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * Created by songdexv on 2018/3/1.
 */
@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties(DruidDataSourceProperties.class)
public class DataSourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
    @Autowired
    private DruidDataSourceProperties druidDataSourceProperties;

    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(druidDataSourceProperties.getUrl());
        dataSource.setUsername(druidDataSourceProperties.getUsername());
        dataSource.setPassword(druidDataSourceProperties.getPassword());
        dataSource.setDriverClassName(druidDataSourceProperties.getDriverClassName());
        dataSource.setInitialSize(druidDataSourceProperties.getInitialSize());
        dataSource.setMinIdle(druidDataSourceProperties.getMinIdle());
        dataSource.setMaxActive(druidDataSourceProperties.getMaxActive());
        dataSource.setMaxWait(druidDataSourceProperties.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(druidDataSourceProperties.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(druidDataSourceProperties.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(druidDataSourceProperties.getValidationQuery());
        dataSource.setTestWhileIdle(druidDataSourceProperties.isTestWhileIdle());
        dataSource.setTestOnBorrow(druidDataSourceProperties.isTestOnBorrow());
        dataSource.setTestOnReturn(druidDataSourceProperties.isTestOnReturn());
        dataSource.setPoolPreparedStatements(druidDataSourceProperties.isPoolPreparedStatements());
        try {
            if (!StringUtils.isEmpty(druidDataSourceProperties.getFilters())) {
                dataSource.setFilters(druidDataSourceProperties.getFilters());
            }
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.songdexv.seqgenerator.dao.entity");
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources
                ("classpath*:mappers/com/songdexv/seqgenerator/dao/mapper/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
