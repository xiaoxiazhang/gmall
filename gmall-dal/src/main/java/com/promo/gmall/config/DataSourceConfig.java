package com.promo.gmall.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 德鲁伊数据源配置
 *
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class DataSourceConfig {

    @Resource
    private DruidDataSourceProp druidDataSourceProp;

    private static final String MYSQL_SUFFIX = "?useSSL=false&useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&allowMultiQueries=true&tinyInt1isBit=false";

    @Bean(initMethod = "init")
    public DruidDataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(druidDataSourceProp.getMallDbUrl() + MYSQL_SUFFIX);
        datasource.setUsername(druidDataSourceProp.getMallDbUsername());
        datasource.setPassword(druidDataSourceProp.getMallDbPassword());
        datasource.setDriverClassName(druidDataSourceProp.getDriverClassName());
        commonConfig(datasource);
        return datasource;

    }


    private void commonConfig(DruidDataSource datasource) {
        //配置连接池的初始化大小，最大值，最小值
        datasource.setInitialSize(druidDataSourceProp.getInitSize());
        datasource.setMaxActive(druidDataSourceProp.getMaxActive());
        datasource.setMinIdle(druidDataSourceProp.getMinIdle());
        //配置获取连接等待超时的时间
        datasource.setMaxWait(60000);
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        datasource.setTimeBetweenEvictionRunsMillis(60000);
        //配置一个连接在池中最小生存的时间，单位是毫秒
        datasource.setMinEvictableIdleTimeMillis(300000);

        //用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
        datasource.setValidationQuery("SELECT 'x'");
        //申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
        datasource.setTestOnBorrow(false);
        //建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
        datasource.setTestWhileIdle(true);
        //归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
        datasource.setTestOnReturn(false);
        //是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭
        datasource.setPoolPreparedStatements(false);
        //要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
        datasource.setMaxPoolPreparedStatementPerConnectionSize(-1);

        //慢sql的记录
        StatFilter statFilter = new StatFilter();
        statFilter.setSlowSqlMillis(200);
        statFilter.setLogSlowSql(true);
        datasource.setProxyFilters(Lists.newArrayList(statFilter));
    }


}
