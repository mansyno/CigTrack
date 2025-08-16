# Step-by-Step Tasks: Analysis Feature Implementation

This document breaks down the development of the Analysis Feature into manageable tasks, based on the `Analysis_Feature_Concepts.md` and `TDS_Analysis_Feature.md`.

## Phase 1: Setup & Basic Structure

1.  **[x]** Project Setup: Review existing PRD and TDS documents.
2.  **[x]** Create `Analysis_Feature_Concepts.md` document (Done).
3.  **[x]** Create `TDS_Analysis_Feature.md` document (Done).
4.  **[x]** Create this `Analysis_Feature_Tasks.md` document (Done).
5.  **[x]** Decide on and add the dependency for a Jetpack Compose compatible charting library.
6.  **[x]** Create basic `AnalysisScreen.kt` Composable file with a `Scaffold` and `TopAppBar`.
7.  **[x]** Create basic `AnalysisViewModel.kt` class extending `ViewModel`.
8.  **[]** Update state-based navigation: Modify MainActivity.kt to manage navigation to the new AnalysisScreen.
9.  **[]** Add navigation trigger: Implement an `IconButton` to navigate to `AnalysisScreen`.
10. **[]** Verify navigation to the empty `AnalysisScreen` works.

## Phase 2: Daily Cigarette Count Chart

11. **[]** Data Layer: Define the `DailyCountFromQuery` data class as specified in the TDS.
12. **[]** Data Layer: Implement the `getDailyCounts(...)` query in `LogEntryDao` returning `Flow<List<DailyCountFromQuery>>`.
13. **[]** ViewModel: Inject `LogEntryDao` into `AnalysisViewModel`.
14. **[]** ViewModel: Create a `StateFlow` (e.g., `dailyCountsData`) in `AnalysisViewModel`.
15. **[]** ViewModel: Implement logic to call `getDailyCounts`, transform results from `DailyCountFromQuery` to the chart library's data format, and update `dailyCountsData`.
16. **[]** UI Layer: In `AnalysisScreen`, collect `dailyCountsData` from the ViewModel.
17. **[]** UI Layer: Implement a line chart using the chosen library, feeding it the collected data.
18. **[]** UI Layer: Add a simple title (e.g., "Daily Cigarettes") for this chart within a `Card`.
19. **[]** ViewModel & UI: Implement basic date range filtering logic (e.g., "Last 7 days," "Last 30 days").

## Phase 3: Remark Frequency Chart

20. **[]** Data Layer: Define the `RemarkCount` data class.
21. **[]** Data Layer: Implement the `getRemarkCounts(...)` query in `LogEntryDao`.
22. **[]** ViewModel: Create a `StateFlow` for remark frequency data.
23. **[]** ViewModel: Implement logic to call `getRemarkCounts`, process results, and update the `StateFlow`.
24. **[]** UI Layer: In `AnalysisScreen`, collect the remark frequency data.
25. **[]** UI Layer: Implement a pie chart (or bar chart) for remark frequencies.
26. **[]** UI Layer: Add a title for this chart (e.g., "Smoking Triggers").

## Phase 4: Hourly/Day-of-Week Pattern Charts

27. **[]** Data Layer: Define `HourlyCountFromQuery` and `DayOfWeekCountFromQuery` data classes.
28. **[]** Data Layer: Implement `getHourlyCounts(...)` query in `LogEntryDao`.
29. **[]** Data Layer: Implement `getDayOfWeekCounts(...)` query in `LogEntryDao`.
30. **[]** ViewModel: Add `StateFlow`s for hourly and day-of-week data.
31. **[]** ViewModel: Implement logic to fetch and process these flows.
    *   **[Sub-Task]** Map the day-of-week string from the DAO (where "0" is Sunday) to a display label (e.g., "Sun", "Mon").
32. **[]** UI Layer: Display hourly counts as a bar chart.
33. **[]** UI Layer: Display day-of-week counts as a bar chart.

## Phase 5: Refinements & Advanced Analysis (Post-MVP for Analysis)

34. **[]** UI/UX: Implement loading states.
35. **[]** UI/UX: Implement "empty state" messages for charts.
36. **[]** Data/ViewModel/UI: (Optional) Implement "Remarks by Time Slot" analysis.
37. **[]** UI/UX: Refine chart styling.
38. **[]** UI/UX: Add more date range filter options.
39. **[]** Testing: Thoroughly test all charts.
40. **[]** Review & Iterate.