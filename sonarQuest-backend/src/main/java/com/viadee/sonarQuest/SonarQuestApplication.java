package com.viadee.sonarQuest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class SonarQuestApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SonarQuestApplication.class, args);
    }

    @Override
    public void run(String... strings) {
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200");
            }
        };
    }

    @Bean
    public GameDataInitializer gameDataInitialializer() {
        return new GameDataInitializer();
    }
}
