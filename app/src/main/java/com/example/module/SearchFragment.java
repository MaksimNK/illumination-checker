package com.example.module;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.module.Data.DataManager;
import com.example.module.Data.IlluminationData;

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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFragment extends Fragment {

    private LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        linearLayout = view.findViewById(R.id.linearLayout);

        fetchDataAll(new DataManager.DataCallback() {
            @Override
            public void onDataLoaded(List<IlluminationData> dataList) {
                displayData(dataList);
            }

            @Override
            public void onFailure(String message) {
                // Обработка ошибки загрузки данных
            }
        });

        return view;
    }

    private void fetchDataAll(final DataManager.DataCallback callback) {
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

    private void displayData(List<IlluminationData> dataList) {
        getActivity().runOnUiThread(() -> {
            for (IlluminationData data : dataList) {
                View dataView = LayoutInflater.from(getContext()).inflate(R.layout.item_data, null);
                TextView levelTextView = dataView.findViewById(R.id.levelTextView);
                TextView createdAtTextView = dataView.findViewById(R.id.createdAtTextView);
                TextView illuminationClassTextView = dataView.findViewById(R.id.illuminationClassTextView);

                levelTextView.setText("Level: " + data.getLevel());
                createdAtTextView.setText("Created At: " + data.getCreatedAt());
                illuminationClassTextView.setText("Illumination Class: " + data.getIlluminationClass());

                linearLayout.addView(dataView);
            }
        });
    }
}
