package io.marvellab.qtbot.command;

import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.function.BiConsumer;

@Log4j2
public final class DefaultAction implements BiConsumer<AbsSender, Message> {

    private final HelpCommand helpCommand;

    public DefaultAction(HelpCommand helpCommand) {
        this.helpCommand = helpCommand;
    }

    @Override
    public void accept(AbsSender absSender, Message message) {
        User user = message.getFrom();

        log.debug("Executing default action for invalid command '{}' per user [name='{}'; id='{}'] request", message.getText(), user.getUserName(), user.getId());

        SendMessage text = new SendMessage()
                .setChatId(message.getChatId())
                .setText(message.getText() + " command not found!");

        try {
            log.debug("Sending message to user [name='{}'; id='{}']", user.getUserName(), user.getId());
            absSender.execute(text);
        } catch (TelegramApiException e) {
            log.error("Failed to send message to user [name='{}'; id='{}']", user.getUserName(), user.getId(), e);
        }

        helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[]{});
    }
}
