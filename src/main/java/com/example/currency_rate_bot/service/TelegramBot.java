package com.example.currency_rate_bot.service;

import com.example.currency_rate_bot.config.BotConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final CurrencyRateService currencyRateService;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        String answer = "";
        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (messageText){
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    try {
                        answer = currencyRateService.get(messageText);
                    } catch (NullPointerException e) {
                        sendMessage(chatId, "Не знайдено вказаної валюти." + "\n" +
                                "Введіть назву валюти, офіційний курс якої" + "\n" +
                                "ви хочете дізнатись відносно української гривні." + "\n" +
                                "Наприклад: Долар США");
                    }
                    sendMessage(chatId, answer);
            }
        }
    }

    private void startCommandReceived(Long chatId, String name) {
        String answer = "Привіт, " + name + ", радий тебе бачити!" + "\n" +
                "Введіть назву валюти, офіційний курс якої" + "\n" +
                "ви хочете дізнатись відносно української гривні." + "\n" +
                "Наприклад: Долар США";
        sendMessage(chatId, answer);
    }

    private void sendMessage(Long chatId, String textToSend){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
        // обробити помилку
        }
    }
}
