# Vehicle Companion Android App

A modern Android application built with Jetpack Compose that helps vehicle owners manage their car profiles and discover interesting places using the Roadtrippers API.

## Features

### Garage Management
- **Vehicle Profiles**: Add, view, and edit vehicle information including name, make, model, year, VIN, and fuel type
- **Hero Images**: Optional local photo picker for each vehicle
- **Empty State**: Clean empty garage state with add vehicle call-to-action
- **Multiple Vehicles**: Support for managing multiple vehicle profiles
- **Data Persistence**: All vehicle data persisted using Room database

### Places Discovery (POI Integration)
- **Roadtrippers API**: Integration with Roadtrippers API to discover interesting spots in a fixed region
- **List View**: Cards displaying POI icons, names, categories, and star ratings (0-5)
- **Interactive Maps**: Google Maps integration with markers and clustering
- **Detail View**: Expandable bottom sheet with place details, static mini map, and "Open in Browser" functionality
- **Favorites**: Save/favorite toggle with Room persistence
- **Image Loading**: Dynamic POI images loaded via Coil library

### Navigation & UI
- **Tab Navigation**: Clean bottom navigation between Garage and Places
- **Material 3 Design**: Modern UI following Material Design 3 principles
- **Responsive Layout**: Optimized for different screen sizes
- **Accessibility**: Basic TalkBack support and readable UI elements

## Architecture

### Architecture Pattern
- **MVVM with MVI elements**: Clean separation of concerns with reactive state management
- **Repository Pattern**: Centralized data access layer
- **Dependency Injection**: Hilt for ViewModels, repositories, and Room components

### Project Structure
```
app/src/main/java/com/yamasoft/vehiclecompanion/
├── data/
│   ├── local/
│   │   ├── dao/           # Room DAOs
│   │   ├── entity/        # Room entities
│   │   └── VehicleCompanionDatabase.kt
│   ├── remote/
│   │   ├── api/           # Retrofit API services
│   │   └── dto/           # Data transfer objects
│   └── repository/        # Repository implementations
├── di/                    # Hilt dependency injection modules
├── domain/
│   ├── mapper/            # Data mappers
│   ├── model/             # Domain models
│   └── repository/        # Repository interfaces
└── ui/
    ├── components/        # Reusable Compose components
    ├── navigation/        # Navigation setup
    └── screen/            # Feature screens
        ├── garage/        # Vehicle management screens
        └── places/        # POI discovery screens
```

### Technology Stack
- **UI Framework**: Jetpack Compose with Material 3
- **Navigation**: Jetpack Navigation Compose
- **Dependency Injection**: Hilt
- **Database**: Room with KTX extensions
- **Networking**: Retrofit + OkHttp with Moshi JSON parsing
- **Image Loading**: Coil Compose
- **Maps**: Google Maps for Compose
- **Coroutines**: Kotlin Coroutines for async operations
- **Build System**: Gradle with Version Catalog (libs.versions.toml)

## Setup & Installation

### Prerequisites
- Android Studio Hedgehog or newer
- JDK 11 or higher
- Android SDK API 26+ (minimum) / API 36 (target)
- Google Maps API key (for map functionality)

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd VehicleCompanion4
   ```

2. **Configure Google Maps API Key**
   - Create a `local.properties` file in the root directory
   - Add your Google Maps API key:
     ```properties
     GOOGLE_MAPS_API_KEY=your_api_key_here
     ```
   - Enable Maps SDK for Android in Google Cloud Console

3. **Build and Run**
   ```bash
   ./gradlew assembleDebug
   ```
   Or use Android Studio's build and run functionality.

### API Configuration
The app uses the Roadtrippers API endpoint:
```
https://api2.roadtrippers.com/api/v2/pois/discover?sw_corner=-84.540499,39.079888&ne_corner=-84.494260,39.113254&page_size=50
```

## Testing

### Running Tests
```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

### Test Coverage
- **Unit Tests**: Vehicle data class validation, API service mocking
- **Integration Tests**: Repository layer testing with MockK
- **UI Tests**: Compose UI testing for vehicle and place cards (optional)

### Test Structure
```
app/src/test/java/           # Unit tests
app/src/androidTest/java/    # Instrumented tests
```

## Design Decisions

### UI/UX Choices
- **Bottom Sheet Pattern**: Used for place details to maintain context while showing detailed information
- **Card-based Layout**: Consistent card design for both vehicles and places
- **Empty States**: Thoughtful empty states with clear call-to-action buttons
- **Progressive Disclosure**: Bottom sheet expands from partial to full view
- **Image Fallbacks**: Graceful handling of missing images with default placeholders

### Technical Decisions
- **Single Activity Architecture**: Leveraging Jetpack Navigation Compose
- **State Hoisting**: Proper state management following Compose best practices
- **Modular Components**: Small, reusable Composables for maintainability
- **Repository Pattern**: Clean separation between data sources and business logic
- **Coroutines**: Async operations handled with structured concurrency

## Error & Empty State Handling

### Network Errors
- Graceful handling of API failures with user-friendly error messages
- Retry mechanisms for transient network issues
- Offline capability for cached data

### Empty States
- **Empty Garage**: Welcoming empty state with prominent "Add Vehicle" button
- **No Places Found**: Informative message when no POIs are available
- **No Favorites**: Clear guidance for users to start favoriting places

### Validation
- **Vehicle Form**: Input validation for required fields (name, make, model, year)
- **Error Feedback**: Clear error messages and field highlighting
- **Data Integrity**: VIN format validation and fuel type selection

## Future Enhancements

Given more time, the following features would be prioritized:

### High Priority
- **Search & Filtering**: Search vehicles by name/make, filter places by category
- **Trip Planning**: Route planning between multiple POIs
- **Maintenance Tracking**: Vehicle service history and reminders
- **Photo Gallery**: Multiple images per vehicle with gallery view

### Medium Priority
- **Export/Import**: Backup and restore vehicle data
- **Social Features**: Share favorite places with other users
- **Offline Maps**: Cached map tiles for offline viewing
- **Push Notifications**: Maintenance reminders and new places alerts

### Technical Improvements
- **Automated Testing**: Expanded test coverage with UI automation
- **Performance**: Image caching optimization and lazy loading
- **Accessibility**: Enhanced screen reader support and keyboard navigation
- **Internationalization**: Multi-language support

## Development Guidelines

### Code Style
- Kotlin coding conventions
- Compose best practices for recomposition awareness
- Small, focused functions and classes
- Meaningful variable and function names

### Git Workflow
- Feature branches for new functionality
- Descriptive commit messages
- Pull request reviews before merging
- Semantic versioning for releases

### Performance Considerations
- Lazy loading for large lists
- Image optimization with Coil
- Efficient Room queries with proper indexing
- Coroutine scope management

## License

This project is developed as part of an Android developer interview assessment.

## Contributing

This is a candidate project for interview purposes. For questions or clarifications, please contact the development team.

---

**Built using Jetpack Compose and Modern Android Development practices**
