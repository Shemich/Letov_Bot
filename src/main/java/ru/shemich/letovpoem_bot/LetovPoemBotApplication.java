package ru.shemich.letovpoem_bot;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LetovPoemBotApplication {

    @SneakyThrows
    public static void main(String[] args)  {

        SpringApplication.run(LetovPoemBotApplication.class, args);
    }

}
