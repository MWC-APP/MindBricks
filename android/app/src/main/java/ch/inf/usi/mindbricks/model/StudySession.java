package ch.inf.usi.mindbricks.model;

import java.util.Date;

/**
 * Represents a single study session with all relevant metrics
 */
public class StudySession {
    private final long sessionId;
    private final Date startTime;
    private final Date endTime;
    private final int durationMinutes;
    private final float noiseAvgDb;
    private final float noiseVariance;
    private final float lightLuxAvg;
    private final float lightVariance;
    private final int phonePickups;
    private final String userLocation;
    private final boolean sessionCompleted;
    private final int selfRatedFocus;
    private final int perceivedDifficulty;
    private final int mood;
    private final int productivityScore;
    private final int distractionsDetected;
    private final int aiEstimatedProdScore;
    private final String notes;

    public StudySession(long sessionId, Date startTime, Date endTime, int durationMinutes,
                        float noiseAvgDb, float noiseVariance, float lightLuxAvg,
                        float lightVariance, int phonePickups, String userLocation,
                        boolean sessionCompleted, int selfRatedFocus, int perceivedDifficulty,
                        int mood, int productivityScore, int distractionsDetected,
                        int aiEstimatedProdScore, String notes) {

        this.sessionId = sessionId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationMinutes = durationMinutes;
        this.noiseAvgDb = noiseAvgDb;
        this.noiseVariance = noiseVariance;
        this.lightLuxAvg = lightLuxAvg;
        this.lightVariance = lightVariance;
        this.phonePickups = phonePickups;
        this.userLocation = userLocation;
        this.sessionCompleted = sessionCompleted;
        this.selfRatedFocus = selfRatedFocus;
        this.perceivedDifficulty = perceivedDifficulty;
        this.mood = mood;
        this.productivityScore = productivityScore;
        this.distractionsDetected = distractionsDetected;
        this.aiEstimatedProdScore = aiEstimatedProdScore;
        this.notes = notes;
    }

    // getters
    public long getSessionId() {
        return sessionId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public float getNoiseAvgDb() {
        return noiseAvgDb;
    }

    public float getNoiseVariance() {
        return noiseVariance;
    }
    public float getLightLuxAvg() {
        return lightLuxAvg;
    }
    public float getLightVariance() {
        return lightVariance;
    }
    public int getPhonePickups() {
        return phonePickups;
    }
    public String getUserLocation() {
        return userLocation;
    }
    public boolean isSessionCompleted() {
        return sessionCompleted;
    }
    public int getSelfRatedFocus() {
        return selfRatedFocus;
    }
    public int getPerceivedDifficulty() {
        return perceivedDifficulty;
    }
    public int getMood() {
        return mood;
    }
    public int getProductivityScore() {
        return productivityScore;
    }
    public int getDistractions() {
        return distractionsDetected;
    }
    public int getAiEstimatedProdScore() {
        return aiEstimatedProdScore;
    }
    public String getNotes() {
        return notes;
    }
}