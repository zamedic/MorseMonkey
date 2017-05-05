package org.telegram.telegrambots.generics;

import org.telegram.telegrambots.bots.DefaultBotOptions;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief TODO
 * @date 29 of October of 2016
 */
public interface BotSession {
    void setOptions(DefaultBotOptions options);
    void setToken(String token);
    void setCallback(LongPollingBot callback);

    /**
     * Starts the bot
     */
    void start();

    /**
     * Stops the bot
     */
    void stop();

    /**
     * Check if the bot is running
     * @return True if the bot is running, false otherwise
     */
    boolean isRunning();

    /**
     * @deprecated Use @link{{@link #stop()}} instead
     */
    default void close() {
        this.stop();
    }
}
