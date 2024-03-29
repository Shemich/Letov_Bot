package ru.shemich.letovpoem_bot.botapi;

/**Возможные состояния бота
 */

public enum BotState {
    ASK_START,
    ASK_NAME,
    ASK_AGE,
    ASK_GENDER,
    ASK_COLOR,
    ASK_NUMBER,
    ASK_MOVIE,
    ASK_SONG,
    ADD_TO_FAVOURITE,
    FILLING_PROFILE,
    PROFILE_FILLED,
    SHOW_RANDOM_POEM,
    SHOW_USER_FAVOURITE,
    SHOW_USER_PROFILE,
    SHOW_MAIN_MENU,
    SHOW_HELP_MENU;
}
