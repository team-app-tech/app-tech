package server.apptech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AppTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppTechApplication.class, args);
    }

}
