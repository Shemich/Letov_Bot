package ru.shemich.letovpoem_bot.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Избранные стихи пользователя
 */

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "userFavouriteData")
public class UserFavouriteData implements Serializable {
    @Id
    String id;
    String name;
    String gender;
    String color;
    String movie;
    String song;
    int age;
    int number;
    long chatId;


    @Override
    public String toString() {
        return String.format("Имя: %s%nВозраст: %d%nПол: %s%nЛюбимая цифра: %d%n" +
                        "Цвет: %s%nФильм: %s%nПесня: %s%n", getName(), getAge(), getGender(), getNumber(),
                getColor(), getMovie(), getSong());
    }
}
