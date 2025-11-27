package ch.inf.usi.mindbricks.model;

/**
 * Represents weekly aggregated statistics
 */
public class WeeklyStats {
    private final int dayOfWeek; // 0=Sunday, 1=Monday...
    private final float avgFocusDuration;
    private final int totalSessions;
    private final float avgProductivity;

    public WeeklyStats(int dayOfWeek, float avgFocusDuration, int totalSessions, float avgProductivity) {
        this.dayOfWeek = dayOfWeek;
        this.avgFocusDuration = avgFocusDuration;
        this.totalSessions = totalSessions;
        this.avgProductivity = avgProductivity;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }
    public float getAvgFocusDuration() {
        return avgFocusDuration;
    }
    public int getTotalSessions() {
        return totalSessions;
    }
    public float getAvgProductivity() {
        return avgProductivity;
    }
}
