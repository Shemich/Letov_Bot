package ru.shemich.letovpoem_bot.service;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Генерирует стихотворение
 *
 */

@Service
public class PoemDataService {
    private final Random random = new Random();
    private ReplyMessagesService messagesService;

    public PoemDataService(ReplyMessagesService messagesService) throws FileNotFoundException, IOException {
        this.messagesService = messagesService;
    }

    public String getPoemData() throws IOException {

        FileReader fr = new FileReader("D:/CODING/JAVA/Bots/letovpoem_bot/src/main/resources/static/docs/poem.txt");
        Scanner scan = new Scanner(fr);
        ArrayList<String> arrayList = new ArrayList<>();
        String text = "* * *";
        while (scan.hasNextLine()) {
            String s = scan.nextLine();

            if (s.contains("* * *")) { //ищем звезды для разделения текста. как конец стиха
                //System.out.println(s);
                text = text + "\n" + s;
                arrayList.add(text);//добавялем весь текст до звезд + свми звезды
                text = "";//обнуляем буфер текста
            } else {
                //System.out.println(s);
                text = text + "\n" + s;
                /*
                if (s != "") {
                    text = text + "\n" + s;
                }

                 */
            }
        }
        int a = (int) (Math.random() * arrayList.size());
        String randomStr = String.format("%s",arrayList.get(a));

        scan.close();
        fr.close();
        //return  messagesService.getReplyText(randomStr);
        randomStr = randomStr + "\nКоличество стихов : " + arrayList.size();
        randomStr = randomStr + "\nНомер стиха : " + (a-1);
        return randomStr;
    }
}