package com.bendsmith.spring.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import static org.hibernate.cfg.Environment.*;

@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScans(value= {
		@ComponentScan("com.bendsmith.spring.dao"),
		@ComponentScan("com.bendsmith.spring.service"),
})
public class AppConfig {

	
	@Autowired
	private Environment env;
	
	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		Properties props = new Properties();
		
		// JDBC properties
		props.put(DRIVER, env.getProperty("spring.datasource.driver"));
		props.put(URL, env.getProperty("spring.datasource.url"));
		props.put(USER, env.getProperty("spring.datasource.username"));
		props.put(PASS, env.getProperty("spring.datasource.password"));
		
		// Hibernate properties
		props.put(SHOW_SQL, env.getProperty("spring.jpa.hibernate.show_sql"));
		props.put(HBM2DDL_AUTO, env.getProperty("spring.jpa.hibernate.hbm2ddl-auto"));
		
		// C3P0 properties
		props.put(C3P0_MIN_SIZE, env.getProperty("spring.jpa.hibernate.C3P0.min_size"));
		props.put(C3P0_MAX_SIZE, env.getProperty("spring.jpa.hibernate.C3P0.max_size"));
		props.put(C3P0_ACQUIRE_INCREMENT, env.getProperty("spring.jpa.hibernate.C3P0.acquire_increment"));
		props.put(C3P0_TIMEOUT, env.getProperty("spring.jpa.hibernate.C3P0.time_out"));
		props.put(C3P0_MAX_STATEMENTS, env.getProperty("spring.jpa.hibernate.C3P0.max_statements"));
		
		sessionFactory.setHibernateProperties(props);
		sessionFactory.setPackagesToScan("com.bendsmith.spring.model");
		
		return sessionFactory;
		
	}
	
	@Bean
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());
		return transactionManager;
	}
	
}
