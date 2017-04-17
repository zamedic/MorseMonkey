package org.telegram.telegrambots.bots;

import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.ForwardMessage;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.GetMe;
import org.telegram.telegrambots.api.methods.GetUserProfilePhotos;
import org.telegram.telegrambots.api.methods.games.GetGameHighScores;
import org.telegram.telegrambots.api.methods.games.SetGameScore;
import org.telegram.telegrambots.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.api.methods.groupadministration.GetChatMemberCount;
import org.telegram.telegrambots.api.methods.groupadministration.KickChatMember;
import org.telegram.telegrambots.api.methods.groupadministration.LeaveChat;
import org.telegram.telegrambots.api.methods.groupadministration.UnbanChatMember;
import org.telegram.telegrambots.api.methods.send.SendAudio;
import org.telegram.telegrambots.api.methods.send.SendChatAction;
import org.telegram.telegrambots.api.methods.send.SendContact;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendGame;
import org.telegram.telegrambots.api.methods.send.SendLocation;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.methods.send.SendVenue;
import org.telegram.telegrambots.api.methods.send.SendVideo;
import org.telegram.telegrambots.api.methods.send.SendVoice;
import org.telegram.telegrambots.api.methods.updates.DeleteWebhook;
import org.telegram.telegrambots.api.methods.updates.GetWebhookInfo;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.ChatMember;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.UserProfilePhotos;
import org.telegram.telegrambots.api.objects.WebhookInfo;
import org.telegram.telegrambots.api.objects.games.GameHighScore;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.updateshandlers.SentCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 */
@SuppressWarnings("unused")
public abstract class AbsSender {
    protected AbsSender() {
    }

    // Send Requests

    public Message sendMessage(SendMessage sendMessage) throws TelegramApiException {
        if (sendMessage == null) {
            throw new TelegramApiException("Parameter sendMessage can not be null");
        }

        return sendApiMethod(sendMessage);
    }

    public Boolean answerInlineQuery(AnswerInlineQuery answerInlineQuery) throws TelegramApiException {
        if (answerInlineQuery == null) {
            throw new TelegramApiException("Parameter answerInlineQuery can not be null");
        }

        return sendApiMethod(answerInlineQuery);
    }

    public Boolean sendChatAction(SendChatAction sendChatAction) throws TelegramApiException {
        if (sendChatAction == null) {
            throw new TelegramApiException("Parameter sendChatAction can not be null");
        }

        return sendApiMethod(sendChatAction);
    }

    public Message forwardMessage(ForwardMessage forwardMessage) throws TelegramApiException {
        if (forwardMessage == null) {
            throw new TelegramApiException("Parameter forwardMessage can not be null");
        }

        return sendApiMethod(forwardMessage);
    }

    public Message sendLocation(SendLocation sendLocation) throws TelegramApiException {
        if (sendLocation == null) {
            throw new TelegramApiException("Parameter sendLocation can not be null");
        }

        return sendApiMethod(sendLocation);
    }

    public Message sendVenue(SendVenue sendVenue) throws TelegramApiException {
        if (sendVenue == null) {
            throw new TelegramApiException("Parameter sendVenue can not be null");
        }

        return sendApiMethod(sendVenue);
    }

    public Message sendContact(SendContact sendContact) throws TelegramApiException {
        if (sendContact == null) {
            throw new TelegramApiException("Parameter sendContact can not be null");
        }

        return sendApiMethod(sendContact);
    }

    public Boolean kickMember(KickChatMember kickChatMember) throws TelegramApiException {
        if (kickChatMember == null) {
            throw new TelegramApiException("Parameter kickChatMember can not be null");
        }
        return sendApiMethod(kickChatMember);
    }

    public Boolean unbanMember(UnbanChatMember unbanChatMember) throws TelegramApiException {
        if (unbanChatMember == null) {
            throw new TelegramApiException("Parameter unbanChatMember can not be null");
        }
        return sendApiMethod(unbanChatMember);
    }

    public Boolean leaveChat(LeaveChat leaveChat) throws TelegramApiException {
        if (leaveChat == null) {
            throw new TelegramApiException("Parameter leaveChat can not be null");
        }
        return sendApiMethod(leaveChat);
    }

    public Chat getChat(GetChat getChat) throws TelegramApiException {
        if (getChat == null) {
            throw new TelegramApiException("Parameter getChat can not be null");
        }
        return sendApiMethod(getChat);
    }

    public List<ChatMember> getChatAdministrators(GetChatAdministrators getChatAdministrators) throws TelegramApiException {
        if (getChatAdministrators == null) {
            throw new TelegramApiException("Parameter getChatAdministrators can not be null");
        }
        return sendApiMethod(getChatAdministrators);
    }

    public ChatMember getChatMember(GetChatMember getChatMember) throws TelegramApiException {
        if (getChatMember == null) {
            throw new TelegramApiException("Parameter getChatMember can not be null");
        }
        return sendApiMethod(getChatMember);
    }

    public Integer getChatMemberCount(GetChatMemberCount getChatMemberCount) throws TelegramApiException {
        if (getChatMemberCount == null) {
            throw new TelegramApiException("Parameter getChatMemberCount can not be null");
        }
        return sendApiMethod(getChatMemberCount);
    }

    public Serializable editMessageText(EditMessageText editMessageText) throws TelegramApiException {
        if (editMessageText == null) {
            throw new TelegramApiException("Parameter editMessageText can not be null");
        }
        return sendApiMethod(editMessageText);
    }

    public Serializable editMessageCaption(EditMessageCaption editMessageCaption) throws TelegramApiException {
        if (editMessageCaption == null) {
            throw new TelegramApiException("Parameter editMessageCaption can not be null");
        }
        return sendApiMethod(editMessageCaption);
    }

    public Serializable editMessageReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup) throws TelegramApiException {
        if (editMessageReplyMarkup == null) {
            throw new TelegramApiException("Parameter editMessageReplyMarkup can not be null");
        }
        return sendApiMethod(editMessageReplyMarkup);
    }

    public Boolean answerCallbackQuery(AnswerCallbackQuery answerCallbackQuery) throws TelegramApiException {
        if (answerCallbackQuery == null) {
            throw new TelegramApiException("Parameter answerCallbackQuery can not be null");
        }
        return sendApiMethod(answerCallbackQuery);
    }

    public UserProfilePhotos getUserProfilePhotos(GetUserProfilePhotos getUserProfilePhotos) throws TelegramApiException {
        if (getUserProfilePhotos == null) {
            throw new TelegramApiException("Parameter getUserProfilePhotos can not be null");
        }

        return sendApiMethod(getUserProfilePhotos);
    }

    public File getFile(GetFile getFile) throws TelegramApiException {
        if(getFile == null){
            throw new TelegramApiException("Parameter getFile can not be null");
        }
        else if(getFile.getFileId() == null){
            throw new TelegramApiException("Attribute file_id in parameter getFile can not be null");
        }
        return sendApiMethod(getFile);
    }

    public User getMe() throws TelegramApiException {
        GetMe getMe = new GetMe();

        return sendApiMethod(getMe);
    }

    public WebhookInfo getWebhookInfo() throws TelegramApiException {
        GetWebhookInfo getWebhookInfo = new GetWebhookInfo();
        return sendApiMethod(getWebhookInfo);
    }

    public Serializable setGameScore(SetGameScore setGameScore) throws TelegramApiException {
        if(setGameScore == null){
            throw new TelegramApiException("Parameter setGameScore can not be null");
        }
        return sendApiMethod(setGameScore);
    }

    public Serializable getGameHighScores(GetGameHighScores getGameHighScores) throws TelegramApiException {
        if(getGameHighScores == null){
            throw new TelegramApiException("Parameter getGameHighScores can not be null");
        }
        return sendApiMethod(getGameHighScores);
    }

    public Message sendGame(SendGame sendGame) throws TelegramApiException {
        if(sendGame == null){
            throw new TelegramApiException("Parameter sendGame can not be null");
        }
        return sendApiMethod(sendGame);
    }

    public Boolean deleteWebhook(DeleteWebhook deleteWebhook) throws TelegramApiException {
        if(deleteWebhook == null){
            throw new TelegramApiException("Parameter deleteWebhook can not be null");
        }
        return sendApiMethod(deleteWebhook);
    }

    // Send Requests Async

    public void sendMessageAsync(SendMessage sendMessage, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (sendMessage == null) {
            throw new TelegramApiException("Parameter sendMessage can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(sendMessage, sentCallback);
    }

    public void answerInlineQueryAsync(AnswerInlineQuery answerInlineQuery, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (answerInlineQuery == null) {
            throw new TelegramApiException("Parameter answerInlineQuery can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(answerInlineQuery, sentCallback);
    }

    public void sendChatActionAsync(SendChatAction sendChatAction, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (sendChatAction == null) {
            throw new TelegramApiException("Parameter sendChatAction can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(sendChatAction, sentCallback);
    }

    public void forwardMessageAsync(ForwardMessage forwardMessage, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (forwardMessage == null) {
            throw new TelegramApiException("Parameter forwardMessage can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(forwardMessage, sentCallback);
    }

    public void sendLocationAsync(SendLocation sendLocation, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (sendLocation == null) {
            throw new TelegramApiException("Parameter sendLocation can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(sendLocation, sentCallback);
    }

    public void sendVenueAsync(SendVenue sendVenue, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (sendVenue == null) {
            throw new TelegramApiException("Parameter sendVenue can not be null");
        }

        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(sendVenue, sentCallback);
    }

    public void sendContactAsync(SendContact sendContact, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (sendContact == null) {
            throw new TelegramApiException("Parameter sendContact can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(sendContact, sentCallback);
    }

    public void kickMemberAsync(KickChatMember kickChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (kickChatMember == null) {
            throw new TelegramApiException("Parameter kickChatMember can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(kickChatMember, sentCallback);
    }

    public void unbanMemberAsync(UnbanChatMember unbanChatMember, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (unbanChatMember == null) {
            throw new TelegramApiException("Parameter unbanChatMember can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(unbanChatMember, sentCallback);
    }

    public void leaveChatAsync(LeaveChat leaveChat, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (leaveChat == null) {
            throw new TelegramApiException("Parameter leaveChat can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(leaveChat, sentCallback);
    }

    public void getChatAsync(GetChat getChat, SentCallback<Chat> sentCallback) throws TelegramApiException {
        if (getChat == null) {
            throw new TelegramApiException("Parameter getChat can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(getChat, sentCallback);
    }

    public void getChatAdministratorsAsync(GetChatAdministrators getChatAdministrators, SentCallback<ArrayList<ChatMember>> sentCallback) throws TelegramApiException {
        if (getChatAdministrators == null) {
            throw new TelegramApiException("Parameter getChatAdministrators can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(getChatAdministrators, sentCallback);
    }

    public void getChatMemberAsync(GetChatMember getChatMember, SentCallback<ChatMember> sentCallback) throws TelegramApiException {
        if (getChatMember == null) {
            throw new TelegramApiException("Parameter getChatMember can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(getChatMember, sentCallback);
    }

    public void getChatMemberCountAsync(GetChatMemberCount getChatMemberCount, SentCallback<Integer> sentCallback) throws TelegramApiException {
        if (getChatMemberCount == null) {
            throw new TelegramApiException("Parameter getChatMemberCount can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(getChatMemberCount, sentCallback);
    }

    public void editMessageTextAsync(EditMessageText editMessageText, SentCallback<Serializable> sentCallback) throws TelegramApiException {
        if (editMessageText == null) {
            throw new TelegramApiException("Parameter editMessageText can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(editMessageText, sentCallback);
    }

    public void editMessageCaptionAsync(EditMessageCaption editMessageCaption, SentCallback<Serializable> sentCallback) throws TelegramApiException {
        if (editMessageCaption == null) {
            throw new TelegramApiException("Parameter editMessageCaption can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(editMessageCaption, sentCallback);
    }

    public void editMessageReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup, SentCallback<Serializable> sentCallback) throws TelegramApiException {
        if (editMessageReplyMarkup == null) {
            throw new TelegramApiException("Parameter editMessageReplyMarkup can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(editMessageReplyMarkup, sentCallback);
    }

    public void answerCallbackQueryAsync(AnswerCallbackQuery answerCallbackQuery, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (answerCallbackQuery == null) {
            throw new TelegramApiException("Parameter answerCallbackQuery can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(answerCallbackQuery, sentCallback);
    }

    public void getUserProfilePhotosAsync(GetUserProfilePhotos getUserProfilePhotos, SentCallback<UserProfilePhotos> sentCallback) throws TelegramApiException {
        if (getUserProfilePhotos == null) {
            throw new TelegramApiException("Parameter getUserProfilePhotos can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(getUserProfilePhotos, sentCallback);
    }

    public void getFileAsync(GetFile getFile, SentCallback<File> sentCallback) throws TelegramApiException {
        if (getFile == null) {
            throw new TelegramApiException("Parameter getFile can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        sendApiMethodAsync(getFile, sentCallback);
    }

    public void getMeAsync(SentCallback<User> sentCallback) throws TelegramApiException {
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        GetMe getMe = new GetMe();
        sendApiMethodAsync(getMe, sentCallback);
    }

    public void getWebhookInfoAsync(SentCallback<WebhookInfo> sentCallback) throws TelegramApiException {
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }

        GetWebhookInfo getWebhookInfo = new GetWebhookInfo();
        sendApiMethodAsync(getWebhookInfo, sentCallback);
    }

    public void setGameScoreAsync(SetGameScore setGameScore, SentCallback<Serializable> sentCallback) throws TelegramApiException {
        if (setGameScore == null) {
            throw new TelegramApiException("Parameter setGameScore can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(setGameScore, sentCallback);
    }

    public void getGameHighScoresAsync(GetGameHighScores getGameHighScores, SentCallback<ArrayList<GameHighScore>> sentCallback) throws TelegramApiException {
        if (getGameHighScores == null) {
            throw new TelegramApiException("Parameter getGameHighScores can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(getGameHighScores, sentCallback);
    }

    public void sendGameAsync(SendGame sendGame, SentCallback<Message> sentCallback) throws TelegramApiException {
        if (sendGame == null) {
            throw new TelegramApiException("Parameter sendGame can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(sendGame, sentCallback);
    }

    public void deleteWebhook(DeleteWebhook deleteWebhook, SentCallback<Boolean> sentCallback) throws TelegramApiException {
        if (deleteWebhook == null) {
            throw new TelegramApiException("Parameter deleteWebhook can not be null");
        }
        if (sentCallback == null) {
            throw new TelegramApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(deleteWebhook, sentCallback);
    }

    // Specific Send Requests
    public abstract Message sendDocument(SendDocument sendDocument) throws TelegramApiException;

    public abstract Message sendPhoto(SendPhoto sendPhoto) throws TelegramApiException;

    public abstract Message sendVideo(SendVideo sendVideo) throws TelegramApiException;

    public abstract Message sendSticker(SendSticker sendSticker) throws TelegramApiException;

    /**
     * Sends a file using Send Audio method (https://core.telegram.org/bots/api#sendaudio)
     * @param sendAudio Information to send
     * @return If success, the sent Message is returned
     * @throws TelegramApiException If there is any error sending the audio
     */
    public abstract Message sendAudio(SendAudio sendAudio) throws TelegramApiException;

    /**
     * Sends a voice note using Send Voice method (https://core.telegram.org/bots/api#sendvoice)
     * For this to work, your audio must be in an .ogg file encoded with OPUS
     * @param sendVoice Information to send
     * @return If success, the sent Message is returned
     * @throws TelegramApiException If there is any error sending the audio
     */
    public abstract Message sendVoice(SendVoice sendVoice) throws TelegramApiException;

    // Simplified methods

    protected abstract <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void sendApiMethodAsync(Method method, Callback callback);

    protected abstract <T extends Serializable, Method extends BotApiMethod<T>> T sendApiMethod(Method method) throws TelegramApiException;
}
