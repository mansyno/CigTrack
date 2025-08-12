# Technical Design Specification (TDS): Smoke Log App v0.2

## 1. Objective
This document updates the software architecture to support editable remarks and outlines the implementation plan for this feature. It builds upon the v0.1 MVP.

## 2. Architecture (Unchanged)
The application will continue to use the **Model-View-ViewModel (MVVM)** architecture.

## 3. Data Model (Revised)
To support editable remarks, the database will be normalized into two tables with a one-to-many relationship. The database version will be incremented, and a destructive migration is acceptable for this version.

*   **Table 1: `remarks`** (New)
    *   **Entity:** `Remark.kt`
    *   **Columns:**
        *   `id`: `Int` - Primary Key, Auto-generated.
        *   `text`: `String` - The remark text (e.g., "With Coffee").

*   **Table 2: `log_entries`** (Modified)
    *   **Entity:** `LogEntry.kt`
    *   **Columns:**
        *   `id`: `Int` - Primary Key, Auto-generated. (Unchanged)
        *   `timestamp`: `Long` - The exact time of the log. (Unchanged)
        *   `remarkId`: `Int?` - Foreign Key referencing `remarks.id`. Nullable.

*   **Relational View:** A new data class `LogEntryWithRemark` will be created to combine a `LogEntry` with its corresponding `Remark` for display purposes.

## 4. Component Plan (Revised for v0.2)

### 4.1. Data Layer (Model)
*   **`Remark.kt` (New Entity):** A data class defining the `remarks` table.
*   **`RemarkDao.kt` (New DAO):** An interface to manage `Remark` entities (`insert`, `update`, `delete`, `getAll`).
*   **`LogEntry.kt` (Modified Entity):** The `remark: String?` column will be replaced with `remarkId: Int?`.
*   **`LogEntryDao.kt` (Modified DAO):**
    *   The `getAllEntries()` function will be updated to perform a database join and return a `Flow<List<LogEntryWithRemark>>`.
    *   The `insert(logEntry: LogEntry)` function remains but will now receive entries with `remarkId`.
*   **`AppDatabase.kt` (Modified Database):**
    *   The `@Database` annotation will be updated to include `Remark::class`.
    *   The database `version` will be incremented to `2`.
    *   The database builder will include `.fallbackToDestructiveMigration()`.
    *   An abstract function for `remarkDao()` will be added.

### 4.2. Business Logic & UI (New "Remark Settings" Feature)
*   **`RemarkSettingsViewModel.kt` (New):** A `ViewModel` to manage the UI state for the remarks settings screen. It will interact with the `RemarkDao`.
*   **`RemarkSettingsScreen.kt` (New):** A new Composable screen that displays a list of all `Remark`s. It will include UI for adding a new remark and for editing an existing remark (initially no delete).

### 4.3. Business Logic & UI (Main Logging Feature Modifications)
*   **`LogViewModel.kt` (Modified):**
    *   Will now fetch data from the DAO as a `StateFlow` of `LogEntryWithRemark`.
    *   The `addLog` function will be updated to accept a nullable `remarkId: Int`.
    *   Will fetch the list of `Remark` objects for the dropdown.
*   **`LogScreen.kt` (Modified):**
    *   The dropdown will be populated with `Remark` objects. Its state will now hold the selected `remarkId`.
    *   The `LazyColumn` will display the `LogEntryWithRemark` objects.
    *   A navigation element (e.g., an `IconButton`) will be added to navigate to the `RemarkSettingsScreen`.

## 5. Phased Implementation Plan

*   **Phase 1: Additive Data Layer.** Implement the new `Remark.kt` entity and `RemarkDao.kt`. Update `AppDatabase.kt` to version 2 with destructive migration. The app will remain runnable.
*   **Phase 2: Independent Settings UI.** Build the `RemarkSettingsScreen` and its `ViewModel`. Provide a temporary navigation path to it. This screen will be for adding and editing only. The app will remain runnable.
*   **Phase 3: Core Refactor.** Modify the `LogEntry` entity, `LogEntryDao`, `LogViewModel`, and `LogScreen` to use the new foreign key relationship. This is the main refactoring step.
*   **Phase 4: Add Delete Functionality.** Implement the delete feature (e.g., via long-press and confirmation dialog) in the `RemarkSettingsScreen` and handle the logic for orphaned log entries.