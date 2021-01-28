package com.healthcare.restservice;

import com.healthcare.restservice.utils.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
@EnableSwagger2
public class RestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }

    @Bean
    public Docket NetMedsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(
                        Collections.singletonList(new ParameterBuilder()
                                .name("Authorization")
                                .description("JWT Authentication token")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(true)
                                .build()));
    }
}
