package com.example.module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.module.Data.DataManager;
import com.example.module.Data.IlluminationData;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.StackedBarModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.List;
import java.util.Random;

public class StarreddFragment extends Fragment {

    private BarChart mBarChart;
    private StackedBarChart mStackedBarChart;
    private PieChart mPieChart;
    private ValueLineChart mLineChart;

    private Button buttonStackedBarChart;
    private Button buttonPieChart;
    private Button buttonBarChart;
    private Button buttonLineChart;

    private Button mSelectedButton;


    private Button buttonDay;
    private Button buttonWeek;
    private Button buttonMonth;


    private boolean isClikedDay = false;
    private boolean isClikedWeek = false;

    private boolean isClikedMoth = false;

    private String dataType;


    public StarreddFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_starredd, container, false);


        LinearLayout linearLayout = view.findViewById(R.id.linear_layout_container);
        Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
        linearLayout.startAnimation(slideDown);
        linearLayout.setVisibility(View.VISIBLE);


        buttonDay = view.findViewById(R.id.button_day);
        buttonDay.setOnClickListener(v -> onDayButtonClick());

        buttonWeek = view.findViewById(R.id.button_week);
        buttonWeek.setOnClickListener(v -> onWeekButtonClick());

        buttonMonth = view.findViewById(R.id.button_month);
        buttonMonth.setOnClickListener(v -> onMonthButtonClick());


        mBarChart = view.findViewById(R.id.barchart);
        mStackedBarChart = view.findViewById(R.id.stackedbarchart);
        mPieChart = view.findViewById(R.id.piechart);
        mLineChart = view.findViewById(R.id.cubiclinechart);



        Button buttonLevel = view.findViewById(R.id.button_level);

        Button buttonIlluminationClass = view.findViewById(R.id.button_illumination_class);

        ImageView arrowLeft = view.findViewById(R.id.arrow_left);
        ImageView arrowRight = view.findViewById(R.id.arrow_right);



        buttonLevel.setOnClickListener(v -> {
            fetchDataFromServer("level");
            buttonLevel.setBackgroundResource(R.drawable.button_pressed_color);
            // Сбросим цвет кнопки Illumination Class
            buttonIlluminationClass.setBackgroundResource(R.drawable.button_normal_color);

            dataType = "level";
        });

        buttonIlluminationClass.setOnClickListener(v -> {
            fetchDataFromServer("illumination_class");

            buttonIlluminationClass.setBackgroundResource(R.drawable.button_pressed_color);

            buttonLevel.setBackgroundResource(R.drawable.button_normal_color);

            dataType = "illumination_class";

        });

        arrowLeft.setOnClickListener(v -> switchToPreviousChart());
        arrowRight.setOnClickListener(v -> switchToNextChart());


      // fetchDataFromServerForMonth();
        return view;
    }


    private void switchToNextChart() {
        if (mBarChart.getVisibility() == View.VISIBLE) {
            mBarChart.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
            mBarChart.setVisibility(View.GONE);
            mLineChart.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
            mLineChart.setVisibility(View.VISIBLE);
        } else if (mLineChart.getVisibility() == View.VISIBLE) {
            mLineChart.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
            mLineChart.setVisibility(View.GONE);
            mPieChart.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
            mPieChart.setVisibility(View.VISIBLE);
        } else if (mPieChart.getVisibility() == View.VISIBLE) {
            mPieChart.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
            mPieChart.setVisibility(View.GONE);
            mStackedBarChart.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
            mStackedBarChart.setVisibility(View.VISIBLE);
        } else if (mStackedBarChart.getVisibility() == View.VISIBLE) {
            mStackedBarChart.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
            mStackedBarChart.setVisibility(View.GONE);
            mBarChart.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
            mBarChart.setVisibility(View.VISIBLE);
        }
    }

    private void switchToPreviousChart() {
        if (mStackedBarChart.getVisibility() == View.VISIBLE) {
            mStackedBarChart.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
            mStackedBarChart.setVisibility(View.GONE);
            mPieChart.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
            mPieChart.setVisibility(View.VISIBLE);
        } else if (mPieChart.getVisibility() == View.VISIBLE) {
            mPieChart.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
            mPieChart.setVisibility(View.GONE);
            mLineChart.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
            mLineChart.setVisibility(View.VISIBLE);
        } else if (mLineChart.getVisibility() == View.VISIBLE) {
            mLineChart.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
            mLineChart.setVisibility(View.GONE);
            mBarChart.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
            mBarChart.setVisibility(View.VISIBLE);
        } else if (mBarChart.getVisibility() == View.VISIBLE) {
            mBarChart.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
            mBarChart.setVisibility(View.GONE);
            mStackedBarChart.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
            mStackedBarChart.setVisibility(View.VISIBLE);
        }
    }


    private void fetchDataFromServer(String dataType) {
        if(isClikedDay) {
            DataManager.getInstance().fetchDataForDay(new DataManager.DataCallback() {
                @Override
                public void onDataLoaded(List<IlluminationData> dataList) {
                    updateChartsWithData(dataList, dataType);

                }

                @Override
                public void onFailure(String message) {
                    // Обработка ошибки
                }
            });
        }
        if(isClikedMoth) {
            DataManager.getInstance().fetchDataForMonth(new DataManager.DataCallback() {
                @Override
                public void onDataLoaded(List<IlluminationData> dataList) {
                    updateChartsWithData(dataList, dataType);

                }

                @Override
                public void onFailure(String message) {
                    // Обработка ошибки
                }
            });
        }
        if(isClikedWeek) {
            DataManager.getInstance().fetchDataForWeek(new DataManager.DataCallback() {
                @Override
                public void onDataLoaded(List<IlluminationData> dataList) {
                    updateChartsWithData(dataList, dataType);

                }

                @Override
                public void onFailure(String message) {
                    // Обработка ошибки
                }
            });
        }

    }

    private void setDataToStackedBarChart(List<IlluminationData> dataList, String dataType) {
        getActivity().runOnUiThread(() -> {
            mStackedBarChart.clearChart();
            for (IlluminationData data : dataList) {
                StackedBarModel stackedBarModel = new StackedBarModel(String.valueOf(data.getCreatedAt()));
                if ("level".equals(dataType)) {
                    stackedBarModel.addBar(new BarModel(data.getLevel(), getRandomColor()));
                } else if ("illumination_class".equals(dataType)) {
                    stackedBarModel.addBar(new BarModel(data.getIlluminationClass(), getRandomColor()));
                }
                mStackedBarChart.addBar(stackedBarModel);
            }
            // Запускаем анимацию на главном потоке
            mStackedBarChart.startAnimation();


        });

    }

    private void setDataToBarChart(List<IlluminationData> dataList, String dataType) {
        getActivity().runOnUiThread(() -> {
            mBarChart.clearChart(); // Очищаем график перед добавлением новых данных
            for (IlluminationData data : dataList) {
                if ("level".equals(dataType)) {
                    mBarChart.addBar(new BarModel(data.getLevel(), getRandomColor()));
                } else if ("illumination_class".equals(dataType)) {
                    mBarChart.addBar(new BarModel(data.getIlluminationClass(), getRandomColor()));
                }
            }
            // Запускаем анимацию на главном потоке
            mBarChart.startAnimation();


        });
    }

    private void setDataToPieChart(List<IlluminationData> dataList, String dataType) {
        getActivity().runOnUiThread(() -> {
            mPieChart.clearChart(); // Очищаем график перед добавлением новых данных
            for (IlluminationData data : dataList) {
                if ("level".equals(dataType)) {
                    mPieChart.addPieSlice(new PieModel("level", data.getLevel(), getRandomColor()));
                } else if ("illumination_class".equals(dataType)) {
                    mPieChart.addPieSlice(new PieModel("illumination class", data.getIlluminationClass(), getRandomColor()));
                }
            }
            // Запускаем анимацию на главном потоке
            mPieChart.startAnimation();


        });
    }

    private void setDataToLineChart(List<IlluminationData> dataList, String dataType) {
        getActivity().runOnUiThread(() -> {
            mLineChart.clearChart(); // Очищаем график перед добавлением новых данных
            ValueLineSeries series = new ValueLineSeries();
            series.setColor(0xFFFFFFFF);
            for (IlluminationData data : dataList) {
                if ("level".equals(dataType)) {
                    series.addPoint(new ValueLinePoint(String.valueOf(data.getCreatedAt()), data.getLevel()));
                } else if ("illumination_class".equals(dataType)) {
                    series.addPoint(new ValueLinePoint(String.valueOf(data.getCreatedAt()), data.getIlluminationClass()));
                }
            }
            mLineChart.addSeries(series);
            // Запускаем анимацию на главном потоке
            mLineChart.startAnimation();

        });
    }


    private int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }







    private void fetchDataFromServerForDay() {
        DataManager.getInstance().fetchDataForDay(new DataManager.DataCallback() {
            @Override
            public void onDataLoaded(List<IlluminationData> dataList) {
                updateChartsWithData(dataList, "");
            }

            @Override
            public void onFailure(String message) {
                // Обработка ошибки
            }
        });
    }

    private void fetchDataFromServerForWeek() {
        DataManager.getInstance().fetchDataForWeek(new DataManager.DataCallback() {
            @Override
            public void onDataLoaded(List<IlluminationData> dataList) {
                updateChartsWithData(dataList, "");
            }

            @Override
            public void onFailure(String message) {
                // Обработка ошибки
            }
        });
    }

    private void fetchDataFromServerForMonth() {
        DataManager.getInstance().fetchDataForMonth(new DataManager.DataCallback() {
            @Override
            public void onDataLoaded(List<IlluminationData> dataList) {
                updateChartsWithData(dataList, "level");
            }

            @Override
            public void onFailure(String message) {
            }
        });
    }

    public void onDayButtonClick() {
        buttonDay.setBackgroundResource(R.drawable.button_pressed_color);
        buttonWeek.setBackgroundResource(R.drawable.button_normal_color);
        buttonMonth.setBackgroundResource(R.drawable.button_normal_color);

        isClikedDay = true;
        isClikedWeek = false;
        isClikedMoth = false;

        Log.d("Button_Clicked", "Day button clicked");

        fetchDataFromServer(dataType);
    }

    public void onWeekButtonClick() {
        buttonDay.setBackgroundResource(R.drawable.button_normal_color);
        buttonWeek.setBackgroundResource(R.drawable.button_pressed_color);
        buttonMonth.setBackgroundResource(R.drawable.button_normal_color);

        isClikedWeek = true;
        isClikedDay = false;
        isClikedMoth = false;

        Log.d("Button_Clicked", "Day button clicked");

        fetchDataFromServer(dataType);
    }

    public void onMonthButtonClick() {
        buttonDay.setBackgroundResource(R.drawable.button_normal_color);
        buttonWeek.setBackgroundResource(R.drawable.button_normal_color);
        buttonMonth.setBackgroundResource(R.drawable.button_pressed_color);

        isClikedMoth = true;
        isClikedDay = false;
        isClikedWeek = false;

        Log.d("Button_Clicked", "Day button clicked");

        fetchDataFromServer(dataType);
    }





    private void updateChartsWithData(List<IlluminationData> dataList, String dataType) {
        setDataToBarChart(dataList, dataType);
        setDataToStackedBarChart(dataList, dataType);
        setDataToLineChart(dataList, dataType);
        setDataToPieChart(dataList, dataType);
    }



}
