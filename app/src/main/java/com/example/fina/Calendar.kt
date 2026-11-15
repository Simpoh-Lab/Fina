package com.example.fina

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fina.ui.theme.FINATheme
import java.util.Calendar

data class CalEvent(val day: Int, val title: String, val amount: String, val month: Int, val year: Int)

@Composable
fun CalendarScreen() {
    val cal = remember { Calendar.getInstance() }
    val realYear = cal.get(Calendar.YEAR)
    val realMonth = cal.get(Calendar.MONTH) + 1 // 1-12
    val realDay = cal.get(Calendar.DAY_OF_MONTH)

    var monthIndex by remember { mutableStateOf(realMonth - 1) } // 0-based month index
    var year by remember { mutableStateOf(realYear) }
    var lastSwipeTime by remember { mutableStateOf(0L) }

    val monthNames = listOf(
        "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
        "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
    )

    // Sample events dynamically anchored around the system date & future months
    val nextMonth = if (realMonth == 12) 1 else realMonth + 1
    val nextMonthYear = if (realMonth == 12) realYear + 1 else realYear

    val sampleEvents = listOf(
        CalEvent(minOf(realDay, 28), "SPC ELECTRICITY BILL (MONTHLY)", "- $12.34", realMonth, realYear),
        CalEvent(minOf(realDay + 1, 28), "CGH MEDICAL APPOINTMENT", "$0", realMonth, realYear),
        CalEvent(minOf(realDay + 3, 28), "GRAB POSTPAID BILL", "- $12.34", realMonth, realYear),
        CalEvent(10, "ABC:SGX QUARTERLY DIVIDENDS", "+ $12.34", nextMonth, nextMonthYear)
    )

    fun changeMonth(delta: Int) {
        var newMonth = monthIndex + delta
        var newYear = year
        if (newMonth > 11) {
            newMonth = 0
            newYear++
        } else if (newMonth < 0) {
            newMonth = 11
            newYear--
        }
        monthIndex = newMonth
        year = newYear
    }

    // Get events for the currently displayed month & year
    val currentMonthNumber = monthIndex + 1
    val monthEvents = sampleEvents.filter { it.month == currentMonthNumber && it.year == year }
    val hasEventsInMonth = monthEvents.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppConfig.primaryColor)
    ) {
        CalendarTopBar()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp),
                    colors = CardDefaults.cardColors(containerColor = AppConfig.secondaryColor),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 20.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            // 1. Calendar Month Grid Card with throttled swipe (0.5s delay) & arrow navigation
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .pointerInput(Unit) {
                                        detectHorizontalDragGestures { _, dragAmount ->
                                            val currentTime = System.currentTimeMillis()
                                            if (currentTime - lastSwipeTime > 500) { // 0.5 seconds interval
                                                if (dragAmount > 50) {
                                                    changeMonth(-1)
                                                    lastSwipeTime = currentTime
                                                } else if (dragAmount < -50) {
                                                    changeMonth(1)
                                                    lastSwipeTime = currentTime
                                                }
                                            }
                                        }
                                    },
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = AppConfig.surfaceWhite),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    // Month header with arrows
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(
                                            onClick = { changeMonth(-1) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.ChevronLeft,
                                                contentDescription = "Previous Month",
                                                tint = AppConfig.textDark
                                            )
                                        }

                                        Text(
                                            text = "${monthNames[monthIndex]} $year",
                                            color = AppConfig.textDark,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Black
                                        )

                                        IconButton(
                                            onClick = { changeMonth(1) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.ChevronRight,
                                                contentDescription = "Next Month",
                                                tint = AppConfig.textDark
                                            )
                                        }
                                    }

                                    // Weekday headers
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceAround
                                    ) {
                                        listOf("M", "T", "W", "T", "F", "S", "S").forEachIndexed { index, day ->
                                            Text(
                                                text = day,
                                                color = if (index == 6) AppConfig.accentColor else AppConfig.textDark,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.width(32.dp),
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }

                                    // Days Grid calculation
                                    val daysInMonth = when (currentMonthNumber) {
                                        1, 3, 5, 7, 8, 10, 12 -> 31
                                        4, 6, 9, 11 -> 30
                                        2 -> if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) 29 else 28
                                        else -> 31
                                    }
                                    val startOffset = (monthIndex * 2) % 7
                                    val totalCells = ((startOffset + daysInMonth + 6) / 7) * 7

                                    val daysList = (1..totalCells).map { cellIndex ->
                                        val dayNum = cellIndex - startOffset
                                        if (dayNum in 1..daysInMonth) dayNum else null
                                    }

                                    daysList.chunked(7).forEach { week ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceAround
                                        ) {
                                            week.forEach { day ->
                                                if (day != null) {
                                                    val isToday = year == realYear && currentMonthNumber == realMonth && day == realDay
                                                    val dayHasEvent = monthEvents.any { it.day == day }

                                                    val cellBg = when {
                                                        isToday -> AppConfig.accentColor
                                                        hasEventsInMonth && dayHasEvent -> AppConfig.primaryColor
                                                        else -> Color.Transparent
                                                    }
                                                    val textColor = when {
                                                        isToday || (hasEventsInMonth && dayHasEvent) -> Color.White
                                                        else -> AppConfig.textDark
                                                    }
                                                    val cellBorder = if (!isToday && !(hasEventsInMonth && dayHasEvent)) {
                                                        Modifier.border(1.dp, Color.Gray.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                                    } else {
                                                        Modifier
                                                    }

                                                    Box(
                                                        modifier = Modifier
                                                            .size(32.dp)
                                                            .background(cellBg, RoundedCornerShape(8.dp))
                                                            .then(cellBorder),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Text(
                                                            text = day.toString(),
                                                            color = textColor,
                                                            fontSize = 11.sp,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                } else {
                                                    Spacer(modifier = Modifier.size(32.dp))
                                                }
                                            }
                                        }
                                    }

                                    // Legend
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 4.dp),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        LegendItem(color = AppConfig.accentColor, label = "TODAY'S DATE")
                                        Spacer(modifier = Modifier.width(12.dp))
                                        LegendItem(color = AppConfig.primaryColor, label = "HAS EVENT")
                                        Spacer(modifier = Modifier.width(12.dp))
                                        LegendItem(color = Color.Transparent, borderColor = Color.Gray, label = "FREE DAY")
                                    }
                                }
                            }

                            // 2. UPCOMING EVENT SECTION
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(AppConfig.surfaceWhite, RoundedCornerShape(20.dp))
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "UPCOMING EVENT",
                                        color = AppConfig.textDark,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(start = 2.dp)
                                    )
                                    // ADD NEW button hugging both width and height on the same row on the right
                                    Box(
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .background(AppConfig.accentColor, RoundedCornerShape(6.dp))
                                            .border(1.dp, Color.Black, RoundedCornerShape(6.dp))
                                            .padding(horizontal = 8.dp, vertical = 3.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.Add,
                                                contentDescription = "Add New",
                                                tint = Color.Black,
                                                modifier = Modifier.size(11.dp)
                                            )
                                            Text(
                                                text = "ADD NEW",
                                                color = Color.Black,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }

                                Column(
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    if (monthEvents.isNotEmpty()) {
                                        monthEvents.forEach { ev ->
                                            val monthStr = when (ev.month) {
                                                1 -> "JAN"
                                                2 -> "FEB"
                                                3 -> "MAR"
                                                4 -> "APR"
                                                5 -> "MAY"
                                                6 -> "JUN"
                                                7 -> "JUL"
                                                8 -> "AUG"
                                                9 -> "SEP"
                                                10 -> "OCT"
                                                11 -> "NOV"
                                                else -> "DEC"
                                            }
                                            val yearShort = (ev.year % 100).toString()
                                            CalendarEventItem(
                                                day = ev.day.toString().padStart(2, '0'),
                                                monthYear = "$monthStr/$yearShort",
                                                title = ev.title,
                                                amount = ev.amount,
                                                stripeColor = AppConfig.buttonBaseColor
                                            )
                                        }
                                    } else {
                                        // Empty state when no upcoming events in the month (Free day month view)
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 16.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "No upcoming events in this month (Free Day)",
                                                color = AppConfig.textMuted,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Inner Shadow with top corner radius
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(18.dp)
                                .clip(RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp))
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black.copy(alpha = 0.25f),
                                            Color.Transparent
                                        )
                                    )
                                )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppConfig.primaryColor)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            val path = Path().apply {
                moveTo(size.width * 0.4f, size.height * 0.1f)
                quadraticTo(size.width * 0.7f, size.height * 0.3f, size.width * 0.85f, size.height * 0.7f)
                quadraticTo(size.width * 0.6f, size.height * 0.9f, size.width * 0.4f, size.height * 0.8f)
                close()
            }
            drawPath(
                path = path,
                color = Color.White.copy(alpha = 0.03f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Profile Photo",
                    tint = Color.White,
                    modifier = Modifier.size(38.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "CALENDAR",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "VIEW UPCOMING EVENTS",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Box(
                modifier = Modifier
                    .border(1.dp, Color.White.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 10.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "LOGOUT",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Composable
fun LegendItem(color: Color, borderColor: Color = Color.Transparent, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape)
                .then(if (borderColor != Color.Transparent) Modifier.border(1.dp, borderColor, CircleShape) else Modifier)
        )
        Text(
            text = label,
            color = AppConfig.textMuted,
            fontSize = 7.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CalendarEventItem(day: String, monthYear: String, title: String, amount: String, stripeColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(AppConfig.eventCardBg, RoundedCornerShape(8.dp))
            .padding(vertical = 6.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Date block on left - made wider (42.dp) so NOV/26 fits in 1 line
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(42.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = day,
                    color = AppConfig.textDark,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = monthYear,
                    color = AppConfig.textMuted,
                    fontSize = 7.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Visible
                )
            }

            // Left vertical stripe filling height
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .fillMaxHeight()
                    .background(stripeColor, RoundedCornerShape(1.5.dp))
            )

            // Title (constrained width to force 2-3 lines wrapping, filling height vertically centered)
            Text(
                text = title,
                color = AppConfig.textDark,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .width(135.dp) // Shorter title width to force 2-3 lines wrapping
                    .fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        }

        // Right side: Wider EDIT button with 1dp black outline & Amount
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .widthIn(min = 52.dp)
                    .background(AppConfig.buttonBaseColor, RoundedCornerShape(4.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "Edit",
                        tint = Color.White,
                        modifier = Modifier.size(9.dp)
                    )
                    Text(
                        text = "EDIT",
                        color = Color.White,
                        fontSize = 7.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = amount,
                color = AppConfig.textDark,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
fun CalendarScreenPreview() {
    FINATheme {
        CalendarScreen()
    }
}
