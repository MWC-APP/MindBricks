package ch.inf.usi.mindbricks.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ch.inf.usi.mindbricks.model.SessionSensorLog;
import ch.inf.usi.mindbricks.model.StudySession;

/**
 * Room database for MindBricks app
 */
@Database(entities = {StudySession.class, SessionSensorLog.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "mindbricks_database"
                    )
                    .fallbackToDestructiveMigrationOnDowngrade(false)
                    .build();
        }
        return INSTANCE;
    }

    public abstract StudySessionDao studySessionDao();

    public abstract SessionSensorLogDao sessionSensorLogDao();
}