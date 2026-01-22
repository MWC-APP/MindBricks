# MindBricks - Quick Reference Card

## 🚀 Quick Start Commands

```bash
# Clone project
git clone https://github.com/yourusername/mindbricks.git
cd mindbricks/android

# Build & Run
./gradlew assembleDebug      # Build debug APK
./gradlew installDebug       # Install on device
./gradlew clean              # Clean build artifacts

# Testing
./gradlew test               # Run unit tests
./gradlew connectedAndroidTest  # Run instrumented tests
```

## 📦 Project Quick Facts

| Property | Value |
|----------|-------|
| **Package** | `ch.inf.usi.mindbricks` |
| **Language** | Java 17 |
| **Min SDK** | 29 (Android 10) |
| **Target SDK** | 36 (Android 15) |
| **Database** | Room (SQLite) |
| **Architecture** | MVVM + Clean Architecture |

## 🗂️ Key Directories

```
android/app/src/main/
├── java/.../mindbricks/
│   ├── database/         # Room DB, DAOs
│   ├── ui/
│   │   ├── nav/          # Main fragments
│   │   │   ├── home/     # Timer screen
│   │   │   ├── analytics/# Charts & stats
│   │   │   └── shop/     # City game
│   │   ├── charts/       # Custom views
│   │   └── settings/     # Settings screens
│   ├── model/            # Data classes
│   ├── repository/       # Data layer
│   ├── service/          # Foreground service
│   └── util/             # Helpers
└── res/
    ├── layout/           # XML layouts
    ├── values/           # Strings, colors
    └── drawable/         # Graphics
```

## 🎨 Resource Naming

| Type | Prefix | Example |
|------|--------|---------|
| Activity | `activity_` | `activity_main.xml` |
| Fragment | `fragment_` | `fragment_home.xml` |
| Component | `component_` | `component_chart.xml` |
| List Item | `item_` | `item_session.xml` |
| Dialog | `dialog_` | `dialog_add_tag.xml` |

## 🗄️ Database Tables

1. **study_sessions** - Study session records
2. **tags** - Subject/topic tags
3. **session_questionnaires** - Productivity ratings
4. **pam_scores** - Emotional assessments
5. **session_sensor_logs** - Sensor data logs
6. **calendar_events** - Cached calendar events

## 🔑 Key Classes

### Activities
- `LauncherActivity` - Splash & routing
- `MainActivity` - Main navigation host
- `OnboardingActivity` - First-time setup
- `SettingsActivity` - Settings hierarchy

### Fragments (Main Navigation)
- `HomeFragment` - Study timer
- `AnalyticsFragment` - Charts & insights
- `ShopFragment` - City building game

### ViewModels
- `HomeViewModel` - Timer state
- `AnalyticsViewModel` - Stats processing
- `ProfileViewModel` - User profile
- `TileGameViewModel` - Game state

### Services
- `SensorService` - Foreground sensor monitoring

### Database
- `AppDatabase` - Room database singleton
- `*Dao` - Data access objects

### Utilities
- `RecommendationEngine` - Schedule generation
- `FocusScoreCalculator` - Sensor analysis
- `PreferencesManager` - SharedPreferences
- `TagManager` - Tag operations
- `NotificationHelper` - Notifications

## 🎯 Main Features Checklist

- ✅ Pomodoro timer with customizable durations
- ✅ Session tagging and categorization
- ✅ Multi-sensor focus tracking (mic, light, motion, orientation)
- ✅ Post-session questionnaires (emotion + productivity)
- ✅ Comprehensive analytics dashboard (7 chart types)
- ✅ AI-powered daily schedule recommendations
- ✅ Calendar integration for conflict detection
- ✅ City building gamification with coin rewards
- ✅ Persistent tile-based world (40x40 grid)
- ✅ Detailed session history with stats
- ✅ Configurable study plans by weekday
- ✅ Profile management with avatar
- ✅ Material Design 3 theming

## 🔧 Common Tasks

### Add New String Resource
```xml
<!-- res/values/strings_*.xml -->
<string name="my_string">My Text</string>
```
```java
// Usage in code
getString(R.string.my_string)
```

### Add New Color
```xml
<!-- res/values/colors.xml -->
<color name="my_color">#FF5722</color>
```

### Create New Fragment
```java
public class MyFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, 
                           ViewGroup container,
                           Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }
}
```

### Query Database
```java
// In ViewModel
LiveData<List<StudySession>> sessions = 
    AppDatabase.getInstance(context)
        .studySessionDao()
        .getAllSessions();

// Observe in Fragment
viewModel.getSessions().observe(getViewLifecycleOwner(), sessions -> {
    // Update UI
});
```

### Show Toast
```java
Toast.makeText(context, "Message", Toast.LENGTH_SHORT).show();
```

### Log Message
```java
private static final String TAG = "MyClass";
Log.d(TAG, "Debug message");
Log.e(TAG, "Error message", exception);
```

## 🐛 Debugging Quick Checks

**App crashes on launch?**
1. Check Logcat for stack trace
2. Verify permissions in `AndroidManifest.xml`
3. Check database migrations
4. Clean and rebuild: `./gradlew clean build`

**Sensors not working?**
1. Run on physical device (emulator limited)
2. Grant all permissions in app settings
3. Check device sensor availability

**Gradle sync fails?**
```bash
./gradlew clean
rm -rf ~/.gradle/caches/
# Then: File > Invalidate Caches > Restart
```

**ADB device not found?**
```bash
adb kill-server && adb start-server
adb devices
```

## 📱 Testing Checklist

### Before Committing
- [ ] Code compiles without errors
- [ ] No lint warnings (critical)
- [ ] All strings externalized (no hardcoded text)
- [ ] JavaDoc added for new public methods
- [ ] Tested on physical device if sensor-related
- [ ] No debug logging in production code
- [ ] Resources follow naming conventions

### Feature Testing
- [ ] Happy path works
- [ ] Error cases handled
- [ ] Permissions handled gracefully
- [ ] UI responsive on different screen sizes
- [ ] Database transactions complete
- [ ] No memory leaks (use Profiler)

## 🔐 Required Permissions

| Permission | Usage |
|------------|-------|
| `RECORD_AUDIO` | Ambient noise monitoring |
| `ACTIVITY_RECOGNITION` | Motion detection |
| `CAMERA` | Profile pictures |
| `READ_MEDIA_IMAGES` | Gallery access |
| `READ_CALENDAR` | Calendar sync |
| `POST_NOTIFICATIONS` | Alerts |
| `VIBRATE` | Haptic feedback |
| `WAKE_LOCK` | Keep CPU awake |
| `INTERNET` | Avatar generation |

## 🎨 Theme Colors

```xml
Primary: #6200EE (Purple)
Secondary: #03DAC6 (Teal)
Background: #FFFFFF (White) / #121212 (Dark)
Surface: #FFFFFF (White) / #1E1E1E (Dark)
Error: #B00020 (Red)
```

## 📊 Analytics Charts

1. **Weekly Focus Chart** - Bar chart (last 7 days)
2. **Hourly Distribution** - Heatmap (24h × 7d)
3. **Quality Heatmap** - Calendar with quality colors
4. **Tag Usage Pie Chart** - Subject distribution
5. **Daily Goal Rings** - Apple Watch style rings
6. **Streak Calendar** - Study streak visualization
7. **Daily Timeline** - Hour-by-hour breakdown
8. **Session History** - List with expandable cards

## 🎮 Game Economy

- **Earn coins**: Complete study sessions (varies by duration)
- **Spend coins**: Purchase tiles in shop
- **Tile types**: Terrain, Buildings, Nature, Infrastructure
- **World size**: 40×40 grid
- **Inventory**: Unlimited storage per tile type

## 📄 Important Files

| File | Purpose |
|------|---------|
| `build.gradle.kts` | Build configuration |
| `AndroidManifest.xml` | App configuration |
| `AppDatabase.java` | Database setup |
| `MainActivity.java` | Navigation host |
| `PreferencesManager.java` | Settings storage |
| `SensorService.java` | Background monitoring |

## 🔗 Useful Links

- **Project Repository**: [GitHub](https://github.com/yourusername/mindbricks)
- **Android Docs**: https://developer.android.com
- **Room Database**: https://developer.android.com/training/data-storage/room
- **Material Design**: https://m3.material.io
- **MPAndroidChart**: https://github.com/PhilJay/MPAndroidChart

---

**Print this page for quick reference while developing! 📋**
