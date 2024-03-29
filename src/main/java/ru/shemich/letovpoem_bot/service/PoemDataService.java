package ru.shemich.letovpoem_bot.service;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.nio.charset.StandardCharsets;
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

        ClassPathResource classPathResource = new ClassPathResource("static/docs/poem.txt");

        InputStream inputStream = classPathResource.getInputStream();
        File textFile = File.createTempFile("textPoem", ".txt");
        try {
            FileUtils.copyInputStreamToFile(inputStream, textFile);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        /*String data = "";
        ClassPathResource cpr = new ClassPathResource("static/docs/poem.txt");
        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            data = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println(e);
        }*/

        //String textPath = "static/docs/poem.txt";
        //File textFile = ResourceUtils.getFile("classpath:" + textPath);
        FileReader fr = new FileReader(textFile);
        Scanner scan = new Scanner(fr);
        ArrayList<String> arrayList = new ArrayList<>();
        String text = "* * *";
        while (scan.hasNextLine()) {
            String s = scan.nextLine();

            if (s.contains("* * *")) { //ищем звезды для разделения текста. как конец стиха
                text = text + "\n" + s;
                arrayList.add(text);//добавялем весь текст до звезд + сами звезды
                text = "";//обнуляем буфер текста
            } else {
                text = text + "\n" + s;
            }
        }
        int a = (int) (Math.random() * arrayList.size());
        String randomStr = String.format("%s",arrayList.get(a));

        scan.close();
        fr.close();
        //return  messagesService.getReplyText(randomStr);
        int numberPoem = a;
        randomStr = randomStr + "\nКоличество стихов : " + arrayList.size();
        randomStr = randomStr + "\nНомер стиха : " + (a-1);
        return randomStr;
    }
}