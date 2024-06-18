package com.pli.springmultidatasourceex.standard.config.datasource;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.pli.springmultidatasourceex.domain.user.repository.jpn",
    entityManagerFactoryRef = "jpnEntityManagerFactory",
    transactionManagerRef = "jpnTransactionManager")
public class JpnDataSourceConfig {

  @Primary
  @Bean(name = "jpnDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.jpn")
  public DataSource jpnDataSource() {
    return DataSourceBuilder.create().type(com.zaxxer.hikari.HikariDataSource.class).build();
  }

  @Primary
  @Bean(name = "jpnEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean jpnEntityManagerFactory(
      EntityManagerFactoryBuilder builder, @Qualifier("jpnDataSource") DataSource dataSource) {
    return builder
        .dataSource(dataSource)
        .packages("com.pli.springmultidatasourceex.domain.user.entity")
        .persistenceUnit("jpn")
        .build();
  }

  @Primary
  @Bean(name = "jpnTransactionManager")
  public PlatformTransactionManager jpnTransactionManager(
      @Qualifier("jpnEntityManagerFactory") EntityManagerFactory deEntityManagerFactory) {
    return new JpaTransactionManager(deEntityManagerFactory);
  }
}
