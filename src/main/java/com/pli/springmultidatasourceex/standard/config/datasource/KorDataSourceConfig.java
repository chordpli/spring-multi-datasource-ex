package com.pli.springmultidatasourceex.standard.config.datasource;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.pli.springmultidatasourceex.domain.user.repository.kor",
    entityManagerFactoryRef = "korEntityManagerFactory",
    transactionManagerRef = "korTransactionManager")
public class KorDataSourceConfig {
  @Bean(name = "korDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.kor")
  public DataSource korDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "korEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean korEntityManagerFactory(
      EntityManagerFactoryBuilder builder, @Qualifier("korDataSource") DataSource dataSource) {
    return builder
        .dataSource(dataSource)
        .packages("com.pli.springmultidatasourceex.domain.user.entity")
        .persistenceUnit("kor")
        .build();
  }

  @Bean(name = "korTransactionManager")
  public PlatformTransactionManager korTransactionManager(
      @Qualifier("korEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
