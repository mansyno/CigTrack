# Technical Design Specification (TDS) - Analysis Feature (Extends v0.2)

## 1. Objective
This document details the technical design for implementing the "Analysis Screen" feature. This feature will provide users with visualizations of their smoking patterns to aid in their cessation efforts. It builds upon the architecture and data model established in TDS v0.2.

## 2. Architecture (Unchanged)
The application will continue to use the **Model-View-ViewModel (MVVM)** architecture.

## 3. Data Model & DAO Modifications

No changes to the existing `remarks` or `log_entries` table schemas are required for the core analysis. New queries will be added to existing DAOs.

### 3.1. New Data Classes for Aggregated Results
Simple data classes will be defined to hold the raw results of aggregation queries. The ViewModel will be responsible for transforming these into a format suitable for the UI.

*   `data class DailyCountFromQuery(val day: String, val count: Int)`
*   `data class HourlyCountFromQuery(val hour: String, val count: Int)`
*   `data class DayOfWeekCountFromQuery(val dayOfWeek: String, val count: Int)`
*   `data class RemarkCount(val remarkText: String, val count: Int)`

### 3.2. `LogEntryDao.kt` (New Queries)
New functions will be added to retrieve aggregated data:

*   `@Query("SELECT COUNT(id) FROM log_entries WHERE timestamp >= :sinceTimestamp") suspend fun getTotalCountSince(sinceTimestamp: Long): Int`
*   `@Query("SELECT strftime('%Y-%m-%d', timestamp / 1000, 'unixepoch', 'localtime') AS day, COUNT(id) AS count FROM log_entries WHERE timestamp >= :sinceTimestamp GROUP BY day ORDER BY day ASC") fun getDailyCounts(sinceTimestamp: Long): Flow<List<DailyCountFromQuery>>`
*   `@Query("SELECT strftime('%H', timestamp / 1000, 'unixepoch', 'localtime') AS hour, COUNT(id) AS count FROM log_entries WHERE timestamp >= :sinceTimestamp GROUP BY hour ORDER BY hour ASC") fun getHourlyCounts(sinceTimestamp: Long): Flow<List<HourlyCountFromQuery>>`
*   `@Query("SELECT strftime('%w', timestamp / 1000, 'unixepoch', 'localtime') AS day_of_week, COUNT(id) AS count FROM log_entries WHERE timestamp >= :sinceTimestamp GROUP BY day_of_week ORDER BY day_of_week ASC") fun getDayOfWeekCounts(sinceTimestamp: Long): Flow<List<DayOfWeekCountFromQuery>>`
*   `@Query("SELECT r.text, COUNT(le.id) AS count FROM log_entries le JOIN remarks r ON le.remarkId = r.id WHERE le.timestamp >= :sinceTimestamp GROUP BY r.text ORDER BY count DESC") fun getRemarkCounts(sinceTimestamp: Long): Flow<List<RemarkCount>>`

### 3.3. `RemarkDao.kt` (No new queries anticipated for MVP analysis)

## 4. Component Plan (New for Analysis Feature)

### 4.1. Business Logic (ViewModel)
*   **`AnalysisViewModel.kt` (New):**
    *   Will take `LogEntryDao` as a constructor parameter.
    *   Will manage UI state for the Analysis screen.
    *   Will contain functions to fetch and process data from DAOs for each chart type.
    *   **Crucially, it will transform the String-based DAO query results (e.g., `DailyCountFromQuery`) into formats suitable for the chosen charting library (e.g., `List<DataPoint>` with Long timestamps).**
    *   Will expose data via `StateFlow` for the UI to observe.
    *   Will handle date range filtering logic, re-fetching data when the filter changes.

### 4.2. UI Layer (Composable Screen)
*   **`AnalysisScreen.kt` (New):**
    *   A Composable screen that takes `AnalysisViewModel` as a parameter.
    *   Observes `StateFlow`s from the ViewModel.
    *   Layout will consist of a `Scaffold` with a `TopAppBar`, and a `LazyColumn` containing `Card`s for each chart.
    *   Will include UI elements for date range selection.

### 4.3. Navigation
*   Navigation will be handled via state changes in `MainActivity.kt`.
*   Navigation to `AnalysisScreen` will be initiated from an `IconButton` in the `TopAppBar` of `LogScreen`.

### 4.4. Charting Library (To Be Decided)
*   **Criteria:** Compose-friendly, good documentation, supports line, bar, and pie charts.
*   **Candidates:** Compose Charts, Vico.

### 5. Phased Implementation Plan (for Analysis Feature)
*   *(The implementation plan is detailed in `Analysis_Feature_Tasks.md`)*

## 6. Dependencies
*   Chosen charting library.
*   Existing Room, Coroutines, ViewModel, Compose dependencies.