package io.marvellab.qtbot;

import com.github.rodionmoiseev.c10n.C10N;
import com.github.rodionmoiseev.c10n.annotations.DefaultC10NAnnotations;
import io.marvellab.qtbot.server.BotServer;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Log4j2
public class BotInitializer {

    public static void main(String[] args) throws Exception {
        log.info("Starting application...");

        new BotServer().start();

        C10N.configure(new DefaultC10NAnnotations());

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        try {
            log.info("Initializing bot...");
            botsApi.registerBot(new QtBot(botOptions));
            log.info("Bot successfully initialized and registered");
        } catch (TelegramApiRequestException e) {
            log.error("Failed to initialize bot", e);
        }
    }
}
