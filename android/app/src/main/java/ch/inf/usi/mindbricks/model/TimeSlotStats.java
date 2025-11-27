package ch.inf.usi.mindbricks.model;

/**
 * Represents productivity statistics for a specific time period
 */
public class TimeSlotStats {
    private final int hour;
    private final float avgProductivity;
    private final int sessionCount;
    private final int totalMinutes;

    public TimeSlotStats(int hour, float avgProductivity, int sessionCount, int totalMinutes) {
        this.hour = hour;
        this.avgProductivity = avgProductivity;
        this.sessionCount = sessionCount;
        this.totalMinutes = totalMinutes;
    }

    public int getHour() {
        return hour;
    }
    public float getAvgProductivity() {
        return avgProductivity;
    }
    public int getSessionCount() {
        return sessionCount;
    }
    public int getTotalMinutes() {
        return totalMinutes;
    }
}