package com.example.currency_rate_bot.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

@Component
public class HttpClient {
    private final CloseableHttpClient httpClient;
    private final Gson gson;

    public HttpClient() {
        this.httpClient = HttpClients.createDefault();
        this.gson = new GsonBuilder()
                .setLenient()
                .create();
    }

    public <T> List<T> get(String url, Class<T> clazz) {
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            Type listType = TypeToken.getParameterized(List.class, clazz).getType();
            return gson.fromJson(jsonResponse, listType);
        } catch (IOException e) {
            throw new RuntimeException("Can`t fetch info from URL: %s".formatted(url), e);
        }
    }
}
