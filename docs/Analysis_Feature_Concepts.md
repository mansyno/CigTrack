# Analysis Feature Concepts: Understanding Smoking Patterns

## 1. Objective
This document outlines the concepts and goals for the "Analysis Screen" feature in the CigTrack application. The primary objective of this feature is to provide the user with actionable insights into their smoking habits, helping them understand when and why they smoke, to support their smoking cessation journey.

## 2. Core User Needs Addressed

The Analysis Screen aims to help the user answer critical questions about their smoking patterns:

*   **"Am I making progress in reducing smoking?"** (Overall Trend)
*   **"When am I most vulnerable to smoking?"** (Time-based Patterns)
*   **"What are my main triggers/reasons for smoking?"** (Remark-based Patterns)
*   **"Are specific triggers more potent at certain times or on certain days?"** (Correlating Remarks and Time)

## 3. Proposed Analyses & Visualizations

Based on the core user needs, the following analyses and corresponding visualizations are proposed. These will form the main components of the Analysis Screen.

### 3.1. Smoking Frequency Trends
*   **Purpose:** To track overall progress and identify periods of increased or decreased smoking.
*   **Analyses:**
    *   Number of cigarettes smoked per day.
    *   Number of cigarettes smoked per week.
*   **Visualization Idea:**
    *   **Daily/Weekly Cigarette Count Trend (Line Chart):**
        *   X-axis: Date (for daily) or Week Number (for weekly).
        *   Y-axis: Number of cigarettes.
        *   *Benefit:* Provides a clear visual of progress or setbacks, motivating the user.

### 3.2. Identifying High-Risk Times
*   **Purpose:** To pinpoint specific times of day or days of the week when the user is most likely to smoke.
*   **Analyses:**
    *   Aggregate smoking instances by the hour of the day.
    *   Aggregate smoking instances by the day of the week.
*   **Visualization Ideas:**
    *   **Cigarettes by Hour of Day (Bar Chart):**
        *   X-axis: Hour (e.g., 0-23 or 12 AM - 11 PM).
        *   Y-axis: Average or total cigarettes smoked in that hour (over a selected period).
        *   *Benefit:* Highlights personal "danger zones" during the day, allowing for proactive coping strategies.
    *   **Cigarettes by Day of Week (Bar Chart):**
        *   X-axis: Day (e.g., Mon, Tue, ..., Sun).
        *   Y-axis: Average or total cigarettes smoked on that day.
        *   *Benefit:* Reveals if certain days (e.g., weekends, specific workdays) are more challenging.

### 3.3. Understanding Triggers (Remarks)
*   **Purpose:** To identify the most common reasons or contexts (remarks) associated with smoking.
*   **Analyses:**
    *   Tally the frequency of each remark logged.
    *   Count total cigarettes associated with each remark.
*   **Visualization Ideas:**
    *   **Most Common Remarks (Pie Chart or Bar Chart):**
        *   Shows the percentage or raw count of each remark.
        *   *Benefit:* Clearly identifies dominant triggers, guiding efforts to address them.
    *   **(Optional) Cigarettes Smoked per Remark (Bar Chart):**
        *   X-axis: Remark.
        *   Y-axis: Total cigarettes logged with that remark.
        *   *Benefit:* Shows which triggers are associated with the highest volume of smoking.

### 3.4. Correlating Triggers with Time (Deeper Insights)
*   **Purpose:** To uncover if specific triggers are more prominent or impactful at certain times of day or on particular days of the week.
*   **Analyses:**
    *   Correlate remark frequency with time slots (e.g., morning, afternoon, evening, night).
    *   Correlate remark frequency with days of the week.
*   **Visualization Ideas:**
    *   **Remarks Distribution by Time Slot (Stacked Bar Chart or Grouped Bar Chart):**
        *   X-axis: Time Slot.
        *   Bars segmented by remark, showing the proportion/count of triggers within that time slot.
        *   *Benefit:* Uncovers patterns like "Stress is high in the morning, Boredom in the evening," enabling targeted interventions.
    *   **Remarks Distribution by Day of Week (Similar to above but for days):**
        *   *Benefit:* Shows if certain triggers are more pronounced on specific days (e.g., "Social" on weekends).

## 4. Key UI/UX Considerations
*   **Date Range Filter:** Essential for allowing users to analyze data over different periods (e.g., "Last 7 Days," "Last 30 Days," "All Time," custom range).
*   **Clarity and Simplicity:** Charts should be easy to understand at a glance. Prioritize clarity over cramming too much information into one visual.
*   **Empty/Insufficient Data State:** Gracefully handle scenarios where there isn't enough data to generate meaningful charts (e.g., display a message encouraging more logging).
*   **Loading State:** Provide visual feedback (e.g., progress indicators) while data is being fetched and processed.

## 5. Success Metrics (for the Analysis Feature)
*   User can access and view the Analysis Screen.
*   Charts load and display data accurately based on logged entries.
*   User reports gaining a better understanding of their smoking patterns after using the screen.
*   The feature contributes positively to the user's motivation and cessation efforts.
