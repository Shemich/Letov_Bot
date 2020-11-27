package ru.shemich.letovpoem_bot.botapi;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.shemich.letovpoem_bot.LetovPoemBot;
import ru.shemich.letovpoem_bot.cache.UserDataCache;
import ru.shemich.letovpoem_bot.model.UserFavouriteData;
import ru.shemich.letovpoem_bot.model.UserProfileData;
import ru.shemich.letovpoem_bot.service.MainMenuService;
import ru.shemich.letovpoem_bot.service.PoemDataService;
import ru.shemich.letovpoem_bot.service.ReplyMessagesService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */


@Component
@Slf4j
public class TelegramFacade {
    private BotStateContext botStateContext;
    private UserDataCache userDataCache;
    private MainMenuService mainMenuService;
    private LetovPoemBot letovPoemBot;
    private ReplyMessagesService messagesService;
    private PoemDataService poemDataService;

    public TelegramFacade(BotStateContext botStateContext, UserDataCache userDataCache, MainMenuService mainMenuService,
                          @Lazy LetovPoemBot letovPoemBot, ReplyMessagesService messagesService, PoemDataService poemDataService) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
        this.mainMenuService = mainMenuService;
        this.letovPoemBot = letovPoemBot;
        this.messagesService = messagesService;
        this.poemDataService = poemDataService;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyMessage = null;

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(),
                    callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            return processCallbackQuery(callbackQuery);
        }


        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }


    private SendMessage handleInputMessage(Message message) {

        String inputMsg = message.getText();
        int userId = message.getFrom().getId();
        long chatId = message.getChatId();
        BotState botState;
        SendMessage replyMessage;

        switch (inputMsg) {
            case "/start":
                botState = BotState.ASK_START;
                letovPoemBot.sendPhoto(chatId, messagesService.getReplyText("reply.hello"), "static/images/letovpoem_logo.jpg");
                break;
            case "Получить cлучайный стих":
                botState = BotState.SHOW_RANDOM_POEM;
                break;
           /* case "Добавить в избранное":
                botState = BotState.ADD_TO_FAVOURITE;
                *//*UserFavouriteData userFavouriteData = userDataCache.getUserFavouriteData(userId);
                userFavouriteData.setPoems("poem");
                userDataCache.saveUserFavouriteData(userId, userFavouriteData);*//*
                break;
            case "Избранное":
                //letovPoemBot.sendDocument(chatId, "Ваше избранное", getUsersProfile(userId));
                botState = BotState.SHOW_USER_FAVOURITE;
                break;*/
            case "Помощь":
                botState = BotState.SHOW_HELP_MENU;
                break;
            default:
                botState = userDataCache.getUsersCurrentBotState(userId);
                break;
        }

        userDataCache.setUsersCurrentBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }


    private BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final long chatId = buttonQuery.getMessage().getChatId();
        final int userId = buttonQuery.getFrom().getId();
        BotApiMethod<?> callBackAnswer = mainMenuService.getMainMenuMessage(chatId, "Воспользуйтесь главным меню");


        //From Destiny choose buttons
        if (buttonQuery.getData().equals("buttonYes")) {
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_RANDOM_POEM);
            callBackAnswer = mainMenuService.getMainMenuMessage(chatId, "Воспользуйтесь главным меню");
        } else if (buttonQuery.getData().equals("buttonNo")) {
            callBackAnswer = sendAnswerCallbackQuery("Возвращайтесь, когда будете готовы", true, buttonQuery);
        } else if (buttonQuery.getData().equals("buttonIwillThink")) {
            callBackAnswer = sendAnswerCallbackQuery("Возвращайтесь, когда будете готовы", true, buttonQuery);
        }

        //From Gender choose buttons
        else if (buttonQuery.getData().equals("buttonMan")) {
            UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
            userProfileData.setGender("М");
            userDataCache.saveUserProfileData(userId, userProfileData);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_COLOR);
            callBackAnswer = new SendMessage(chatId, "Твоя любимая цифра");
        } else if (buttonQuery.getData().equals("buttonWoman")) {
            UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
            userProfileData.setGender("Ж");
            userDataCache.saveUserProfileData(userId, userProfileData);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_COLOR);
            callBackAnswer = new SendMessage(chatId, "Твоя любимая цифра");

        }
         else {
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
        }


        return callBackAnswer;


    }


    private AnswerCallbackQuery sendAnswerCallbackQuery(String text, boolean alert, CallbackQuery callbackquery) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackquery.getId());
        answerCallbackQuery.setShowAlert(alert);
        answerCallbackQuery.setText(text);
        return answerCallbackQuery;
    }


    @SneakyThrows
    public File getUsersProfile(int userId) {
        UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
        File profileFile = ResourceUtils.getFile("classpath:static/docs/users_profile.txt");

        try (FileWriter fw = new FileWriter(profileFile.getAbsoluteFile());
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(userProfileData.toString());
        }


        return profileFile;

    }


}
