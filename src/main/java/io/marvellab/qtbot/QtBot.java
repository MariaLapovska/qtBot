package io.marvellab.qtbot;

import io.marvellab.qtbot.command.DefaultAction;
import io.marvellab.qtbot.command.HelpCommand;
import io.marvellab.qtbot.command.MoodCommand;
import io.marvellab.qtbot.command.StartCommand;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Log4j2
public final class QtBot extends TelegramLongPollingCommandBot {

    public QtBot(DefaultBotOptions botOptions) {
        super(botOptions);

        register(new StartCommand());
        HelpCommand helpCommand = new HelpCommand(this);
        register(new HelpCommand(this));
        register(new MoodCommand());

        registerDefaultAction(new DefaultAction(helpCommand));
    }

    @Override
    public String getBotToken() {
        return System.getenv("bot.token");
    }

    @Override
    public String getBotUsername() {
        return System.getenv("bot.name");
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message message = update.getMessage();
        User user = message.getFrom();

        log.debug("Executing non-command update per user [name='{}'; id='{}'] request", user.getUserName(), user.getId());

        String answerText = String.format("Sorry, %s, I don't know how to respond yet. Please, try later", user.getFirstName());

        SendMessage answer = new SendMessage()
                .setChatId(message.getChatId())
                .setText(answerText);

        try {
            log.debug("Sending answer to user [name='{}'; id='{}']", user.getUserName(), user.getId());
            execute(answer);
        } catch (TelegramApiException e) {
            log.error("Failed to answer to user [name='{}'; id='{}']", user.getUserName(), user.getId(), e);
        }
    }
}
