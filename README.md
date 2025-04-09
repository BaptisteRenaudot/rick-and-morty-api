# M'GPT

This project is a Kotlin-based Android application designed with Clean Architecture principles. The app interacts with the Rick and Morty API to provide users with a detailed and interactive way to explore characters, episodes, and locations from the Rick and Morty universe.


## Project Structure

This project is a Kotlin Multiplatform project targeting both Android and Desktop platforms.

### Code Architecture

The code is organized into several layers, each with a specific responsibility. The architecture follows the Clean Architecture and Model-View-Intent (MVI) pattern, ensuring a unidirectional data flow and clear separation of concerns.

#### Clean Architecture

Clean Architecture divides the project into three main layers: UI, Domain, and Data.

1. **UI Layer**: This layer is responsible for the presentation logic and user interface. It includes Compose Multiplatform code for shared UI components and platform-specific UI code for Android, iOS or desktop.
2. **Domain Layer**: This layer contains the business logic. It includes use cases and interfaces for the repositories.
3. **Data Layer**: This layer handles data management. It includes implementations of the repositories, data sources, and network clients.

##### Communication Flow

- The **UI Layer** interacts with the **Domain Layer** through use cases or repositories interfaces.
- The **Domain Layer** defines the business logic and interacts with the **Data Layer** through repository interfaces.
- The **Data Layer** provides data to the **Domain Layer** by implementing the repository interfaces.

##### Clean Architecture Diagram

```plaintext
+----------------+       +----------------+       +----------------+
|      UI        | ----> |     Domain     | ----> |      Data      |
| (Presentation) |       |(Business Logic)|       | (Data Handling)|
+----------------+       +----------------+       +----------------+
```

#### Model-View-Intent (MVI) Architecture

MVI ensures a unidirectional data flow and clear separation of concerns by dividing the application into three main components: Model, View, and Intent.

1. **Model**: This component contains the state and business logic. It includes view model to communicate with repositories, use cases, and data sources.
2. **View**: This component is responsible for the UI. It includes Compose Multiplatform code for shared UI components and platform-specific UI code for Android, iOS and desktop.
3. **Intent**: This component handles user actions and intents, transforming them into actions that update the Model.

##### Communication Flow

- The **View** sends user intents to the **Intent** component.
- The **Intent** component processes these intents and interacts with the **Model** component.
- The **Model** component updates the state based on the actions and notifies the **View** component of any changes.

##### MVI Architecture Diagram

```plaintext
+----------------+       +----------------+       +----------------+
|     View       | ----> |     Intent     | ----> |     Model      |
| (UI Components)|       | (User Actions) |       | (Business Logic)|
+----------------+       +----------------+       +----------------+
       ^                                              |
       |----------------------------------------------|
```

### Folder Structure

- composeApp: Contains shared code for Compose Multiplatform applications.
  - `commonMain`: Code common to all targets.
  - `androidMain`: Android-specific code.
  - `iosMain`: iOS-specific code.
- iosApp: Contains the iOS application entry point and any SwiftUI code.