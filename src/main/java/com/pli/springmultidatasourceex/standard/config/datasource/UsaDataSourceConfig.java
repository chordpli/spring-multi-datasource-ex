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
    basePackages = "com.pli.springmultidatasourceex.domain.user.repository.usa",
    entityManagerFactoryRef = "usaEntityManagerFactory",
    transactionManagerRef = "usaTransactionManager")
public class UsaDataSourceConfig {
  @Bean(name = "usaDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.usa")
  public DataSource usaDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "usaEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean usaEntityManagerFactory(
      EntityManagerFactoryBuilder builder, @Qualifier("usaDataSource") DataSource dataSource) {
    return builder
        .dataSource(dataSource)
        .packages("com.pli.multidb.domain.quotation.entity")
        .persistenceUnit("usa")
        .build();
  }

  @Bean(name = "usaTransactionManager")
  public PlatformTransactionManager usaTransactionManager(
      @Qualifier("usaEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
