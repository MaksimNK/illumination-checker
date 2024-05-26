package com.example.module.network;

import com.google.gson.Gson;

public class JsonParser {

    private Gson gson = new Gson();

    public <T> T parseJson(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }


}
