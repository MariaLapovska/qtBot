package io.marvellab.qtbot.command;

import io.marvellab.qtbot.message.Messages;
import io.marvellab.qtbot.util.BotUtils;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public final class HelpCommand extends QtBotCommand {

    private final ICommandRegistry commandRegistry;

    public HelpCommand(ICommandRegistry commandRegistry) {
        super("help", "List all known commands\n");
        this.commandRegistry = commandRegistry;
    }

    @Override
    public SendMessage generateMessage(AbsSender absSender, User user, Chat chat, String[] strings) {
        Messages messages = BotUtils.getUserLocale(user, Messages.class);
        StringBuilder helpMessageBuilder = new StringBuilder(messages.availableCommands());

        commandRegistry.getRegisteredCommands()
                .forEach(cmd -> helpMessageBuilder.append(cmd.toString()).append("\n"));

        return new SendMessage()
                .setChatId(chat.getId().toString())
                .setText(helpMessageBuilder.toString())
                .enableHtml(true);
    }
}
