package io.marvellab.qtbot;

import lombok.extern.java.Log;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Log
public class BotInitializer {

    public static void main(String[] args) {
        try {
            String botToken = System.getenv("bot.token");
            String botName = System.getenv("bot.name");

            ApiContextInitializer.init();

            TelegramBotsApi botsApi = new TelegramBotsApi();

            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

            botsApi.registerBot(new QtBot(botOptions, botToken, botName));

            log.info("Bot successfully initialized and registered");
        } catch (TelegramApiRequestException e) {
        }
    }
}
