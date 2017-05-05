package org.telegram.telegrambots.bots;


import javax.annotation.PostConstruct;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.bots.commands.CommandRegistry;
import org.telegram.telegrambots.bots.commands.ICommandRegistry;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * This class adds command functionality to the TelegramLongPollingBot
 *
 * @author Timo Schulz (Mit0x2)
 */
public abstract class TelegramLongPollingCommandBot extends TelegramLongPollingBot implements ICommandRegistry {
    private CommandRegistry commandRegistry;


    private void setupCommandRegistry(){
        this.commandRegistry = new CommandRegistry(true, getBotUsername());
    }



    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.isCommand() && !filter(message)) {
                if (commandRegistry.executeCommand(this, message)) {
                    return;
                }
            }
        }
        processNonCommandUpdate(update);
    }

    /**
     * Override this function in your bot implementation to filter messages with commands
     *
     * For example, if you want to prevent commands execution incoming from group chat:
     *   #
     *   # return !message.getChat().isGroupChat();
     *   #
     *
     * @note Default implementation doesn't filter anything
     * @param message Received message
     * @return true if the message must be ignored by the command bot and treated as a non command message,
     * false otherwise
     */
    protected boolean filter(Message message) {
        return false;
    }

    @Override
    public boolean register(BotCommand botCommand) {
        if(commandRegistry == null){
            setupCommandRegistry();
        }
        return commandRegistry.register(botCommand);
    }

    @Override
    public Map<BotCommand, Boolean> registerAll(BotCommand... botCommands) {
        return commandRegistry.registerAll(botCommands);
    }

    @Override
    public boolean deregister(BotCommand botCommand) {
        return commandRegistry.deregister(botCommand);
    }

    @Override
    public Map<BotCommand, Boolean> deregisterAll(BotCommand... botCommands) {
        return commandRegistry.deregisterAll(botCommands);
    }

    @Override
    public Collection<BotCommand> getRegisteredCommands() {
        return commandRegistry.getRegisteredCommands();
    }

    @Override
    public void registerDefaultAction(BiConsumer<AbsSender, Message> defaultConsumer) {
        commandRegistry.registerDefaultAction(defaultConsumer);
    }

    @Override
    public BotCommand getRegisteredCommand(String commandIdentifier) {
        return commandRegistry.getRegisteredCommand(commandIdentifier);
    }

    /**
     * Process all updates, that are not commands.
     * @warning Commands that have valid syntax but are not registered on this bot,
     * won't be forwarded to this method <b>if a default action is present</b>.
     *
     * @param update the update
     */
    public abstract void processNonCommandUpdate(Update update);
}
