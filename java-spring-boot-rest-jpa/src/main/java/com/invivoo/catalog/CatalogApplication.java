package com.invivoo.catalog;

import com.invivoo.catalog.application.config.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.stream.Collectors;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class CatalogApplication implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(CatalogApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CatalogApplication.class, args);
    }

    private AppProperties appProperties;

    @Autowired
    public CatalogApplication(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Value("${server.port}")
    private String port;

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Welcome to {} ! Application started on port {}", appProperties.getName(), port);
        logger.info(appProperties.getDescription());
        logger.info("Your application started with options : {}",
                args.getOptionNames()
                        .stream()
                        .map(name -> String.format("%s=%s", name, args.getOptionValues(name)
                                .stream()
                                .reduce((value1, value2) -> String.format("%s,%s", value1, value2)).get()))
                        .collect(Collectors.toList()));
    }

}
