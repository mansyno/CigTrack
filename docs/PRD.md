# Product Requirements Document (PRD): Smoke Log App v0.1 (MVP)

## 1. Objective
To provide an extremely low-friction mobile application for a user to log every cigarette smoked. The primary goal is speed and ease of data entry to ensure consistent tracking during a smoking cessation program.

## 2. Target User
A single, technically-adept user (the developer) who is actively participating in a smoking cessation workshop and needs to replace manual note-taking with a faster, digital solution. The user is running Android 13 on a Samsung S20 FE.

## 3. MVP Feature Set (In Scope)
The first functional version of the application will be a single-screen experience with the following components:

*   **3.1. Instant Log Button:** A large, prominent button. A single tap immediately records the current system timestamp as a new log entry.
*   **3.2. Optional Remark Dropdown:** A dropdown menu located near the log button, pre-populated with a static list of contextual remarks. If a remark is selected when the log button is tapped, the remark is saved along with the timestamp. If no remark is selected, only the timestamp is saved.
*   **3.3. Hard-coded Remarks:** The initial list of remarks will be hard-coded inside the application. The list is: "With Coffee", "After Meal", "Waking Up", "Driving", "Stress", "Boredom".
*   **3.4. Log Display:** A simple, reverse-chronological list on the same screen that displays all saved log entries. Each entry will show the timestamp and the associated remark, if any (e.g., "Aug 11, 2025, 10:45 AM - With Coffee").

## 4. Future Enhancements (Post-MVP)
The following features are planned for future iterations to build upon the core functionality of the MVP:

*   **Data Analysis & Summary Screen:** A dedicated screen to visualize key statistics, such as daily/weekly counts, average time between logs, and frequency of remarks.
*   **Editable Remarks via Settings:** A settings screen allowing the user to add, edit, and delete the list of remarks.
*   **Data Export:** Functionality to export the log data into a common format (e.g., CSV) for external analysis.
*   **Log Management:** The ability to edit the timestamp or remark of a past entry, or to delete entries entirely.

## 5. Success Metric
The MVP will be considered a success when the user can:
1.  Build a functional, installable APK from the project.
2.  Install and run the app on their target device.
3.  Successfully log at least 10 entries using both the instant log button and the remark dropdown.
4.  View all 10 entries correctly displayed in the on-screen list.

## 6. Technical Stack
*   **Language:** Kotlin
*   **UI Framework:** Jetpack Compose
*   **Data Persistence:** Room Persistence Library (wrapper for built-in SQLite)
*   **Target Android Version:** Android 13 (API Level 33)
*   **Development Environment:** Android Studio with Gemini AI assistant.