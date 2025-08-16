# Product Requirements Document (PRD): Smoke Log App v0.3

## 1. Objective
To provide a simple, effective mobile application for a user to log every cigarette smoked, manage contextual remarks, and analyze their smoking patterns to support cessation.

## 2. Target User
A single, technically-adept user (the developer) participating in a smoking cessation workshop who requires a digital tool for logging and analysis.

## 3. Core Features (As-Built)
*   **Logging:** A main screen for logging entries with an optional, user-defined remark. A Floating Action Button opens a dialog for quick entry.
*   **Log History:** A date-grouped list of all past log entries on the main screen, presented in a clean, card-based UI.
*   **Log Management:** Ability to delete log entries via a long-press confirmation dialog.
*   **Remark Management:** A dedicated settings screen to add new remarks and to hide/unhide existing remarks from the logging dialog.
*   **Navigation:** A modern, state-based navigation system using a `TopAppBar` with icons to switch between the Log screen and the Settings screen.

## 4. Future Enhancements / Current Development
The following features are planned for future iterations.

*   **In Development:**
    *   **Data Analysis & Summary Screen:** A dedicated screen to visualize key statistics (daily counts, trigger frequency, time-based patterns, etc.) to provide actionable insights into smoking habits. *See `Analysis_Feature_Concepts.md` for details.*
*   **Backlog:**
    *   **Data Export:** Functionality to export the log data to a common format (e.g., CSV).
    *   **Log Editing:** The ability to edit the timestamp or remark of a past entry.
    *   **Dark Theme:** Implementation of a dark mode color scheme.

## 5. Technical Stack
*   **Language:** Kotlin
*   **UI Framework:** Jetpack Compose (Material 3)
*   **Data Persistence:** Room Persistence Library
*   **Development Environment:** Android Studio with Gemini AI assistant.