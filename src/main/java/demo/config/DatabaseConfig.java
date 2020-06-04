package demo.config;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@MapperScan(value = {"demo.**.mapper"}, sqlSessionTemplateRef = "db1SqlSessionTemplate")
public class DatabaseConfig {
    
    @Bean(name = "hikariDataSource1") 
    @Primary
    @ConfigurationProperties("spring.datasource.hikari.test")
    public DataSource hikariDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(hikariDataSource());
    }

    @Bean(name = "sqlSessionFactory1") 
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("hikariDataSource1") DataSource datasource, ApplicationContext applicationContext) throws Exception { 
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean(); 
        sqlSessionFactory.setDataSource(datasource); 
        //sqlSessionFactory.setTypeAliasesPackage("**.mapper");
        sqlSessionFactory.setConfigLocation(applicationContext.getResource("classpath:/config/mybatis-config.xml"));
        sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/*Mapper.xml")); 
        return sqlSessionFactory.getObject(); 
    } 
        
    @Bean(name = "db1SqlSessionTemplate")
    @Primary
     public SqlSessionTemplate sqlSession(@Qualifier("sqlSessionFactory1") SqlSessionFactory sqlSessionFactory) { 
        return new SqlSessionTemplate(sqlSessionFactory); 
    }
 
}
