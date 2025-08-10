# Birds - Kotlin Multiplatform Mobile App
Birds is a Kotlin Multiplatform Mobile (KMP) project using Compose Multiplatform that targets Android, Desktop (JVM), and iOS platforms. The app displays a gallery of bird images fetched from an API with category filtering.

Always reference these instructions first and fallback to search or bash commands only when you encounter unexpected information that does not match the info here.

## CRITICAL NETWORK LIMITATION
**⚠️ WARNING: Build failures and runtime issues due to network restrictions**

This repository has CRITICAL limitations in sandboxed environments:
- **Google Maven repository (dl.google.com) is BLOCKED** - prevents Android builds and desktop dependencies
- **JetBrains Maven repository access is LIMITED** - causes dependency resolution failures  
- **API endpoint (sebi.io) is BLOCKED** - prevents bird image loading at runtime
- **Android builds WILL FAIL** due to Android Gradle Plugin (AGP) version issues and blocked repositories
- **Desktop builds WILL FAIL** due to missing dependencies from blocked repositories
- **Even basic commands like `./gradlew clean` FAIL** due to plugin resolution issues

## Working Effectively

### Environment Requirements
- Java 17 (verified working: OpenJDK 17.0.16)
- Gradle 8.14.3+ (automatically downloaded via Gradle Wrapper)
- **NEVER CANCEL builds or long-running commands** - network timeouts can take 5+ minutes

### Repository Structure
```
/home/runner/work/birds/birds/
├── shared/                    # Common Kotlin code (App.kt, BirdsViewModel.kt, models)
│   ├── src/commonMain/kotlin/
│   │   ├── App.kt            # Main UI with bird image grid
│   │   ├── BirdsViewModel.kt # Fetches from sebi.io API
│   │   └── model/BirdImage.kt # Data model
├── desktopApp/               # Desktop JVM application 
├── androidApp/               # Android application (BUILDS FAIL - blocked repos)
├── iosApp/                   # iOS application (Xcode project)
├── build.gradle.kts          # Root build configuration
├── settings.gradle.kts       # Project structure
└── gradle/libs.versions.toml # Dependency versions
```

### Build Commands (WITH LIMITATIONS)
**⚠️ NEVER CANCEL: All builds may take 5-10 minutes due to network timeouts. Set timeout to 15+ minutes.**

#### What WORKS (with limitations):
- `java -version` - Check Java installation
- `./gradlew --version` - Check Gradle version (downloads Gradle on first run)
- Repository exploration and code reading

#### What FAILS (due to network blocks):
- `./gradlew tasks` - FAILS: Cannot resolve Android Gradle Plugin
- `./gradlew clean` - FAILS: Plugin resolution issues  
- `./gradlew :desktopApp:build` - FAILS: Cannot resolve dependencies from blocked repositories
- `./gradlew :androidApp:build` - FAILS: Android Gradle Plugin not accessible  
- `./gradlew :shared:build` - FAILS: Dependencies from blocked repositories
- **ALL Gradle commands FAIL** due to Android Gradle Plugin resolution

**Build timing expectations (in unrestricted environments):**
- Task listing: 3-5 seconds
- Dependency resolution: 30-60 seconds on first run
- Desktop build: 3-5 minutes 
- Full project build: 5-10 minutes
- **In restricted environments: ALL Gradle commands fail within 1-2 seconds due to plugin resolution**

### Manual Validation Scenarios
Since the app cannot be built in restricted environments, validation must focus on:

1. **Code Structure Validation:**
   - Verify `shared/src/commonMain/kotlin/App.kt` contains the main Compose UI
   - Check `BirdsViewModel.kt` has the API call to `https://sebi.io/demo-image-api/pictures.json`
   - Confirm `model/BirdImage.kt` has the data model with `author`, `category`, `path` fields

2. **Configuration Validation:**
   - Run `./gradlew tasks` to ensure Gradle configuration is valid
   - Check `gradle/libs.versions.toml` for dependency versions
   - Verify Java 17 is available: `java -version`

3. **In Working Environment (unrestricted network):**
   - Build: `./gradlew :desktopApp:build` (expect 5-8 minutes)
   - Run: `./gradlew :desktopApp:run` 
   - Test bird image loading by clicking category buttons
   - **NOTE:** Bird images API (sebi.io) may also be blocked in some environments
   - Verify images load from sebi.io API in grid layout when network permits

### Available Gradle Tasks
When network allows, key tasks include:
- **Desktop**: `run`, `build`, `package`, `createDistributable`
- **Android**: `assembleDebug`, `installDebug` (FAILS in restricted environments)
- **General**: `clean`, `tasks`, `dependencies`

### Known Issues & Workarounds
1. **Android Gradle Plugin Resolution Fails:**
   - Current version in libs.versions.toml may not exist
   - Google Maven repository blocked prevents any AGP access
   - Workaround: Comment out Android modules in restricted environments

2. **Compose Dependencies Fail:**
   - Desktop builds require Android dependencies that can't be downloaded
   - No reliable workaround for restrictive network environments
   
3. **CI/CD Pipeline:**
   - GitHub Actions CI (`.github/workflows/ci.yml`) may work with different network access
   - Builds Android, iOS, and JVM targets separately
   - Uses Java 17 and specific Gradle actions

### Key Application Components
- **App.kt**: Main Compose UI with `BirdsPage` component showing image grid
- **BirdsViewModel**: Uses Ktor HTTP client to fetch bird data from API
- **BirdImage**: Data model with fields for category filtering and image paths
- **Platform-specific**: `getPlatformName()` function implemented per platform

### Common Commands Summary
```bash
# Check environment (works in restricted environments)
java -version                           # Should show Java 17
./gradlew --version                     # Downloads Gradle if needed (may work)

# All other Gradle commands FAIL in restricted environments:
./gradlew tasks                         # FAILS: Plugin resolution error
./gradlew clean                         # FAILS: Plugin resolution error  
./gradlew :desktopApp:build            # FAILS: Plugin resolution error

# Only code exploration works:
cat shared/src/commonMain/kotlin/App.kt          # View main UI code
cat shared/src/commonMain/kotlin/BirdsViewModel.kt # View API integration
cat gradle/libs.versions.toml                    # Check dependency versions
```

### Workaround for Restricted Environments
To work around network restrictions when absolutely necessary:
1. Comment out Android plugins in `build.gradle.kts` and `shared/build.gradle.kts`
2. Remove `:androidApp` from `settings.gradle.kts`
3. Remove Android dependencies from `shared/build.gradle.kts`
4. This will allow basic Gradle task listing but builds will still fail on Compose dependencies

**Note: These workarounds break the project structure and should only be used for testing in severely restricted environments.**