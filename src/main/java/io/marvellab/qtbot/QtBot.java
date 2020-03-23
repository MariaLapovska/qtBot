package io.marvellab.qtbot;

import io.marvellab.qtbot.command.HelpCommand;
import io.marvellab.qtbot.command.StartCommand;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class QtBot extends TelegramLongPollingCommandBot {
    private String botToken;
    private String botName;

    public QtBot(DefaultBotOptions botOptions, String botToken, String botName) {
        super(botOptions);

        this.botToken = botToken;
        this.botName = botName;

        register(new StartCommand());
        HelpCommand helpCommand = new HelpCommand(this);
        register(new HelpCommand(this));

        registerDefaultAction(((absSender, message) -> {
            SendMessage text = new SendMessage();
            text.setChatId(message.getChatId());
            text.setText(message.getText() + " command not found!");

            try {
                absSender.execute(text);
            } catch (TelegramApiException e) {
            }

            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[]{});
        }));
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        SendMessage answer = new SendMessage();

        answer.setText("Sorry, I don't know how to respond yet. Please, try later");
        answer.setChatId(msg.getChatId());

        try {
            execute(answer);
        } catch (TelegramApiException e) {
        }

//
//        LOG.info("Processing non-command update...");
//
//        if (!update.hasMessage()) {
//            LOG.error("Update doesn't have a body!");
//            throw new IllegalStateException("Update doesn't have a body!");
//        }
//
//        Message msg = update.getMessage();
//        User user = msg.getFrom();
//
//        LOG.info(LogTemplate.MESSAGE_PROCESSING.getTemplate(), user.getId());
//
//        if (!canSendMessage(user, msg)) {
//            return;
//        }
//
//        String clearMessage = msg.getText();
//        String messageForUsers = String.format("%s:\n%s", mAnonymouses.getDisplayedName(user), msg.getText());
//
//        SendMessage answer = new SendMessage();
//
//        // отправка ответа отправителю о том, что его сообщение получено
//        answer.setText(clearMessage);
//        answer.setChatId(msg.getChatId());
//        replyToUser(answer, user, clearMessage);
//
//        // отправка сообщения всем остальным пользователям бота
//        answer.setText(messageForUsers);
//        Stream<Anonymous> anonymouses = mAnonymouses.anonymouses();
//        anonymouses.filter(a -> !a.getUser().equals(user))
//                .forEach(a -> {
//                    answer.setChatId(a.getChat().getId());
//                    sendMessageToUser(answer, a.getUser(), user);
//                });
    }
}
