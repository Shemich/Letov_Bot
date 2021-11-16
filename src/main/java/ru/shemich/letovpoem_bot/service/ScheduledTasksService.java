package ru.shemich.letovpoem_bot.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import ru.shemich.letovpoem_bot.botapi.BotState;
import ru.shemich.letovpoem_bot.cache.UserDataCache;
import ru.shemich.letovpoem_bot.model.UserFavouriteData;
import ru.shemich.letovpoem_bot.model.UserProfileData;
import ru.shemich.letovpoem_bot.service.MainMenuService;
import ru.shemich.letovpoem_bot.service.PoemDataService;
import ru.shemich.letovpoem_bot.service.ReplyMessagesService;


@EnableScheduling
public class ScheduledTasksService {
    BotState botState;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "*/10 * * * * *")
    public void reportCurrentTime() {
        System.out.println("The time is now " + dateFormat.format(new Date()));
        botState  = BotState.SHOW_RANDOM_POEM;
    }
}