package io.marvellab.qtbot.command;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public final class HelpCommand extends QtBotCommand {

    private final ICommandRegistry commandRegistry;

    public HelpCommand(ICommandRegistry commandRegistry) {
        super("help", "list all known commands\n");
        this.commandRegistry = commandRegistry;
    }

    @Override
    public SendMessage generateMessage(AbsSender absSender, User user, Chat chat, String[] strings) {
        StringBuilder helpMessageBuilder = new StringBuilder("<b>Available commands:</b>\n\n");

        commandRegistry.getRegisteredCommands()
                .forEach(cmd -> helpMessageBuilder.append(cmd.toString()).append("\n"));

        return new SendMessage()
                .setChatId(chat.getId().toString())
                .setText(helpMessageBuilder.toString())
                .enableHtml(true);
    }
}
