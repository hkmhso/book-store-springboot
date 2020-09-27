package com.sprjjs.book.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;

import javax.sql.DataSource;
import java.sql.SQLException;
//类上标注了@Configuration注解，意味着它是一个IoC容器配置类,相当于xml
@Configuration
@MapperScan(basePackages = "com.sprjjs.book.mapper",sqlSessionFactoryRef ="sqlSessionFactory" )
public class DataSourceTransactionConfiguration {
    /**
     * 配置数据源
     * @return
     */
    //标注@Bean表示交给spring容器进行管理，id="dataSource"
    @Bean(name = "dataSource")
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
    //将yml属性文件中指定前缀的属性依次设值注入到该bean的属性中，该bean需要提供set和get方法
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        System.out.println("配置数据源");
        return new DruidDataSource();
    }

    /**
     * 配置SqlSessionFactory,将数据源注入到SqlSessionFactory中
     * @param dataSource 数据源
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        System.out.println("配置SqlSessionFactory");
        //注入数据源
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        //给某个包下的所有类起别名 ，别名为类名（不区分大小写
        sqlSessionFactoryBean.setTypeAliasesPackage("com.sprjjs.book.pojo");
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 配置事务管理器,将数据源注入到SqlSessionFactory中
     * @param dataSource 数据源
     * @return
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        System.out.println("配置事务管理器");
        return new DataSourceTransactionManager(dataSource);
    }

}
