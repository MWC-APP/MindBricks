package ch.inf.usi.mindbricks.ui.charts;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import ch.inf.usi.mindbricks.R;
import ch.inf.usi.mindbricks.model.DailyRecommendation;

/**
 * Custom view that displays a 24-hour productivity timeline
 * Color-coded by productivity level
 */
public class DailyTimelineChartView extends LinearLayout {

    private BarChart chart;
    private DailyRecommendation recommendation;

    // Color scheme for productivity levels
    private static final int COLOR_LOW = Color.rgb(239, 83, 80);      // Red
    private static final int COLOR_MEDIUM = Color.rgb(255, 183, 77);  // Orange
    private static final int COLOR_HIGH = Color.rgb(102, 187, 106);   // Green

    public DailyTimelineChartView(Context context) {
        super(context);
        init(context);
    }

    public DailyTimelineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.view_daily_timeline_chart, this, true);
        chart = findViewById(R.id.dailyTimelineChart);
        setupChart();
    }

    private void setupChart() {
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(false);
        chart.setMaxVisibleValueCount(24);
        chart.setPinchZoom(false);
        chart.setScaleEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.getLegend().setEnabled(false);

        // X-axis setup -> hours
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(12);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int hour = (int) value;
                if (hour == 0) return "12AM";
                if (hour < 12) return hour + "AM";
                if (hour == 12) return "12PM";
                return (hour - 12) + "PM";
            }
        });

        // Y-axis setup (productivity)
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setEnabled(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    public void setData(DailyRecommendation recommendation) {
        this.recommendation = recommendation;
        updateChart();
    }

    private void updateChart() {
        if (recommendation == null) {
            chart.clear();
            return;
        }

        List<BarEntry> entries = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        for (int hour = 0; hour < 24; hour++) {
            int productivity = recommendation.getProductivityForHour(hour);
            entries.add(new BarEntry(hour, productivity));

            int category = recommendation.getCategoryForHour(hour);
            switch (category) {
                case 0: colors.add(COLOR_LOW); break;
                case 1: colors.add(COLOR_MEDIUM); break;
                case 2: colors.add(COLOR_HIGH); break;
            }
        }

        BarDataSet dataSet = new BarDataSet(entries, "Productivity");
        dataSet.setColors(colors);
        dataSet.setDrawValues(false);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f);

        chart.setData(data);
        chart.invalidate();
    }

    public void refresh() {
        updateChart();
    }
}