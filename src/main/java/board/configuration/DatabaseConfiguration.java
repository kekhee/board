package board.configuration;


import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
//@PropertySource
@EnableTransactionManagement
// 1 프로포티스 사용 설정파일의 위치
public class DatabaseConfiguration {

	// 3
	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	// 2프로포티스에 spring.datasource.hikari로 설정되어있음
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}

	// 3 히카리cp설정파일로 dataSource 생성
	@Bean
	public DataSource dataSource() throws Exception {
		DataSource dataSource = new HikariDataSource(hikariConfig());
		// 정상 데이터소스 출력확인!!!
		//System.out.println(dataSource.toString());
		return dataSource;
	}

	//4-2 프로포티스 설정중 마이바티스 설정 가지고옴 
	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig() {
		//가지고온 마이바티스 설정 자바클래스로 반환 4에 추가 
		return new org.apache.ibatis.session.Configuration();
	}

	// 4.sqlSessionFactory생성 매퍼 파일 위치설정
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/sql-*.xml")); //매퍼위치 
		//4-2 메서드 생성후 기재 
		sqlSessionFactoryBean.setConfiguration(mybatisConfig());
		return sqlSessionFactoryBean.getObject();
	}

	// 4-1
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}


	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		return new DataSourceTransactionManager(dataSource());
	}
	
	//jpa설정
	@Bean
	@ConfigurationProperties(prefix="sping.jpa")
	public Properties hibernateConfig() {
		return new Properties();
	}
		
		
		
		
	
	
}