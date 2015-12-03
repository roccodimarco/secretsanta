package com.github.warabak.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.github.warabak.services"})
@EntityScan(basePackages = {"com.github.warabak.persistence.models"})
@EnableJpaRepositories(basePackages = {"com.github.warabak.persistence.repositories"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}