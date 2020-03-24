package io.marvellab.qtbot.command;

import io.marvellab.qtbot.message.Buttons;
import io.marvellab.qtbot.message.Messages;
import io.marvellab.qtbot.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Collections;

public final class MoodCommand extends QtBotCommand {

    public MoodCommand() {
        super("mood", "Set mood for today\n");
    }

    @Override
    public SendMessage generateMessage(AbsSender absSender, User user, Chat chat, String[] strings) {
        Buttons buttons = BotUtils.getUserLocale(user, Buttons.class);
        Messages messages = BotUtils.getUserLocale(user, Messages.class);

        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(buttons.happy());
        keyboardRow1.add(buttons.sad());

        return new SendMessage()
                .setChatId(chat.getId())
                .setText(messages.mood())
                .setReplyMarkup(new ReplyKeyboardMarkup()
                        .setKeyboard(Collections.singletonList(keyboardRow1))
                        .setResizeKeyboard(true)
                        .setOneTimeKeyboard(true));
    }
}
