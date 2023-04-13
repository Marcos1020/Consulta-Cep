package com.sanches.consultacep.swaggerConfig;

import com.sanches.consultacep.utils.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(Constants.SWAGGER_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag("WIPRO", Constants.SWAGGER_DESCRIPTION_TAG))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(Constants.SWAGGER_TITLE_API)
                .description(Constants.SWAGGER_API_DESCRIPTION_APRESENTATION)
                .version(Constants.SWAGGER_API_VERSION)
                .build();
    }
}