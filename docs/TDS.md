# Technical Design Specification (TDS): Smoke Log App v0.3

## 1. Objective
This document describes the current, as-built technical architecture of the application following the major UI redesign. It supersedes v0.2 and serves as the definitive foundation for new features.

## 2. Architecture (Unchanged)
The application uses the **Model-View-ViewModel (MVVM)** architecture.

## 3. Data Model (Unchanged from v0.2)
The database schema is normalized and includes a flag for hiding remarks.

*   **Table: `remarks`** (`Remark.kt`)
    *   `id`: Int (PK)
    *   `text`: String
    *   `isHidden`: Boolean (default `false`)
*   **Table: `log_entries`** (`LogEntry.kt`)
    *   `id`: Int (PK)
    *   `timestamp`: Long
    *   `remarkId`: Int? (Foreign Key to `remarks.id`, configured with `onDelete = SET_NULL`)
*   **Relational View:** `LogEntryWithRemark` is used by the DAO to join `LogEntry` with its `Remark`.

## 4. Component Plan (Current As-Built State)

### 4.1. Data Layer
*   `RemarkDao.kt`: Manages `Remark` entities.
*   `LogEntryDao.kt`: Manages `LogEntry` entities.
*   `AppDatabase.kt`: Room database configuration at version 3.

### 4.2. Business Logic (ViewModels)
*   `LogViewModel.kt`: Manages the state and business logic for the main logging screen.
*   `RemarkSettingsViewModel.kt`: Manages the state and business logic for the remark settings screen.

### 4.3. UI Layer (Views)
*   **`MainActivity.kt`**: Acts as the central navigation controller using mutable state.
*   **`LogScreen.kt`**: The main screen, built with `Scaffold`, `TopAppBar`, `FAB`, and a date-grouped `LazyColumn` of `Card`s.
*   **`RemarkSettingsScreen.kt`**: The settings screen, built with `Scaffold`, `TopAppBar`, and a `LazyColumn` of `Card`s with `Checkbox`es.

### 4.4. Theming (`ui.theme` package)
*   The application is configured with a custom light theme.

## 5. Feature-Specific Technical Designs
This document provides a high-level overview. For detailed technical specifications of major features, refer to the following documents:

*   **Analysis Feature:** See `docs/TDS_Analysis_Feature.md`.