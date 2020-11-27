package ru.shemich.letovpoem_bot;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

@SpringBootApplication
public class LetovPoemBotApplication {

    @SneakyThrows
    public static void main(String[] args) throws FileNotFoundException {



        SpringApplication.run(LetovPoemBotApplication.class, args);
    }

}
