package com.example.module;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

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


public class StarredFragment extends Fragment {


    private BarChart mBarChart;
    private StackedBarChart mStackedBarChart;
    private PieChart mPieChart;
    private ValueLineChart mLineChart;


    private String dataType;


    public StarredFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_starred, container, false);

        LinearLayout linearLayout = view.findViewById(R.id.linear_layout_container);
        Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
        linearLayout.startAnimation(slideDown);
        linearLayout.setVisibility(View.VISIBLE);




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
            DataManager.getInstance().fetchDataAll(new DataManager.DataCallback() {
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


    private void updateChartsWithData(List<IlluminationData> dataList, String dataType) {
        setDataToBarChart(dataList, dataType);
        setDataToStackedBarChart(dataList, dataType);
        setDataToLineChart(dataList, dataType);
        setDataToPieChart(dataList, dataType);
    }






}