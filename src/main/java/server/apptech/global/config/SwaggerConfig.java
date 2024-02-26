package server.apptech.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "PANTA API DOCS",
                description = "PANTA API 명세서 입니다.",
                version = "v0"
        )
)
@Configuration
public class SwaggerConfig {


}
