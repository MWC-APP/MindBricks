package ch.inf.usi.mindbricks.ui.analytics;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.inf.usi.mindbricks.R;
import ch.inf.usi.mindbricks.model.DailyRecommendation;
import ch.inf.usi.mindbricks.model.StudySession;
import ch.inf.usi.mindbricks.model.TimeSlotStats;
import ch.inf.usi.mindbricks.model.WeeklyStats;
import ch.inf.usi.mindbricks.ui.charts.DailyTimelineChartView;
import ch.inf.usi.mindbricks.ui.charts.HourlyDistributionChartView;
import ch.inf.usi.mindbricks.ui.charts.SessionHistoryAdapter;
import ch.inf.usi.mindbricks.ui.charts.WeeklyFocusChartView;
import ch.inf.usi.mindbricks.util.DataProcessor;
import ch.inf.usi.mindbricks.util.MockDataGenerator;

/**
 * Activity that displays comprehensive study analytics
 */
public class AnalyticsActivity extends AppCompatActivity {

    private DailyTimelineChartView dailyTimelineChart;
    private WeeklyFocusChartView weeklyFocusChart;
    private HourlyDistributionChartView hourlyDistributionChart;
    private RecyclerView sessionHistoryRecycler;
    private SessionHistoryAdapter sessionAdapter;
    private ProgressBar loadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        initViews();
        setupRecyclerView();
        loadData();
    }

    private void initViews() {
        dailyTimelineChart = findViewById(R.id.dailyTimelineChart);
        weeklyFocusChart = findViewById(R.id.weeklyFocusChart);
        hourlyDistributionChart = findViewById(R.id.hourlyDistributionChart);
        sessionHistoryRecycler = findViewById(R.id.sessionHistoryRecycler);
        loadingProgress = findViewById(R.id.loadingProgress);
    }

    private void setupRecyclerView() {
        sessionAdapter = new SessionHistoryAdapter(session -> {
            // Handle session click - could open detail view
            showSessionDetails(session);
        });

        sessionHistoryRecycler.setLayoutManager(new LinearLayoutManager(this));
        sessionHistoryRecycler.setAdapter(sessionAdapter);
    }

    private void loadData() {
        loadingProgress.setVisibility(View.VISIBLE);

        // In production, load from database/repository
        // For now, simulate async data loading
        new Thread(() -> {
            List<StudySession> allSessions = loadSessionsFromDatabase();
            List<StudySession> recentSessions = DataProcessor.getRecentSessions(allSessions, 30);

            DailyRecommendation dailyRec = DataProcessor.generateDailyRecommendation(recentSessions);
            List<WeeklyStats> weeklyStats = DataProcessor.calculateWeeklyStats(recentSessions);
            List<TimeSlotStats> hourlyStats = DataProcessor.calculateHourlyStats(recentSessions);

            runOnUiThread(() -> {
                updateCharts(dailyRec, weeklyStats, hourlyStats);
                sessionAdapter.setData(recentSessions);
                loadingProgress.setVisibility(View.GONE);
            });
        }).start();
    }

    private void updateCharts(DailyRecommendation dailyRec,
                              List<WeeklyStats> weeklyStats,
                              List<TimeSlotStats> hourlyStats) {
        dailyTimelineChart.setData(dailyRec);
        weeklyFocusChart.setData(weeklyStats);
        hourlyDistributionChart.setData(hourlyStats);
    }

    private List<StudySession> loadSessionsFromDatabase() {
        // TODO: Implement actual database loading
        // This would use Room DAO or repository pattern
        return MockDataGenerator.generateMockSessions(50);
    }

    private void showSessionDetails(StudySession session) {
        // TODO: Implement session detail dialog or activity
        // Could show: notes, full metrics, environmental factors, etc.
    }
}
