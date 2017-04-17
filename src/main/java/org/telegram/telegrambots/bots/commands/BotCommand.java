package org.telegram.telegrambots.bots.commands;

import javax.annotation.PostConstruct;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * Representation of a command, which can be executed
 *
 * @author Timo Schulz (Mit0x2)
 */
public abstract class BotCommand {
    public static final String COMMAND_INIT_CHARACTER = "/";
    public static final String COMMAND_PARAMETER_SEPARATOR = " ";
    private static int COMMAND_MAX_LENGTH = 32;

    private String commandIdentifier;
    private String description;

    @PostConstruct
    public void setup() {
        this.commandIdentifier = getCommandIdentifier();

        if (commandIdentifier == null || commandIdentifier.isEmpty()) {
            throw new IllegalArgumentException("commandIdentifier for command cannot be null or empty");
        }

        if (commandIdentifier.startsWith(COMMAND_INIT_CHARACTER)) {
            commandIdentifier = commandIdentifier.substring(1);
        }

        if (commandIdentifier.length() + 1 > COMMAND_MAX_LENGTH) {
            throw new IllegalArgumentException("commandIdentifier cannot be longer than " + COMMAND_MAX_LENGTH + " (including " + COMMAND_INIT_CHARACTER + ")");
        }

        this.commandIdentifier = commandIdentifier.toLowerCase();
        this.description = description;
    }

    /**
     * Get the identifier of this command
     *
     * @return the identifier
     */
    public abstract String getCommandIdentifier();

    /**
     * Get the description of this command
     *
     * @return the description as String
     */
    public abstract String getDescription();

    @Override
    public String toString() {
        return "<b>" + COMMAND_INIT_CHARACTER + getCommandIdentifier() +
                "</b>\n" + getDescription();
    }

    /**
     * Execute the command
     *
     * @param absSender absSender to send messages over
     * @param user      the user who sent the command
     * @param chat      the chat, to be able to send replies
     * @param arguments passed arguments
     */
    public abstract void execute(AbsSender absSender, User user, Chat chat, String[] arguments);
}