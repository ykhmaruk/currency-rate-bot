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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if (messageText.equalsIgnoreCase("/start")) {
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
            } else {
                try {
                    answer = currencyRateService.get(messageText); // toLowerCase??
                } catch (NullPointerException e) {
                    sendMessage(chatId, """
                                Не знайдено вказаної валюти 😞.
                                Введіть назву валюти, офіційний курс якої
                                ви хочете дізнатись відносно української гривні.
                                Наприклад: Долар США
                            """);
                }
                sendMessage(chatId, answer);
            }
        }
    }

    private void startCommandReceived(Long chatId, String name) {
        String answer = """
                Привіт, %s, радий Вас бачити!
                Введіть назву валюти, офіційний курс якої Ви
                хочете дізнатись відносно української гривні.
                Наприклад: Долар США
                """.formatted(name);
        sendMessage(chatId, answer);
    }

    private void sendMessage(Long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(("Can`t send message with text: %s "
                    + ", to chat with ID $l").formatted(textToSend, chatId), e);
        }
    }
}
