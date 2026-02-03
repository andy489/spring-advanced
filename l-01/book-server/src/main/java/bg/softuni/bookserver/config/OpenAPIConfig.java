package bg.softuni.bookserver.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI openAPI() {
        Contact contact = new Contact()
                .email("stoev.andy@gmail.com")
                .name("andy489");

        Info info = new Info().title("My Book API")
                .contact(contact)
                .description("Small API for books/authors description");

        return new OpenAPI().info(info);
    }

    // endpoint: /swagger-ui/index.html
}