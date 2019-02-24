package com;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 *
 * @author lance
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    @Value("${server.servlet.context-path}")
    private String proJName;

    /**
     * 创建API应用
     *
     * @return
     */
    @Bean
    public Docket createRestApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lance.business.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 展示文档的基本信息
     *
     * @return
     */
    private ApiInfo apiInfo()
    {
        return new ApiInfoBuilder()
                .title("httpApiDemo")
                .description("show-page")
                .termsOfServiceUrl(proJName+"/swagger-ui.html").contact(
                        new Contact("lance", proJName + "/druid", "lance2038@hotmail.com")
                )
                .version("1.0")
                .build();
    }
}
