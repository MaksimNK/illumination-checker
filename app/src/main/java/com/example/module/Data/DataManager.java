package com.example.module.Data;


import com.example.module.Data.IlluminationData;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataManager {

    private static final String URL = "http://34.125.128.207/server/api/illuminations/getalltime";

    private static DataManager instance;

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public void fetchDataForDay(final DataCallback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://34.125.128.207/server/api/illuminations/getforday")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    List<IlluminationData> illuminationDataList = parseJson(responseData);
                    callback.onDataLoaded(illuminationDataList);
                } else {
                    callback.onFailure("Failed to load data from server");
                }
            }
        });
    }

    public void fetchDataForWeek(final DataCallback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://34.125.128.207/server/api/illuminations/getforweek")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    List<IlluminationData> illuminationDataList = parseJson(responseData);
                    callback.onDataLoaded(illuminationDataList);
                } else {
                    callback.onFailure("Failed to load data from server");
                }
            }
        });
    }

    public void fetchDataForMonth(final DataCallback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://34.125.128.207/server/api/illuminations/getformonth")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    List<IlluminationData> illuminationDataList = parseJson(responseData);
                    callback.onDataLoaded(illuminationDataList);
                } else {
                    callback.onFailure("Failed to load data from server");
                }
            }
        });
    }


    public void fetchDataAll(final DataCallback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://34.125.128.207/server/api/illuminations/getalltime")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    List<IlluminationData> illuminationDataList = parseJson(responseData);
                    callback.onDataLoaded(illuminationDataList);
                } else {
                    callback.onFailure("Failed to load data from server");
                }
            }
        });
    }


    private List<IlluminationData> parseJson(String json) {
        List<IlluminationData> dataList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int level = jsonObject.getInt("level");
                int illuminationClass = jsonObject.getInt("illumination_class");
                String createAt = jsonObject.getString("created_at");

                // Преобразование строки даты в объект даты
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                Date date = dateFormat.parse(createAt);

                // Форматирование даты в желаемый формат (только день месяца и время)
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.getDefault());
                String formattedDate = outputFormat.format(date);

                IlluminationData illuminationData = new IlluminationData(level, illuminationClass);
                illuminationData.setCreated_at(formattedDate); // Установка отформатированной даты
                dataList.add(illuminationData);
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        return dataList;
    }



    public interface DataCallback {
        void onDataLoaded(List<IlluminationData> dataList);

        void onFailure(String message);
    }

}
