package ru.shemich.letovpoem_bot.cache;

import ru.shemich.letovpoem_bot.botapi.BotState;
import ru.shemich.letovpoem_bot.model.UserFavouriteData;
import ru.shemich.letovpoem_bot.model.UserProfileData;


public interface DataCache {
    void setUsersCurrentBotState(int userId, BotState botState);

    BotState getUsersCurrentBotState(int userId);

    UserProfileData getUserProfileData(int userId);

    UserFavouriteData getUserFavouriteData(int userId);

    void saveUserProfileData(int userId, UserProfileData userProfileData);

    void saveUserFavouriteData(int userId, UserFavouriteData userFavouriteData);
}
