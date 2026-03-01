package kz.persona.core;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@EnableScheduling
@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties
public class PersonaCoreApplication {

    @Value("${application.timezone}")
    private String applicationTimeZone;

    public static void main(String[] args) {
        SpringApplication.run(PersonaCoreApplication.class, args);
    }

    @PostConstruct
    public void executeAfterMain() {
        TimeZone.setDefault(TimeZone.getTimeZone(applicationTimeZone));
    }
}
