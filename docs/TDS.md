# Technical Design Specification (TDS): Smoke Log App v0.1 (MVP)

## 1. Objective
This document defines the software architecture, data model, and component structure for the Smoke Log App MVP. It is the technical implementation plan for the requirements outlined in the PRD.

## 2. Architecture
The application will be built using the **Model-View-ViewModel (MVVM)** architecture to ensure a clean separation of concerns, testability, and scalability for future enhancements.

*   **Model (Data Layer):** Manages the application's data and business logic. This will be handled by the Room Persistence Library, which includes the database, entities (data tables), and Data Access Objects (DAOs).
*   **View (UI Layer):** The user interface, built with Jetpack Compose. It observes the ViewModel for state changes and forwards user interactions to it. It is passive and does not contain business logic.
*   **ViewModel:** Acts as a bridge between the Model and the View. It holds the UI state, processes user inputs, and fetches data from the Model, exposing it to the View in an observable format.

## 3. Data Model (Room Entity)
A single table will be used to store log entries.

*   **Database Table:** `log_entries`
*   **Entity:** `LogEntry.kt`
*   **Columns:**
    *   `id`: `Int` - Primary Key, Auto-generated.
    *   `timestamp`: `Long` - The exact time of the log in milliseconds since epoch.
    *   `remark`: `String?` - The optional text remark. Nullable to allow for logs without a remark.

## 4. Component Plan
The project will be structured into the following files/classes:

### 4.1. Data Layer (Model)
*   **`LogEntry.kt` (Entity):**
    *   A `data class` annotated with `@Entity`.
    *   Defines the table structure as specified in Section 3.
*   **`LogEntryDao.kt` (DAO):**
    *   An `interface` annotated with `@Dao`.
    *   Provides methods to interact with the database.
    *   **Functions:**
        *   `insert(logEntry: LogEntry)`: A `suspend` function to add a new entry.
        *   `getAllEntries(): Flow<List<LogEntry>>`: Returns all log entries, ordered by timestamp descending, wrapped in a `Flow` for real-time UI updates.
*   **`AppDatabase.kt` (Database):**
    *   An `abstract class` extending `RoomDatabase`.
    *   Defines the database configuration, lists the entities (`LogEntry`), and provides access to the DAO.

### 4.2. Business Logic Layer (ViewModel)
*   **`LogViewModel.kt`:**
    *   Extends AndroidX `ViewModel`.
    *   **Responsibilities:**
        *   Holds the list of log entries fetched from the DAO as a `StateFlow`.
        *   Exposes a function `addLog(remark: String?)` which creates a `LogEntry` object with the current timestamp and calls the DAO's `insert` function within a coroutine.
        *   Holds the hard-coded list of remarks.
        *   Holds the state for the dropdown menu's selected item.

### 4.3. UI Layer (View)
*   **`MainActivity.kt`:**
    *   The app's main entry point.
    *   Responsible for setting up the `ViewModel` and calling the main Composable screen.
*   **`LogScreen.kt`:**
    *   Contains the main Composable function, `LogScreen`.
    *   **Responsibilities:**
        *   Collects the `StateFlow` of log entries from the `LogViewModel`.
        *   Builds the UI layout containing the log button, remark dropdown, and the list of entries.
        *   Calls the `ViewModel`'s `addLog` function when the user taps the button.
        *   Uses a `LazyColumn` to efficiently display the list of log entries.