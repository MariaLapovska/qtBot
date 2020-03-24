package io.marvellab.qtbot;

import io.marvellab.qtbot.command.DefaultAction;
import io.marvellab.qtbot.command.HelpCommand;
import io.marvellab.qtbot.command.MoodCommand;
import io.marvellab.qtbot.command.StartCommand;
import io.marvellab.qtbot.response.AnswerProcessor;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Log4j2
public final class QtBot extends TelegramLongPollingCommandBot {

    private AnswerProcessor answerProcessor;

    public QtBot(DefaultBotOptions botOptions) {
        super(botOptions);

        answerProcessor = new AnswerProcessor();

        register(new StartCommand());
        HelpCommand helpCommand = new HelpCommand(this);
        register(new HelpCommand(this));
        register(new MoodCommand());

        registerDefaultAction(new DefaultAction(helpCommand));
    }

    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }

    @Override
    public String getBotUsername() {
        return System.getenv("BOT_NAME");
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message message = update.getMessage();
        User user = message.getFrom();

        try {
            log.debug("Sending answer to user [name='{}'; id='{}']", user.getUserName(), user.getId());
            answerProcessor.processMessage(message, user, this);
        } catch (TelegramApiException e) {
            log.error("Failed to answer to user [name='{}'; id='{}']", user.getUserName(), user.getId(), e);
        }
    }
}
