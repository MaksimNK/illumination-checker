package com.example.module;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private TextView idTextView;
    private TextView levelTextView;
    private TextView createdAtTextView;
    private TextView illuminationClassTextView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        idTextView = view.findViewById(R.id.idTextView);
        levelTextView = view.findViewById(R.id.levelTextView);
        createdAtTextView = view.findViewById(R.id.createdAtTextView);
        illuminationClassTextView = view.findViewById(R.id.illuminationClassTextView);

        fetchData();

        return view;
    }

    private void fetchData() {
        String url = "http://34.125.128.207/server/api/illuminations/getlast";

        Request request = new Request.Builder()
                .url(url)
                .build();

        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                try {
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    int id = jsonObject.getInt("id");
                    long level = jsonObject.getLong("level");
                    String createdAt = jsonObject.getString("created_at");
                    int illuminationClass = jsonObject.getInt("illumination_class");

                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            levelTextView.setText("Level: " + level);
                            createdAtTextView.setText("Created At: " + formatData(createdAt));
                            illuminationClassTextView.setText("Illumination Class: " + illuminationClass);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static String formatData(String createAt) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            Date date = dateFormat.parse(createAt);

            // Форматирование даты в желаемый формат (только день месяца и время)
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.getDefault());
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return ""; // Возвращаем пустую строку в случае ошибки парсинга даты
        }
    }

}
