package com.travel.travel_on.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfig {
    @Bean
    public Docket commonApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("example")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.api.shop.modules.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.ShopApiInfo())
                .tags(   new Tag("AuthController", "Auth API")
                        , new Tag("MemberController", "Member API")
                );

    }

    private ApiInfo ShopApiInfo() {
        return new ApiInfoBuilder()
                .title("shop API")
                .description("shop API")
                .termsOfServiceUrl("http://www.shop-api.com")
                .version("1.0")
                .build();
    }
}
