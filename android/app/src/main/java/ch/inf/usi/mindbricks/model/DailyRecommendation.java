package ch.inf.usi.mindbricks.model;

/**
 * Represents hourly recommendation levels for the day
 */
public class DailyRecommendation {
    private final int[] hourlyProductivity; // 24 values, 0-100

    public DailyRecommendation(int[] hourlyProductivity) {
        if (hourlyProductivity.length != 24) {
            throw new IllegalArgumentException("Hourly productivity must have 24 values");
        }
        this.hourlyProductivity = hourlyProductivity;
    }

    public int getProductivityForHour(int hour) {
        if (hour < 0 || hour >= 24) return 0;
        return hourlyProductivity[hour];
    }

    public int[] getAllHourlyProductivity() {
        return hourlyProductivity.clone();
    }

    /**
     * Categorizes productivity level
     * @return 0=low, 1=medium, 2=high
     */
    public int getCategoryForHour(int hour) {
        int prod = getProductivityForHour(hour);
        if (prod < 40)
            return 0;
        if (prod < 70)
            return 1;

        return 2;
    }
}