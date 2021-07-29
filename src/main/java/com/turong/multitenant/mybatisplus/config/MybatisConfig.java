package com.turong.multitenant.mybatisplus.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@MapperScan("com.turong.multitenant.mybatisplus.mapper")
@Log4j2
public class MybatisConfig {

    private static final String TENANT_COLUMN = "tenant";

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource dataSource() throws SQLException {
        return DataSourceBuilder.create().build();
    }

    @Bean("dataSourceTurkey")
    @ConfigurationProperties(prefix = "spring.datasource.tr")
    public DataSource dataSourceTurkey() throws SQLException {
        return DataSourceBuilder.create().build();
    }

    @Bean("dataSourceIndonesia")
    @ConfigurationProperties(prefix = "spring.datasource.ind")
    public DataSource dataSourceIndonesia() throws SQLException {
        return DataSourceBuilder.create().build();
    }

    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource(@Qualifier("dataSource") DataSource dataSourceMaster,
                                        @Qualifier("dataSourceTurkey") DataSource dataSourceTurkey,
                                        @Qualifier("dataSourceIndonesia") DataSource dataSourceIndonesia) {
        log.info("Dynamic datasource");
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSources.MASTER.getTenant(), dataSourceMaster);
        dataSourceMap.put(DataSources.TR.getTenant(), dataSourceTurkey);
        dataSourceMap.put(DataSources.IND.getTenant(), dataSourceIndonesia);

        dynamicRoutingDataSource.setDefaultTargetDataSource(dataSourceMaster);
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);

        return dynamicRoutingDataSource;
    }

    @Bean
    @Primary
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(@Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        mybatisSqlSessionFactoryBean.setConfiguration(this.getConfiguration());

        return mybatisSqlSessionFactoryBean;

    }

    @Bean
    protected MybatisConfiguration getConfiguration() {
        MybatisConfiguration config = new MybatisConfiguration();
        config.setMapUnderscoreToCamelCase(true);
        Properties properties = new Properties();
        properties.setProperty("dialect", "MySQL");
        config.setVariables(properties);
        config.setJdbcTypeForNull(JdbcType.NULL);
        config.setCacheEnabled(false);
        config.setLazyLoadingEnabled(false);
        config.setDefaultExecutorType(ExecutorType.SIMPLE);
        return config;
    }

}
