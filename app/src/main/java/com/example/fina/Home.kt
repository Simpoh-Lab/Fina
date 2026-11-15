package com.example.fina

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.HelpOutline
import androidx.compose.material.icons.automirrored.rounded.OpenInNew
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fina.ui.theme.FINATheme
import java.util.Calendar

@Composable
fun HomeScreen() {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val dynamicGreeting = when (currentHour) {
        in 0..11 -> "GOOD MORNING,"
        in 12..18 -> "GOOD AFTERNOON,"
        else -> "GOOD EVENING,"
    }

    // Locked top and bottom, only middle is scrollable
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppConfig.primaryColor)
    ) {
        // Locked Top Bar
        TopBarSection(dynamicGreeting = dynamicGreeting)

        // Scrollable Middle Card Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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
                        // DEMOGRAPHIC / MONTHLY EXPENSE CARD
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .background(AppConfig.surfaceWhite, RoundedCornerShape(24.dp))
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = "MONTHLY\nEXPENSE",
                                            color = AppConfig.textDark,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Black,
                                            lineHeight = 22.sp
                                        )
                                        Text(
                                            text = AppConfig.EXPENSE_PERIOD,
                                            color = AppConfig.textMuted,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }

                                    Column(
                                        verticalArrangement = Arrangement.spacedBy((-12).dp)
                                    ) {
                                        AppConfig.expenseCategories.forEach { category ->
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                modifier = Modifier.padding(vertical = 2.dp)
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(10.dp)
                                                        .clip(CircleShape)
                                                        .background(category.color)
                                                )
                                                Text(
                                                    text = category.name,
                                                    color = AppConfig.textDark,
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                        }
                                    }
                                }

                                DonutChart(
                                    categories = AppConfig.expenseCategories,
                                    modifier = Modifier.size(150.dp)
                                )
                            }
                        }

                        // MIDDLE SECTION: Two Columns (Quick actions card and activity card top/bottom aligned with IntrinsicSize.Min)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Left Column: Quick Action card filling height to match activity card
                            Column(
                                modifier = Modifier
                                    .weight(0.7f)
                                    .fillMaxHeight()
                                    .background(AppConfig.surfaceWhite, RoundedCornerShape(16.dp))
                                    .padding(8.dp),
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                ActionButton(title = "MY QR", icon = Icons.Rounded.QrCodeScanner, buttonColor = AppConfig.buttonBaseColor)
                                ActionButton(title = "INVEST", icon = Icons.AutoMirrored.Rounded.TrendingUp, buttonColor = AppConfig.buttonBaseColor)
                                ActionButton(title = "ALERT", icon = Icons.Rounded.Notifications, buttonColor = AppConfig.buttonBaseColor)
                                ActionButton(title = "NONE", icon = Icons.AutoMirrored.Rounded.HelpOutline, buttonColor = AppConfig.buttonBaseColor)
                                ActionButton(title = "NONE", icon = Icons.AutoMirrored.Rounded.HelpOutline, buttonColor = AppConfig.buttonBaseColor)
                            }

                            // Right Column: Activity Section filling height
                            Column(
                                modifier = Modifier
                                    .weight(1.3f)
                                    .fillMaxHeight()
                                    .background(AppConfig.surfaceWhite, RoundedCornerShape(16.dp))
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "ACTIVITY",
                                    color = AppConfig.textDark,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )

                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    AppConfig.activities.take(3).forEach { activity ->
                                        ActivityRowItem(item = activity)
                                    }
                                }
                            }
                        }

                        // BOTTOM SECTION: UPCOMING EVENT
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(AppConfig.surfaceWhite, RoundedCornerShape(20.dp))
                                .padding(12.dp)
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
                                    fontWeight = FontWeight.Bold
                                )
                                // View All button with 1dp black outline
                                Box(
                                    modifier = Modifier
                                        .background(AppConfig.buttonBaseColor, RoundedCornerShape(12.dp))
                                        .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Visibility,
                                            contentDescription = "View All",
                                            tint = Color.White,
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Text(
                                            text = "VIEW ALL",
                                            color = Color.White,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Event Cards Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                AppConfig.upcomingEvents.forEach { event ->
                                    UpcomingEventCard(
                                        event = event,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }

                    // Inner Shadow only on the middle white/cream card clipped to top 2 corners
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

@Composable
fun TopBarSection(dynamicGreeting: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppConfig.primaryColor)
    ) {
        // Map background silhouette
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
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
                Text(
                    text = dynamicGreeting,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = UserSettings.username,
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
fun ActionButton(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, buttonColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(buttonColor, RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)) // 1dp black outline
            .clickable { }
            .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color.White,
            modifier = Modifier.size(14.dp)
        )
        Text(
            text = title,
            color = Color.White,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ActivityRowItem(item: AppConfig.ActivityItem) {
    val stripeColor = AppConfig.getCategoryColor(item.categoryName)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppConfig.eventCardBg, RoundedCornerShape(6.dp))
            .padding(vertical = 3.dp, horizontal = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Left vertical stripe matching the category color (shrunk individually)
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(22.dp)
                    .background(stripeColor, RoundedCornerShape(1.5.dp))
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy((-8).dp)
            ) {
                Text(
                    text = item.title,
                    color = AppConfig.textDark,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.date,
                    color = AppConfig.textMuted,
                    fontSize = 7.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Text(
            text = item.amount,
            color = AppConfig.textDark,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun UpcomingEventCard(event: AppConfig.UpcomingEvent, modifier: Modifier = Modifier) {
    val stripeColor = AppConfig.getCategoryColor(event.categoryName)
    Column(
        modifier = modifier
            .background(AppConfig.eventCardBg, RoundedCornerShape(12.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = event.day,
            color = AppConfig.textDark,
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center
        )
        Text(
            text = event.monthYear,
            color = AppConfig.textMuted,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(4.dp)
                .background(stripeColor, RoundedCornerShape(2.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(event.progress)
                    .height(4.dp)
                    .background(stripeColor, RoundedCornerShape(2.dp))
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = event.title,
            color = AppConfig.textDark,
            fontSize = 7.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun DonutChart(categories: List<AppConfig.ColoredCategory>, modifier: Modifier = Modifier) {
    val totalAmount = categories.sumOf { it.amount.toDouble() }.toFloat()

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 26.dp.toPx()
            val diameter = size.minDimension - strokeWidth
            val topLeft = Offset(
                (size.width - diameter) / 2f,
                (size.height - diameter) / 2f
            )
            val canvasSize = Size(diameter, diameter)

            var startAngle = -90f
            categories.forEach { category ->
                val sweepAngle = (category.amount / totalAmount) * 360f
                drawArc(
                    color = Color.Black.copy(alpha = 0.25f),
                    startAngle = startAngle,
                    sweepAngle = sweepAngle - 8f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = canvasSize,
                    style = Stroke(width = strokeWidth + 3.dp.toPx(), cap = StrokeCap.Round)
                )
                drawArc(
                    color = category.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle - 10f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = canvasSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
                startAngle += sweepAngle
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
fun HomeScreenPreview() {
    FINATheme {
        HomeScreen()
    }
}
