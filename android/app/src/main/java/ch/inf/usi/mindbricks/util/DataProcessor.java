package ch.inf.usi.mindbricks.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.inf.usi.mindbricks.model.DailyRecommendation;
import ch.inf.usi.mindbricks.model.StudySession;
import ch.inf.usi.mindbricks.model.TimeSlotStats;
import ch.inf.usi.mindbricks.model.WeeklyStats;

/**
 * Utility class for processing study session data into visualization-ready formats
 */
public class DataProcessor {

    /**
     * Generates daily recommendation based on historical session data
     * @param sessions List of past study sessions
     * @return DailyRecommendation with productivity scores for each hour
     */
    public static DailyRecommendation generateDailyRecommendation(List<StudySession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return new DailyRecommendation(new int[24]);
        }

        Map<Integer, List<Integer>> hourlyProductivity = new HashMap<>();

        for (StudySession session : sessions) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(session.getStartTime());
            int hour = cal.get(Calendar.HOUR_OF_DAY);

            if (!hourlyProductivity.containsKey(hour)) {
                hourlyProductivity.put(hour, new ArrayList<>());
            }
            hourlyProductivity.get(hour).add(session.getProductivityScore());
        }

        int[] hourlyAvg = new int[24];
        for (int i = 0; i < 24; i++) {
            if (hourlyProductivity.containsKey(i)) {
                List<Integer> scores = hourlyProductivity.get(i);
                int sum = 0;
                for (int score : scores) sum += score;
                hourlyAvg[i] = sum / scores.size();
            } else {
                hourlyAvg[i] = 50; // Default medium productivity
            }
        }

        return new DailyRecommendation(hourlyAvg);
    }

    /**
     * Calculates weekly statistics from session data
     * @param sessions List of past study sessions
     * @return List of WeeklyStats for each day of the week
     */
    public static List<WeeklyStats> calculateWeeklyStats(List<StudySession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return createEmptyWeeklyStats();
        }

        Map<Integer, List<StudySession>> dayMap = new HashMap<>();

        for (StudySession session : sessions) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(session.getStartTime());
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1; // 0=Sunday

            if (!dayMap.containsKey(dayOfWeek)) {
                dayMap.put(dayOfWeek, new ArrayList<>());
            }
            dayMap.get(dayOfWeek).add(session);
        }

        List<WeeklyStats> weeklyStats = new ArrayList<>();

        for (int day = 0; day < 7; day++) {
            if (dayMap.containsKey(day)) {
                List<StudySession> daySessions = dayMap.get(day);

                int totalDuration = 0;
                int totalProductivity = 0;

                for (StudySession session : daySessions) {
                    totalDuration += session.getDurationMinutes();
                    totalProductivity += session.getProductivityScore();
                }

                float avgDuration = (float) totalDuration / daySessions.size();
                float avgProductivity = (float) totalProductivity / daySessions.size();

                weeklyStats.add(new WeeklyStats(day, avgDuration, daySessions.size(), avgProductivity));
            } else {
                weeklyStats.add(new WeeklyStats(day, 0f, 0, 0f));
            }
        }

        return weeklyStats;
    }

    /**
     * Calculates hourly statistics from session data
     * @param sessions List of past study sessions
     * @return List of TimeSlotStats for each hour
     */
    public static List<TimeSlotStats> calculateHourlyStats(List<StudySession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return createEmptyHourlyStats();
        }

        Map<Integer, List<StudySession>> hourMap = new HashMap<>();

        for (StudySession session : sessions) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(session.getStartTime());
            int hour = cal.get(Calendar.HOUR_OF_DAY);

            if (!hourMap.containsKey(hour)) {
                hourMap.put(hour, new ArrayList<>());
            }
            hourMap.get(hour).add(session);
        }

        List<TimeSlotStats> hourlyStats = new ArrayList<>();

        for (int hour = 0; hour < 24; hour++) {
            if (hourMap.containsKey(hour)) {
                List<StudySession> hourSessions = hourMap.get(hour);

                int totalProductivity = 0;
                int totalMinutes = 0;

                for (StudySession session : hourSessions) {
                    totalProductivity += session.getProductivityScore();
                    totalMinutes += session.getDurationMinutes();
                }

                float avgProductivity = (float) totalProductivity / hourSessions.size();

                hourlyStats.add(new TimeSlotStats(hour, avgProductivity, hourSessions.size(), totalMinutes));
            } else {
                hourlyStats.add(new TimeSlotStats(hour, 0f, 0, 0));
            }
        }

        return hourlyStats;
    }

    /**
     * Filters sessions to only include recent ones (last N days)
     * @param sessions All sessions
     * @param days Number of days to include
     * @return Filtered list of recent sessions
     */
    public static List<StudySession> getRecentSessions(List<StudySession> sessions, int days) {
        if (sessions == null || sessions.isEmpty()) {
            return new ArrayList<>();
        }

        Calendar cutoff = Calendar.getInstance();
        cutoff.add(Calendar.DAY_OF_YEAR, -days);
        long cutoffTime = cutoff.getTimeInMillis();

        List<StudySession> recent = new ArrayList<>();
        for (StudySession session : sessions) {
            if (session.getStartTime().getTime() >= cutoffTime) {
                recent.add(session);
            }
        }

        return recent;
    }

    private static List<WeeklyStats> createEmptyWeeklyStats() {
        List<WeeklyStats> stats = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            stats.add(new WeeklyStats(i, 0f, 0, 0f));
        }
        return stats;
    }

    private static List<TimeSlotStats> createEmptyHourlyStats() {
        List<TimeSlotStats> stats = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            stats.add(new TimeSlotStats(i, 0f, 0, 0));
        }
        return stats;
    }
}