
package com.example.hr.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI hrManagementOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("HR Employee Management System API")
                        .description(
                                "REST APIs for managing employees, "
                                + "departments, attendance, leave requests, "
                                + "and performance reviews.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("HR Management API Team")));
    }
}

