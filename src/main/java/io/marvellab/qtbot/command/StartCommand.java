package io.marvellab.qtbot.command;

import lombok.extern.java.Log;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Log
public class StartCommand extends QtBotCommand {

    public StartCommand() {
        super("start", "start using bot\n");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("User with username " + user.getUserName());

        StringBuilder sb = new StringBuilder();

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        sb.append("Hi, ").append(user.getUserName())
                .append("! Thanks for using this bot! It's still under construction, but will be ready soon\n");

        message.setText(sb.toString());
        execute(absSender, message, user);
    }
}
