package ch.inf.usi.mindbricks.ui.charts;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import ch.inf.usi.mindbricks.R;
import ch.inf.usi.mindbricks.model.WeeklyStats;

/**
 * Displays weekly focus trends showing average focus duration per weekday
 */
public class WeeklyFocusChartView extends LinearLayout {

    private LineChart chart;
    private List<WeeklyStats> weeklyStats;

    private static final int PRIMARY_COLOR = Color.rgb(33, 150, 243); // Blue

    public WeeklyFocusChartView(Context context) {
        super(context);
        init(context);
    }

    public WeeklyFocusChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.view_weekly_focus_chart, this, true);
        chart = findViewById(R.id.weeklyFocusChart);
        setupChart();
    }

    private void setupChart() {
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);
        chart.getLegend().setEnabled(false);

        // X-axis setup -> days of week
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(new ValueFormatter() {
            private final String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < days.length) {
                    return days[index];
                }
                return "";
            }
        });

        // Y-axis setup -> average duration in minutes
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%dm", (int) value);
            }
        });

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    public void setData(List<WeeklyStats> stats) {
        this.weeklyStats = stats;
        updateChart();
    }

    private void updateChart() {
        if (weeklyStats == null || weeklyStats.isEmpty()) {
            chart.clear();
            return;
        }

        List<Entry> entries = new ArrayList<>();

        for (WeeklyStats stat : weeklyStats) {
            entries.add(new Entry(stat.getDayOfWeek(), stat.getAvgFocusDuration()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Avg Focus Time");

        dataSet.setColor(PRIMARY_COLOR);
        dataSet.setCircleColor(PRIMARY_COLOR);
        dataSet.setLineWidth(2.5f);
        dataSet.setCircleRadius(5f);
        dataSet.setDrawCircleHole(false);
        dataSet.setValueTextSize(10f);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(PRIMARY_COLOR);
        dataSet.setFillAlpha(50);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%dm", (int) value);
            }
        });

        LineData data = new LineData(dataSet);

        chart.setData(data);
        chart.invalidate();
    }

    public void refresh() {
        updateChart();
    }
}