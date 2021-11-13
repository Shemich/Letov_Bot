package ru.shemich.letovpoem_bot.appconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import ru.shemich.letovpoem_bot.LetovPoemBot;
import ru.shemich.letovpoem_bot.botapi.TelegramFacade;


@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String webHookPath;
    private String botUserName;
    @Value("${botToken}")
    private String botToken;
    @Autowired
    private Environment environment;
    private String herokuPath;



    @Bean
    public LetovPoemBot myWizardTelegramBot(TelegramFacade telegramFacade) {
        DefaultBotOptions options = ApiContext
                .getInstance(DefaultBotOptions.class);

        LetovPoemBot myWizardTelegramBot = new LetovPoemBot(options, telegramFacade);
        myWizardTelegramBot.setBotUserName(botUserName);
        myWizardTelegramBot.setBotToken(botToken);
        myWizardTelegramBot.setWebHookPath(webHookPath);
        System.out.println(environment.getProperty("botToken"));
        System.out.println(herokuPath);
        return myWizardTelegramBot;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
