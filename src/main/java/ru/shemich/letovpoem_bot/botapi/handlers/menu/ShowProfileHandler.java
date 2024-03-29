package ru.shemich.letovpoem_bot.botapi.handlers.menu;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.shemich.letovpoem_bot.botapi.BotState;
import ru.shemich.letovpoem_bot.botapi.InputMessageHandler;
import ru.shemich.letovpoem_bot.cache.UserDataCache;
import ru.shemich.letovpoem_bot.model.UserProfileData;
import ru.shemich.letovpoem_bot.service.UsersProfileDataService;

/**
 *
 */
@Component
public class ShowProfileHandler implements InputMessageHandler {
    private UserDataCache userDataCache;
    private UsersProfileDataService profileDataService;

    public ShowProfileHandler(UserDataCache userDataCache, UsersProfileDataService profileDataService) {
        this.userDataCache = userDataCache;
        this.profileDataService = profileDataService;
    }

    @Override
    public SendMessage handle(Message message) {
        SendMessage userReply;
        final int userId = message.getFrom().getId();
        final UserProfileData profileData = profileDataService.getUserProfileData(message.getChatId());

        userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
        if (profileData != null) {
            userReply = new SendMessage(message.getChatId(),
                    String.format("%s%n-------------------%n%s", "Ваши избранные стихотворения:", profileData.toString()));
        } else {
            userReply = new SendMessage(message.getChatId(), "Ваших избранных не сущетсвует !");
        }

        return userReply;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_USER_FAVOURITE;
    }
}
