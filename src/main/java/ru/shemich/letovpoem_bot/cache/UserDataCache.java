package ru.shemich.letovpoem_bot.cache;

import org.springframework.stereotype.Component;
import ru.shemich.letovpoem_bot.botapi.BotState;
import ru.shemich.letovpoem_bot.model.UserFavouriteData;
import ru.shemich.letovpoem_bot.model.UserProfileData;

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory cache.
 * usersBotStates: user_id and user's bot state
 * usersProfileData: user_id  and user's profile data.
 */

@Component
public class UserDataCache implements DataCache {
    private Map<Integer, BotState> usersBotStates = new HashMap<>();
    private Map<Integer, UserProfileData> usersProfileData = new HashMap<>();
    private Map<Integer, UserFavouriteData> usersFavouriteData = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(int userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUsersCurrentBotState(int userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null) {
            botState = BotState.ASK_START;
        }

        return botState;
    }

    @Override
    public UserProfileData getUserProfileData(int userId) {
        UserProfileData userProfileData = usersProfileData.get(userId);
        if (userProfileData == null) {
            userProfileData = new UserProfileData();
        }
        return userProfileData;
    }

    @Override
    public UserFavouriteData getUserFavouriteData(int userId) {
        UserFavouriteData userFavouriteData = usersFavouriteData.get(userId);
        if (userFavouriteData == null) {
            userFavouriteData = new UserFavouriteData();
        }
        return userFavouriteData;
    }

    @Override
    public void saveUserProfileData(int userId, UserProfileData userProfileData) {
        usersProfileData.put(userId, userProfileData);
    }
    @Override
    public void saveUserFavouriteData(int userId, UserFavouriteData userFavouriteData) {
        usersFavouriteData.put(userId, userFavouriteData);
    }
}
