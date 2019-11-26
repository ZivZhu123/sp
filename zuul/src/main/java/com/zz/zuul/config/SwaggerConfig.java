package com.zz.zuul.config;

import io.swagger.models.Swagger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SwaggerConfig
 * @Description: TODO
 * @Author zhuzhou
 * @Date 2019/11/15
 * @Version V1.0
 **/
@ConditionalOnClass(value = {Swagger.class})
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${spring.application.name}")
    private String applicationName;
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zz"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(applicationName+"接口文档")
                .description(applicationName+"接口文档")
                .contact(new Contact("miaoyouche", "http://www.miaoyouche.com", "mail.xxx@miaoyouche.com"))
                .version("1.0")
                .build();
    }

    private List<Parameter> parameters() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        parameterBuilder.name("Authorization")
                .description("Authorization")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build();
        parameters.add(parameterBuilder.build());
        return parameters;
    }

}