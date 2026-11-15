package com.example.fina

import androidx.compose.ui.graphics.Color

object AppConfig {
    // 4 Core App Colors requested: Primary, Secondary, Accent, Button Base Color
    val primaryColor = Color(0xff1e3b2f)       // Primary background & theme color
    val secondaryColor = Color(0xfff5f0e6)     // Secondary / Card background color
    val accentColor = Color(0xffff7f50)        // Accent Teal / Highlights color
    val buttonBaseColor = Color(0xff3a735c)    // Button Base Color

    // Color for selected option outline across cards
    val selectedOptionColor = Color(0xFF1B3022)

    // Additional design colors
    val surfaceWhite = Color(0xFFEFE9E1)
    val cardInnerBg = Color(0xFFEAE3DA)        // Background for inner groups like monthly expense
    val textDark = Color(0xFF1B2E23)
    val textMuted = Color(0xFF6B7A71)
    val activityItemBg = Color(0xFFE5DFD7)
    val eventCardBg = Color(0xFFE8E2DA)

    // Brand & App Info
    const val BRAND_NAME = "FINA"
    const val APP_NAME = "Fina Financial App"
    const val VERSION = "v0.1.0-alpha"
    const val EXPENSE_PERIOD = "1/10 - 31/10"

    // Reorganised color listing from darkest to lightest without fixed category naming.
    // Automatically mapped from highest percentage to lowest percentage for the donut graph.
    val donutColors = listOf(
        Color(0xff2c5745), // 1st (Darkest / Highest %)
        Color(0xff498f72), // 2nd
        Color(0xff58aa88), // 3rd
        Color(0xff74b89c), // 4th
        Color(0xff90c7b0), // 5th
        Color(0xffacd5c4)  // 6th (Lightest / Lowest %)
    )

    data class RawCategory(val name: String, val amount: Float)

    private val rawExpenseCategories = listOf(
        RawCategory("FOOD & GROCERIES", 300f),
        RawCategory("INVESTMENT", 200f),
        RawCategory("TRANSPORT", 150f),
        RawCategory("ENTERTAINMENT", 120f),
        RawCategory("UTILITIES", 110f),
        RawCategory("MEDICAL", 90f)
    )

    data class ColoredCategory(val name: String, val amount: Float, val color: Color)

    // Sorted categories from highest to lowest amount, assigned donutColors sequentially (darkest to lightest)
    val expenseCategories: List<ColoredCategory> by lazy {
        val sorted = rawExpenseCategories.sortedByDescending { it.amount }
        sorted.mapIndexed { index, cat ->
            ColoredCategory(
                name = cat.name,
                amount = cat.amount,
                color = donutColors.getOrElse(index) { donutColors.last() }
            )
        }
    }

    // Helper to get category color by name for activity records stripe matching
    fun getCategoryColor(categoryName: String): Color {
        return expenseCategories.find { it.name.equals(categoryName, ignoreCase = true) }?.color ?: primaryColor
    }

    // Activity Items linked to category name
    data class ActivityItem(val title: String, val date: String, val amount: String, val categoryName: String)
    
    val activities = listOf(
        ActivityItem("TUAS LINK > PASIR RIS (EWL)", "27-SEP-2025", "$ 1.23", "TRANSPORT"),
        ActivityItem("BOWLING @ TAMPINES SAFRA", "01-AUG-2025", "$ 12.34", "ENTERTAINMENT"),
        ActivityItem("MOOMOO ABC:SGX STOCK", "15-DEC-2024", "$ 123.45", "INVESTMENT"),
        ActivityItem("CGH OUTPATIENT COST", "07-DEC-2024", "$ 12.34", "MEDICAL"),
        ActivityItem("SPC ELECTRICITY BILL", "01-JAN-2023", "$ 12.34", "UTILITIES")
    )

    // Upcoming Events
    data class UpcomingEvent(val day: String, val monthYear: String, val title: String, val progress: Float, val categoryName: String)
    
    val upcomingEvents = listOf(
        UpcomingEvent("10", "MAY/24", "SPC ELECTRICITY BILL (MONTHLY)", 0.7f, "UTILITIES"),
        UpcomingEvent("16", "JUN/24", "ABC SGX QUARTERLY DIVIDENDS", 0.5f, "INVESTMENT"),
        UpcomingEvent("25", "DEC/24", "ABC SGX QUARTERLY DIVIDENDS", 0.9f, "INVESTMENT")
    )

    // Helper to validate major provider email
    fun isValidMajorEmail(email: String): Boolean {
        val trimmed = email.trim().lowercase()
        val validDomains = listOf(
            "gmail.com", "outlook.com", "yahoo.com", "hotmail.com",
            "icloud.com", "live.com", "msn.com", "aol.com", "proton.me", "protonmail.com"
        )
        val parts = trimmed.split("@")
        if (parts.size != 2) return false
        val domain = parts[1]
        return validDomains.any { domain == it || domain.endsWith(".$it") }
    }
}
