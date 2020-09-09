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
//���ϱ�ע��@Configurationע�⣬��ζ������һ��IoC����������,�൱��xml
@Configuration
@MapperScan(basePackages = "com.sprjjs.book.mapper",sqlSessionFactoryRef ="sqlSessionFactory" )
public class DataSourceTransactionConfiguration {
    /**
     * ��������Դ
     * @return
     */
    //��ע@Bean��ʾ����spring�������й���id="dataSource"
    @Bean(name = "dataSource")
    @Primary  //��ͬ����DataSource�У�����ʹ�ñ���ע��DataSource
    //��yml�����ļ���ָ��ǰ׺������������ֵע�뵽��bean�������У���bean��Ҫ�ṩset��get����
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        System.out.println("��������Դ");
        return new DruidDataSource();
    }

    /**
     * ����SqlSessionFactory,������Դע�뵽SqlSessionFactory��
     * @param dataSource ����Դ
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        System.out.println("����SqlSessionFactory");
        //ע������Դ
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        //��ĳ�����µ������������ ������Ϊ�����������ִ�Сд
        sqlSessionFactoryBean.setTypeAliasesPackage("com.sprjjs.book.pojo");
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * �������������,������Դע�뵽SqlSessionFactory��
     * @param dataSource ����Դ
     * @return
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        System.out.println("�������������");
        return new DataSourceTransactionManager(dataSource);
    }

}
