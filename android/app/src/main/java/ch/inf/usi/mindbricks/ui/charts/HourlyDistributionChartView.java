package ch.inf.usi.mindbricks.ui.charts;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import ch.inf.usi.mindbricks.R;
import ch.inf.usi.mindbricks.model.TimeSlotStats;

/**
 * Displays hourly productivity distribution in a radar/spider chart
 * Shows when user is most and least productive throughout the day
 */
public class HourlyDistributionChartView extends LinearLayout {

    private RadarChart chart;
    private List<TimeSlotStats> hourlyStats;

    private static final int PRIMARY_COLOR = Color.rgb(156, 39, 176); // Purple

    public HourlyDistributionChartView(Context context) {
        super(context);
        init(context);
    }

    public HourlyDistributionChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.view_hourly_distribution_chart, this, true);
        chart = findViewById(R.id.hourlyDistributionChart);
        setupChart();
    }

    private void setupChart() {
        chart.getDescription().setEnabled(false);
        chart.setWebLineWidth(1.5f);
        chart.setWebColor(Color.LTGRAY);
        chart.setWebLineWidthInner(0.75f);
        chart.setWebColorInner(Color.LTGRAY);
        chart.setWebAlpha(100);
        chart.getLegend().setEnabled(false);
        chart.setRotationEnabled(false);

        // X-axis setup -> hours
        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(10f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int hour = (int) value;
                if (hour == 0) return "12AM";
                if (hour < 12) return hour + "";
                if (hour == 12) return "12PM";
                return (hour - 12) + "";
            }
        });

        // Y-axis setup
        YAxis yAxis = chart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(100f);
        yAxis.setDrawLabels(false);
    }

    public void setData(List<TimeSlotStats> stats) {
        this.hourlyStats = stats;
        updateChart();
    }

    private void updateChart() {
        if (hourlyStats == null || hourlyStats.isEmpty()) {
            chart.clear();
            return;
        }

        List<RadarEntry> entries = new ArrayList<>();

        // Group hours into 6 time blocks for better visualization
        int[] timeBlocks = {0, 4, 8, 12, 16, 20}; // 12AM, 4AM, 8AM, 12PM, 4PM, 8PM

        for (int blockStart : timeBlocks) {
            float avgProductivity = 0f;
            int count = 0;

            for (TimeSlotStats stat : hourlyStats) {
                if (stat.getHour() >= blockStart && stat.getHour() < blockStart + 4) {
                    avgProductivity += stat.getAvgProductivity();
                    count++;
                }
            }

            if (count > 0) {
                entries.add(new RadarEntry(avgProductivity / count));
            } else {
                entries.add(new RadarEntry(0f));
            }
        }

        RadarDataSet dataSet = new RadarDataSet(entries, "Productivity");
        dataSet.setColor(PRIMARY_COLOR);
        dataSet.setFillColor(PRIMARY_COLOR);
        dataSet.setDrawFilled(true);
        dataSet.setFillAlpha(100);
        dataSet.setLineWidth(2f);
        dataSet.setDrawHighlightCircleEnabled(true);
        dataSet.setDrawHighlightIndicators(false);

        RadarData data = new RadarData(dataSet);
        data.setValueTextSize(10f);
        data.setDrawValues(false);

        chart.setData(data);
        chart.invalidate();
    }

    public void refresh() {
        updateChart();
    }
}