package ru.shemich.letovpoem_bot;


import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.shemich.letovpoem_bot.botapi.TelegramFacade;

import java.io.File;
import java.io.InputStream;


public class LetovPoemBot extends TelegramWebhookBot {
    private String webHookPath;
    private String botUserName;
    private String botToken;

    private TelegramFacade telegramFacade;


    public LetovPoemBot(DefaultBotOptions botOptions, TelegramFacade telegramFacade) {
        super(botOptions);
        this.telegramFacade = telegramFacade;
    }


    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        final BotApiMethod<?> replyMessageToUser = telegramFacade.handleUpdate(update);

        return replyMessageToUser;
    }

    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    @SneakyThrows
    public void sendPhoto(long chatId, String imageCaption) {
        try {
            ClassPathResource classPathResource = new ClassPathResource("static/images/letovpoem_logo.jpg");

            InputStream inputStream = classPathResource.getInputStream();
            File imageFile = File.createTempFile("imagePoem", ".txt");
            try {
                FileUtils.copyInputStreamToFile(inputStream, imageFile);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
            /*File image = ResourceUtils.getFile("classpath:" + imagePath);*/
            SendPhoto sendPhoto = new SendPhoto().setPhoto(imageFile);
            sendPhoto.setChatId(chatId);
            sendPhoto.setCaption(imageCaption);
            execute(sendPhoto);
        } catch (TelegramApiRequestException e) {
            //e.printStackTrace();
            System.out.println("!!!ERROR!!! " + e);
        }
    }

    @SneakyThrows
    public void sendDocument(long chatId, String caption, File sendFile) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        sendDocument.setCaption(caption);
        sendDocument.setDocument(sendFile);
        execute(sendDocument);
    }

}
