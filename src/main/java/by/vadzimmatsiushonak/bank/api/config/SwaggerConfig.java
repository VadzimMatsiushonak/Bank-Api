package by.vadzimmatsiushonak.bank.api.config;

import by.vadzimmatsiushonak.bank.api.BankApiApplication;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .apiInfo(apiInfo())
            .securityContexts(List.of(securityContext()))
            .securitySchemes(List.of(apiKey()))
            .select()
            .apis(RequestHandlerSelectors.basePackage(BankApiApplication.class.getPackageName()))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
            "Bank-Api Documentation",
            "The project is an example of a banking API that contains all the functions related to storing data and providing an API to work with it.",
            "1.0",
            "",
            new Contact(
                "Vadzim Matsiushonak",
                "https://github.com/VadzimMatsiushonak/Bank-Api",
                "vadzimmatsiushonak@gmail.com"),
            "",
            "",
            new ArrayList<>());
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("JWT", authorizationScopes));
    }

}
