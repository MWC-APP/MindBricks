# MindBricks Screenshots

This directory contains all application screenshots used in the README and documentation.

## 📸 Available Screenshots (13 total)

### ✅ Captured Screenshots

#### Home Screen (1)
- ✅ `home_timer.png` - Main timer interface with tag selection and session dots

#### Onboarding Flow (4)
- ✅ `onboarding_step_1_welcome.png` - Welcome introduction screen
- ✅ `onboarding_step_2_profile.png` - User profile creation with name and avatar
- ✅ `onboarding_step_3_sensors.png` - Sensor permissions explanation and testing
- ✅ `onboarding_step_4_notifications.png` - Notification permission request

#### Analytics - Overview Tab (2)
- ✅ `visualization_overview_daily_schedule.png` - AI-recommended daily schedule with activity blocks
- ✅ `visualization_overview_study_streak.png` - Study streak calendar with quality colors

#### Analytics - Insights Tab (4)
- ✅ `visualization_insights_weekly_focus.png` - Weekly focus bar chart (last 7 days)
- ✅ `visualization_insights_hourly_distribution.png` - Hourly distribution heatmap
- ✅ `visualization_insights_focus_score_heatmap.png` - Focus quality calendar heatmap
- ✅ `visualization_insights_tag_distribution.png` - Tag usage pie chart

#### Analytics - History Tab (1)
- ✅ `visualization_history.png` - Session history list with expandable cards

#### Shop - City Building Game (3)
- ✅ `shop_worldmap.png` - 40x40 city grid with placed tiles and buildings
- ✅ `shop_editing.png` - City editing mode with tile placement interface
- ✅ `shop_items.png` - Tile shop with categorized items and inventory

#### Post-Session Questionnaires (2)
- ✅ `questionnaire_mood.jpg` - PAM emotion assessment (Pleasure-Arousal-Dominance model)
- ✅ `questionnaire_perceived_productivity.jpg` - Multi-dimensional productivity questionnaire

#### Settings Screens (5)
- ✅ `settings_profile.png` - User profile with avatar and study objectives
- ✅ `settings_pomodoro.png` - Pomodoro timer configuration (short/long/focus)
- ✅ `settings_study_plan.png` - Weekly study plan editor
- ✅ `settings_calendar_sync.png` - Calendar integration settings
- ✅ `settings_debug_menu.png` - Debug tools (development builds only)

### ⏳ Missing Screenshots

**None! 🎉 100% Coverage Achieved!**

All major features and user flows are now documented with screenshots.

## 📊 Screenshot Coverage

| Feature | Screenshots | Coverage |
|---------|-------------|----------|
| **Home Timer** | 1 | ✅ Complete |
| **Onboarding Flow** | 4 | ✅ Complete |
| **Analytics Overview** | 2 | ✅ Complete |
| **Analytics Insights** | 4 | ✅ Complete |
| **Analytics History** | 1 | ✅ Complete |
| **Shop/Game** | 3 | ✅ Complete |
| **Questionnaires** | 2 | ✅ Complete |
| **Settings** | 5 | ✅ Complete |
| **TOTAL** | **22/22** | **🎉 100% Complete!** |

## 📱 Screenshot Details

### Resolution & Format
- **Resolution**: 1080x2400 pixels (18.5:9 aspect ratio)
- **Format**: PNG with transparency preserved
- **Device**: Modern Android device with Material Design 3

### Content Quality
- ✅ Real data (not empty states)
- ✅ Consistent theme across all screenshots
- ✅ No personal information visible
- ✅ High resolution and clear text
- ✅ Professional presentation

## 🎯 How These Are Used

### Main README.md
All screenshots are displayed in the **Screenshots** section with:
- Organized by feature area
- Side-by-side comparison where relevant
- Descriptive captions
- Centered alignment with 300px width

Example markdown:
```markdown
<p align="center">
  <img src="./screenshots/home_timer.png" alt="Home Timer" width="300"/>
</p>
```

### Documentation Pages
Screenshots can be referenced in:
- DEVELOPMENT.md - For feature explanations
- CONTRIBUTING.md - For contribution examples
- Project presentations and reports

## 📸 Capture Guidelines (For Missing Screenshots)

### Shop/City Building Game
**What to show:**
- City grid with placed tiles
- Tile shop with categories
- Inventory panel with tile counts
- Coin balance indicator
- Drag-and-drop interaction (if possible)

**Navigation:**
1. Open app → Navigate to Shop tab
2. Show main city view with some buildings placed
3. Capture shop panel with tile categories
4. Show inventory with purchased tiles

### Questionnaires
**What to show:**
- Emotion selection dialog (PAM model)
- Productivity questionnaire dialog
- Rating sliders/options
- Dialog with user selecting options

**Navigation:**
1. Complete a study session
2. Capture emotion selection dialog
3. Capture productivity questionnaire dialog
4. Show submission confirmation

### Onboarding (Optional)
**What to show:**
- Welcome screen
- Profile setup page
- Permission request screens
- Sensor testing page

**Navigation:**
1. Clear app data or fresh install
2. Capture each onboarding step
3. Show permission dialogs

## 🔧 Capture Methods

### Method 1: Android Studio Device Toolbar
```
1. Run app in Android Studio
2. Navigate to desired screen
3. Click camera icon in device toolbar
4. Screenshot saves to desktop
5. Move to this directory with proper name
```

### Method 2: ADB Command Line
```bash
# Capture screenshot
adb shell screencap -p /sdcard/screenshot.png

# Pull to computer
adb pull /sdcard/screenshot.png ./screenshots/shop.png

# Clean up device
adb shell rm /sdcard/screenshot.png
```

### Method 3: Device Built-in
```
1. Navigate to screen in app
2. Press device screenshot buttons (Power + Volume Down)
3. Transfer to computer via USB/cloud
4. Rename and move to this directory
```

## ✅ Screenshot Checklist

Before considering screenshots complete:
- [x] Home timer screen
- [x] All analytics tabs (overview, insights, history)
- [x] All settings screens
- [ ] Shop/city building game
- [ ] Post-session questionnaires
- [ ] Onboarding flow (optional)

## 📊 Statistics

- **Total Screenshots**: 22 (20 PNG + 2 JPG)
- **File Size**: ~1.9 MB total
- **Average Size**: ~86 KB per screenshot
- **Resolution**: Consistent across all (1080x2400)
- **Coverage**: 🎉 **100% COMPLETE**

---

**Status**: ✅ **Perfect Coverage Achieved!**  
All features and user flows are now fully documented with screenshots.

Last updated: January 22, 2026
