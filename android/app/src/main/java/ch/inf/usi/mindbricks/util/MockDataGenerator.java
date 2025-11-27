package ch.inf.usi.mindbricks.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ch.inf.usi.mindbricks.model.StudySession;

/**
 * Utility class for generating mock study session data for testing
 * In production, this would be replaced with actual database queries
 */
public class MockDataGenerator {

    private static final Random random = new Random();
    private static final String[] LOCATIONS = {"home", "library", "cafe", "outside"};
    private static final String[] NOTES = {
            "Great flow", "Too noisy", "Distracted", "Energy peak",
            "Felt tired", "Good session", "Hard to focus", "Mediocre",
            "Stable productivity", "Sun was annoying"
    };

    /**
     * Generates mock study sessions for testing
     * @param count Number of sessions to generate
     * @return List of mock StudySession objects
     */
    public static List<StudySession> generateMockSessions(int count) {
        List<StudySession> sessions = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        for (int i = 0; i < count; i++) {
            // Generate session in the past 30 days
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_YEAR, -(random.nextInt(30)));
            cal.set(Calendar.HOUR_OF_DAY, 8 + random.nextInt(14)); // 8 AM to 10 PM
            cal.set(Calendar.MINUTE, random.nextInt(60));

            Date startTime = cal.getTime();

            int duration = 20 + random.nextInt(41); // 20-60 minutes
            cal.add(Calendar.MINUTE, duration);
            Date endTime = cal.getTime();

            StudySession session = new StudySession(
                    i + 1,
                    startTime,
                    endTime,
                    duration,
                    20f + random.nextFloat() * 50f, // noise: 20-70 dB
                    random.nextFloat() * 15f, // noise variance
                    random.nextFloat() * 700f, // light: 0-700 lux
                    random.nextFloat() * 40f, // light variance
                    random.nextInt(5), // phone pickups: 0-4
                    LOCATIONS[random.nextInt(LOCATIONS.length)],
                    random.nextBoolean() || random.nextBoolean(), // 75% completed
                    1 + random.nextInt(10), // self-rated focus: 1-10
                    1 + random.nextInt(5), // difficulty: 1-5
                    1 + random.nextInt(5), // mood: 1-5
                    generateProductivityScore(cal.get(Calendar.HOUR_OF_DAY)), // productivity based on hour
                    random.nextInt(8), // distractions: 0-7
                    generateProductivityScore(cal.get(Calendar.HOUR_OF_DAY)), // AI score
                    NOTES[random.nextInt(NOTES.length)]
            );

            sessions.add(session);
        }

        // Sort by date descending (newest first)
        sessions.sort((a, b) -> b.getStartTime().compareTo(a.getStartTime()));

        return sessions;
    }

    /**
     * Generates realistic productivity scores based on time of day
     * Higher productivity during typical work hours
     */
    private static int generateProductivityScore(int hour) {
        int baseScore;

        if (hour >= 9 && hour <= 11) {
            baseScore = 70 + random.nextInt(25); // Morning peak: 70-95
        } else if (hour >= 14 && hour <= 17) {
            baseScore = 65 + random.nextInt(25); // Afternoon: 65-90
        } else if (hour >= 19 && hour <= 21) {
            baseScore = 60 + random.nextInt(30); // Evening: 60-90
        } else if (hour >= 6 && hour <= 8) {
            baseScore = 50 + random.nextInt(30); // Early morning: 50-80
        } else {
            baseScore = 20 + random.nextInt(40); // Night/very early: 20-60
        }

        return Math.min(100, Math.max(0, baseScore));
    }

    /**
     * Generates a realistic daily recommendation based on persona data
     * Uses patterns from the provided CSV data
     */
    public static int[] generateRealisticDailyRecommendation() {
        int[] hourly = new int[24];

        // Low productivity during sleep/early hours
        hourly[0] = 35 + random.nextInt(10);
        hourly[1] = 30 + random.nextInt(10);
        hourly[2] = 30 + random.nextInt(10);
        hourly[3] = 35 + random.nextInt(10);
        hourly[4] = 30 + random.nextInt(10);
        hourly[5] = 35 + random.nextInt(10);

        // Morning rise (6-8 AM)
        hourly[6] = 50 + random.nextInt(15);
        hourly[7] = 55 + random.nextInt(15);

        // School hours blocked or low (8-13)
        hourly[8] = 5 + random.nextInt(10);
        hourly[9] = 5 + random.nextInt(10);
        hourly[10] = 5 + random.nextInt(10);
        hourly[11] = 5 + random.nextInt(10);
        hourly[12] = 5 + random.nextInt(10);

        // Post-school recovery (13-14)
        hourly[13] = 45 + random.nextInt(15);

        // Peak study hours (14-17)
        hourly[14] = 70 + random.nextInt(20);
        hourly[15] = 70 + random.nextInt(20);
        hourly[16] = 75 + random.nextInt(20);
        hourly[17] = 70 + random.nextInt(15);

        // Gym/dinner blocked (18-19)
        hourly[18] = 0;
        hourly[19] = 25 + random.nextInt(15);

        // Evening study possibility (20-22)
        hourly[20] = 50 + random.nextInt(15);
        hourly[21] = 50 + random.nextInt(15);
        hourly[22] = 50 + random.nextInt(15);

        // Late night decline
        hourly[23] = 35 + random.nextInt(10);

        return hourly;
    }
}