package mk.ukim.finki.mp.smsManagement;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
public class AppConfig {

	private static final String PN_DATABASE_DRIVER = "db.driver";
	private static final String PN_DATABASE_PASSWORD = "db.password";
	private static final String PN_DATABASE_URL = "db.url";
	private static final String PN_DATABASE_USERNAME = "db.username";

	private static final String PN_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PN_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PN_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
	private static final String PN_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	private static final String PN_HIBERNATE_CACHE_PROVIDER="hibernate.cache.provider_class";

	@Resource
	private Environment env;

	private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put(PN_HIBERNATE_DIALECT,
				env.getRequiredProperty(PN_HIBERNATE_DIALECT));
		properties.put(PN_HIBERNATE_SHOW_SQL,
				env.getRequiredProperty(PN_HIBERNATE_SHOW_SQL));
		properties.put(PN_HIBERNATE_HBM2DDL_AUTO,
				env.getRequiredProperty(PN_HIBERNATE_HBM2DDL_AUTO));
		return properties;
	}

	@Bean(name="myDataSource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(env
				.getRequiredProperty(PN_DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(PN_DATABASE_URL));
		dataSource.setUsername(env.getRequiredProperty(PN_DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PN_DATABASE_PASSWORD));

		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setPackagesToScan(env
				.getRequiredProperty(PN_ENTITYMANAGER_PACKAGES_TO_SCAN));
		sessionFactoryBean.setHibernateProperties(hibProperties());
		return sessionFactoryBean;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	@Bean
	public UrlBasedViewResolver setupViewResolver() {
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/pages/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}

}
