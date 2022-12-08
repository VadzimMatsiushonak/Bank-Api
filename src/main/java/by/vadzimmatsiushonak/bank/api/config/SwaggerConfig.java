package by.vadzimmatsiushonak.bank.api.config;

import by.vadzimmatsiushonak.bank.api.BankApiApplication;
import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .apiInfo(apiInfo())
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

}
