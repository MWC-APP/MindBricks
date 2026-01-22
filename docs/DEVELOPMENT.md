# MindBricks Development Guide

This guide provides detailed instructions for setting up and developing the MindBricks Android application.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Initial Setup](#initial-setup)
- [Android Studio Configuration](#android-studio-configuration)
- [Running the App](#running-the-app)
- [Development Workflow](#development-workflow)
- [Code Style & Conventions](#code-style--conventions)
- [Testing](#testing)
- [Debugging](#debugging)
- [Common Issues](#common-issues)

---

## Prerequisites

### Required Software

1. **Android Studio Hedgehog (2023.1.1) or later**
   - Download from: https://developer.android.com/studio
   - Recommended: Latest stable version

2. **Java Development Kit (JDK) 17**
   - Bundled with Android Studio, or
   - Download from: https://adoptium.net/

3. **Android SDK Components**
   - Android SDK Platform 36 (API 36)
   - Android SDK Build-Tools 36.0.0
   - Android Emulator
   - Android SDK Platform-Tools

## Recommended Hardware
- **RAM**: 8GB minimum, 16GB recommended
- **Disk Space**: 10GB free space
- **CPU**: Intel i5 or equivalent (for emulator performance)

---

## Initial Setup

### 1. Clone the Repository

```bash
# Clone via HTTPS
git clone https://github.com/yourusername/mindbricks.git

# Or clone via SSH
git clone git@github.com:yourusername/mindbricks.git

# Navigate to project
cd mindbricks
```

### 2. Project Structure

The project follows this structure:
```
mindbricks/
├── android/           (Android Studio project root)
│   ├── app/
│   ├── build.gradle.kts
│   └── settings.gradle.kts
├── docs/
├── screenshots/
└── README.md
```

**Important**: Open the `android` directory (not the root) in Android Studio.

---

## Android Studio Configuration

### 1. Import Project

1. Launch Android Studio
2. Select `File > Open`
3. Navigate to `mindbricks/android` directory
4. Click `OK`
5. Wait for Gradle sync to complete

### 2. SDK Configuration

1. Open SDK Manager: `Tools > SDK Manager`
2. **SDK Platforms** tab:
   - ✅ Android 15.0 (API 36) - Target SDK
   - ✅ Android 10.0 (API 29) - Min SDK
   - ✅ Android 14.0 (API 34) - Recommended for testing

3. **SDK Tools** tab:
   - ✅ Android SDK Build-Tools 36
   - ✅ Android Emulator
   - ✅ Android SDK Platform-Tools
   - ✅ Google Play Services
   - ✅ Intel x86 Emulator Accelerator (HAXM) - For Intel CPUs

4. Click `Apply` and wait for downloads

### 3. Gradle Configuration

The project uses Gradle Version Catalog for dependency management.

#### gradle/libs.versions.toml (auto-managed)
No manual configuration needed - dependencies are defined in version catalog.

#### local.properties (create if missing)
```properties
# Android SDK location
sdk.dir=/Users/YourName/Library/Android/sdk

# Optional: Release signing (for production builds)
# storeFile=path/to/keystore.jks
# storePassword=****
# keyAlias=****
# keyPassword=****
```

### 4. Build Variants

Configure build variants in the bottom-left panel:

- **debug**: Development build
  - Includes debug menu in settings
  - Detailed logging enabled
  - No obfuscation
  - Faster build times

- **release**: Production build
  - Optimized and obfuscated
  - Release signing configuration
  - Slower build, smaller APK

Switch between variants: `Build > Select Build Variant`

### 5. Run Configurations

1. Click `Run > Edit Configurations`
2. Verify "app" configuration exists
3. Recommended settings:
   - **Launch**: Default Activity (LauncherActivity)
   - **Deployment target**: USB Device or Emulator
   - **Install flags**: -t (force reinstall)

---

## Running the App

### Option 1: Physical Device (Recommended for Sensors)

#### Enable Developer Options
1. Go to device `Settings > About Phone`
2. Tap `Build Number` 7 times
3. Go back to `Settings > Developer Options`
4. Enable `USB Debugging`

#### Connect Device
1. Connect device via USB
2. Accept USB debugging prompt on device
3. Verify connection: `Tools > Device Manager`
4. Click ▶ Run button or press `Shift + F10`

### Option 2: Android Emulator

#### Create Emulator
1. Open AVD Manager: `Tools > Device Manager`
2. Click `Create Device`
3. Recommended configuration:
   - **Hardware**: Pixel 6 or Pixel 7
   - **System Image**: Android 14 (API 34) with Google APIs
   - **Performance**: 
     - RAM: 4GB+
     - Internal Storage: 4GB+
     - Graphics: Hardware (GLES 2.0)

4. **Configure Sensors**:
   - Advanced Settings > Sensors
   - Ensure accelerometer, light sensor enabled

5. Click `Finish`

#### Run on Emulator
1. Start emulator from Device Manager
2. Wait for boot completion
3. Click ▶ Run button
4. Select running emulator

**Note**: Some sensors (microphone, motion) may not work perfectly on emulator. Physical device recommended for full testing.

### Option 3: Command Line

```bash
cd android

# Build and install debug
./gradlew installDebug

# Launch app
adb shell am start -n ch.inf.usi.mindbricks/.LauncherActivity

# View logs
adb logcat -s MindBricks:* AndroidRuntime:E
```

---

## Development Workflow

### 1. Code Organization

Follow the existing package structure:

```
ch.inf.usi.mindbricks/
├── config/            # Configuration constants
├── database/          # Room DB and DAOs
├── drivers/           # Hardware abstraction
├── game/              # Game logic
├── model/             # Data models
├── repository/        # Data layer
├── service/           # Background services
├── ui/                # UI components
│   ├── charts/       # Custom views
│   ├── nav/          # Fragments
│   ├── onboarding/   # Onboarding flow
│   └── settings/     # Settings screens
└── util/              # Utilities
```

### 2. Adding a New Feature

#### Example: Add new analytics chart

1. **Create Model** (if needed)
   ```java
   // model/visual/MyChartData.java
   public class MyChartData {
       private String label;
       private float value;
       // getters, setters, constructors
   }
   ```

2. **Add Database Query** (if needed)
   ```java
   // database/StudySessionDao.java
   @Query("SELECT ... FROM study_sessions ...")
   LiveData<List<MyChartData>> getMyChartData();
   ```

3. **Update ViewModel**
   ```java
   // ui/nav/analytics/AnalyticsViewModel.java
   public LiveData<List<MyChartData>> getMyChartData() {
       return database.studySessionDao().getMyChartData();
   }
   ```

4. **Create Custom View**
   ```java
   // ui/charts/MyChartView.java
   public class MyChartView extends View {
       public void setData(List<MyChartData> data) {
           // Render chart
       }
   }
   ```

5. **Add to Fragment**
   ```java
   // ui/nav/analytics/AnalyticsFragment.java
   viewModel.getMyChartData().observe(getViewLifecycleOwner(), 
       data -> myChartView.setData(data));
   ```

6. **Add Layout**
   ```xml
   <!-- res/layout/component_my_chart.xml -->
   <ch.inf.usi.mindbricks.ui.charts.MyChartView
       android:id="@+id/my_chart"
       android:layout_width="match_parent"
       android:layout_height="300dp" />
   ```

### 3. Working with Resources

#### Strings
- Add to appropriate `strings_*.xml` file
- Never hardcode strings in Java/Kotlin code
- Use `getString(R.string.my_string)` or `@string/my_string`

```xml
<!-- res/values/strings_analytics.xml -->
<string name="my_chart_title">My Chart</string>
```

#### Colors
```xml
<!-- res/values/colors.xml -->
<color name="my_color">#FF5722</color>
```

#### Dimensions
```xml
<!-- res/values/dimens.xml -->
<dimen name="my_spacing">16dp</dimen>
```

### 4. Database Migrations

When changing database schema:

1. **Increment Version**
   ```java
   // database/AppDatabase.java
   @Database(entities = {...}, version = 2) // Increment
   ```

2. **Add Migration**
   ```java
   static final Migration MIGRATION_1_2 = new Migration(1, 2) {
       @Override
       public void migrate(@NonNull SupportSQLiteDatabase db) {
           db.execSQL("ALTER TABLE study_sessions ADD COLUMN new_field INTEGER");
       }
   };
   ```

3. **Register Migration**
   ```java
   Room.databaseBuilder(...)
       .addMigrations(MIGRATION_1_2)
       .build();
   ```

---

## Code Style & Conventions

### Naming Conventions

#### Files
- Activities: `*Activity.java` (e.g., `MainActivity.java`)
- Fragments: `*Fragment.java` (e.g., `HomeFragment.java`)
- ViewModels: `*ViewModel.java`
- Adapters: `*Adapter.java`
- Utilities: Descriptive names (e.g., `TagManager.java`)

#### Layouts
- Activities: `activity_*.xml`
- Fragments: `fragment_*.xml`
- Components: `component_*.xml`
- List items: `item_*.xml`
- Dialogs: `dialog_*.xml`

#### Java Code
```java
// Class names: PascalCase
public class MyClassName { }

// Methods: camelCase
public void myMethodName() { }

// Variables: camelCase
private String myVariableName;

// Constants: UPPER_SNAKE_CASE
private static final int MAX_RETRY_COUNT = 3;

// Private fields: start with lowercase
private final Context context;
```

### Documentation

#### Class JavaDoc
```java
/**
 * Brief description of the class.
 * <p>
 * Longer description if needed with implementation details.
 *
 * @author Your Name
 */
public class MyClass {
}
```

#### Method JavaDoc
```java
/**
 * Brief description of what this method does.
 *
 * @param paramName Description of parameter
 * @return Description of return value
 * @throws ExceptionType When this exception is thrown
 */
public ReturnType myMethod(ParamType paramName) throws ExceptionType {
}
```

### Code Formatting

Android Studio auto-format: `Ctrl+Alt+L` (Windows/Linux) or `Cmd+Opt+L` (Mac)

**Settings**: `File > Settings > Editor > Code Style > Java`
- Indentation: 4 spaces
- Continuation indent: 8 spaces
- Line length: 120 characters

---

## Testing

### Unit Tests

Located in `src/test/java/`

```java
// Example unit test
@RunWith(JUnit4.class)
public class MyUnitTest {
    @Test
    public void testSomething() {
        assertEquals(expected, actual);
    }
}
```

Run tests:
```bash
./gradlew test
```

### Instrumented Tests

Located in `src/androidTest/java/`

```java
// Example instrumented test
@RunWith(AndroidJUnit4.class)
public class MyInstrumentedTest {
    @Test
    public void testUI() {
        // UI testing code
    }
}
```

Run tests:
```bash
./gradlew connectedAndroidTest
```

---

## Debugging

### Logcat

Use Android Studio Logcat panel or command line:

```bash
# View all logs
adb logcat

# Filter by tag
adb logcat -s "MindBricks:*"

# Filter by priority (E=Error, W=Warn, I=Info, D=Debug)
adb logcat *:E
```

### Logging in Code

```java
import android.util.Log;

private static final String TAG = "MyClass";

// Error
Log.e(TAG, "Error message", exception);

// Warning
Log.w(TAG, "Warning message");

// Info
Log.i(TAG, "Info message");

// Debug
Log.d(TAG, "Debug message");

// Verbose
Log.v(TAG, "Verbose message");
```

### Debugging Sensors

```java
// Enable detailed sensor logging
private static final boolean DEBUG_SENSORS = true;

if (DEBUG_SENSORS) {
    Log.d(TAG, "Light: " + lightLevel + ", Noise: " + noiseLevel);
}
```

### Database Inspector

View database in real-time:
1. Run app in debug mode
2. `View > Tool Windows > App Inspection`
3. Select `Database Inspector` tab
4. Explore tables and run queries

### Layout Inspector

Debug UI issues:
1. Run app
2. `Tools > Layout Inspector`
3. Inspect view hierarchy
4. Check properties and constraints

---

## Common Issues

### Issue: Gradle Sync Failed

**Solution:**
```bash
# Clean project
./gradlew clean

# Delete Gradle cache
rm -rf ~/.gradle/caches/

# In Android Studio: File > Invalidate Caches > Invalidate and Restart
```

### Issue: App Crashes on Launch

**Check:**
1. Logcat for stack trace
2. Verify permissions in manifest
3. Check database migrations
4. Ensure all dependencies are synced

### Issue: Sensors Not Working

**Physical Device:**
- Grant all permissions
- Check if device has required sensors: `Settings > About Phone > Sensors`

**Emulator:**
- Use Extended Controls (⋮) to simulate sensors
- Some sensors (microphone, motion) may not work properly

### Issue: Build Takes Too Long

**Optimize:**
```properties
# gradle.properties
org.gradle.jvmargs=-Xmx4g -XX:MaxPermSize=2048m
org.gradle.parallel=true
org.gradle.caching=true
```

### Issue: OutOfMemoryError

**Increase heap size:**
```
File > Settings > Build, Execution, Deployment > Compiler
VM Options: -Xmx4g
```

### Issue: ADB Device Not Found

```bash
# Restart ADB
adb kill-server
adb start-server

# Check devices
adb devices

# If still not found, check USB cable and drivers
```

---

## Additional Resources

### Official Documentation
- [Android Developer Guides](https://developer.android.com/guide)
- [Room Database](https://developer.android.com/training/data-storage/room)
- [Sensors Overview](https://developer.android.com/guide/topics/sensors/sensors_overview)
- [Material Design 3](https://m3.material.io/)

### Useful Commands

```bash
# Clean build
./gradlew clean

# Build debug APK
./gradlew assembleDebug

# Install on device
./gradlew installDebug

# Uninstall
adb uninstall ch.inf.usi.mindbricks

# View APK info
aapt dump badging app/build/outputs/apk/debug/app-debug.apk

# List all tasks
./gradlew tasks

# Check dependencies
./gradlew app:dependencies
```

### Keyboard Shortcuts (Android Studio)

| Action | Windows/Linux | Mac |
|--------|--------------|-----|
| Run | Shift+F10 | ^R |
| Debug | Shift+F9 | ^D |
| Build | Ctrl+F9 | ⌘F9 |
| Find | Ctrl+F | ⌘F |
| Find in Files | Ctrl+Shift+F | ⌘⇧F |
| Go to Class | Ctrl+N | ⌘O |
| Go to File | Ctrl+Shift+N | ⌘⇧O |
| Format Code | Ctrl+Alt+L | ⌘⌥L |
| Optimize Imports | Ctrl+Alt+O | ^⌥O |
| Quick Fix | Alt+Enter | ⌥↵ |

---

## Getting Help

If you encounter issues:

1. **Check Logcat** for error messages
2. **Search existing issues** on GitHub
3. **Clean and rebuild** project
4. **Ask team members** via project communication channels
5. **Create new issue** on GitHub with:
   - Android Studio version
   - Device/Emulator details
   - Steps to reproduce
   - Logcat output
   - Screenshots if applicable

---

**Happy Coding! 🚀**
