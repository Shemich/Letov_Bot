package ru.shemich.letovpoem_bot.botapi.handlers.menu;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.shemich.letovpoem_bot.botapi.BotState;
import ru.shemich.letovpoem_bot.botapi.InputMessageHandler;
import ru.shemich.letovpoem_bot.service.MainMenuService;
import ru.shemich.letovpoem_bot.service.ReplyMessagesService;
import ru.shemich.letovpoem_bot.utils.Emojis;


@Component
public class HelpMenuHandler implements InputMessageHandler {
    private MainMenuService mainMenuService;
    private ReplyMessagesService messagesService;

    public HelpMenuHandler(MainMenuService mainMenuService, ReplyMessagesService messagesService) {
        this.mainMenuService = mainMenuService;
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        return mainMenuService.getMainMenuMessage(message.getChatId(),
                messagesService.getReplyText("reply.showHelpMenu", Emojis.EYE));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_HELP_MENU;
    }
}
