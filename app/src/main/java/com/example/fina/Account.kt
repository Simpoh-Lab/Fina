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
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.fina.ui.theme.FINATheme

@Composable
fun AccountScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppConfig.primaryColor)
    ) {
        AccountsTopBar()

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
                            // TOP ROW: Your Accounts (Left) & Donut Chart Card (Right) aligned in height (IntrinsicSize.Min)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Min),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Left: Your Accounts card (scrollable account listing)
                                Column(
                                    modifier = Modifier
                                        .weight(0.42f)
                                        .fillMaxHeight()
                                        .background(AppConfig.surfaceWhite, RoundedCornerShape(16.dp))
                                        .padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "YOUR ACCOUNTS",
                                        color = AppConfig.textDark,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(bottom = 2.dp)
                                    )
                                    Column(
                                        modifier = Modifier
                                            .heightIn(max = 160.dp)
                                            .verticalScroll(rememberScrollState()),
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        AccountItemRow("MULTIPLIER", "012-345678-9", "dbs.com.sg", isSelected = true)
                                        AccountItemRow("BONUSSAVER", "01-23-45578-9", "sc.com", isSelected = false)
                                        AccountItemRow("SAVE AS YOU EARN", "123-45678-9", "posb.com.sg", isSelected = false)
                                        AccountItemRow("ONE ACCOUNT", "01-123-456-7", "uob.com.sg", isSelected = false)
                                        AccountItemRow("360 ACCOUNT", "001-123456-001", "ocbc.com", isSelected = false)
                                    }
                                }

                                // Right: Donut Chart with -10dp line gap for texts and visible 0-gap demographic legend
                                Column(
                                    modifier = Modifier
                                        .weight(0.58f)
                                        .fillMaxHeight()
                                        .background(AppConfig.surfaceWhite, RoundedCornerShape(16.dp))
                                        .padding(10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Box(
                                        modifier = Modifier.size(120.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        DonutChart(categories = AppConfig.expenseCategories, modifier = Modifier.fillMaxSize())
                                        // -10 dp line gap for donut chart texts
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy((-10).dp)
                                        ) {
                                            Text(
                                                text = "$1234.56",
                                                color = AppConfig.textDark,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Black
                                            )
                                            Text(
                                                text = "Remark",
                                                color = AppConfig.textMuted,
                                                fontSize = 8.sp,
                                                fontStyle = FontStyle.Italic
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))

                                    // Demographic legend with 0 line gap and fully visible texts
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(0.dp)
                                    ) {
                                        AppConfig.expenseCategories.chunked(3).forEach { rowItems ->
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                rowItems.forEach { category ->
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                                                        modifier = Modifier.padding(vertical = 1.dp)
                                                    ) {
                                                        Box(
                                                            modifier = Modifier
                                                                .size(5.dp)
                                                                .clip(CircleShape)
                                                                .background(category.color)
                                                        )
                                                        Text(
                                                            text = category.name.take(7),
                                                            color = AppConfig.textDark,
                                                            fontSize = 6.5.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            maxLines = 1,
                                                            overflow = TextOverflow.Ellipsis
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            // MIDDLE ROW: Selected Account Card (Left) & Actions (Right) aligned in height (IntrinsicSize.Min)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Min),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Left: Selected Account Card filling height to match actions card
                                Row(
                                    modifier = Modifier
                                        .weight(0.42f)
                                        .fillMaxHeight()
                                        .background(AppConfig.surfaceWhite, RoundedCornerShape(16.dp))
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    AsyncImage(
                                        model = "https://www.google.com/s2/favicons?domain=dbs.com.sg&sz=128",
                                        contentDescription = "Bank Logo",
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                    )
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy((-2).dp)
                                    ) {
                                        Text(
                                            text = "MULTIPLIER",
                                            color = AppConfig.textDark,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "012-345678-9",
                                            color = AppConfig.textMuted,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }

                                // Right: Actions with 1dp black outline & readable action button texts across all screen sizes
                                Column(
                                    modifier = Modifier
                                        .weight(0.58f)
                                        .fillMaxHeight()
                                        .background(AppConfig.surfaceWhite, RoundedCornerShape(16.dp))
                                        .padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "ACTIONS",
                                        color = AppConfig.textDark,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        ActionButtonCustom("SHOW", Icons.Rounded.Visibility, AppConfig.buttonBaseColor, Color.White, modifier = Modifier.weight(1f))
                                        ActionButtonCustom("QR", Icons.Rounded.QrCode, AppConfig.buttonBaseColor, Color.White, modifier = Modifier.weight(1f))
                                        ActionButtonCustom("EDIT", Icons.Rounded.Edit, Color(0xFFFF7F50), Color.Black, modifier = Modifier.weight(1f))
                                    }
                                }
                            }

                            // BOTTOM SECTION: LINKED ACTIVITIES (text aligned with records below)
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(AppConfig.surfaceWhite, RoundedCornerShape(20.dp))
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = "LINKED ACTIVITIES",
                                    color = AppConfig.textDark,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
                                )

                                Column(
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    val extendedActivities = AppConfig.activities + AppConfig.activities.take(3)
                                    extendedActivities.forEach { activity ->
                                        ActivityRowItem(item = activity)
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
fun AccountsTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppConfig.primaryColor)
    ) {
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
                    text = "ACCOUNTS",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "MANAGE BANKS AND EWALLETS",
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
fun AccountItemRow(title: String, subtitle: String, domain: String, isSelected: Boolean) {
    val borderModifier = if (isSelected) {
        Modifier.border(1.5.dp, AppConfig.selectedOptionColor, RoundedCornerShape(8.dp))
    } else {
        Modifier
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppConfig.eventCardBg, RoundedCornerShape(8.dp))
            .then(borderModifier)
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        AsyncImage(
            model = "https://www.google.com/s2/favicons?domain=$domain&sz=128",
            contentDescription = "Bank Logo",
            modifier = Modifier
                .size(16.dp)
                .clip(RoundedCornerShape(3.dp))
        )
        // Smaller gap between name and number (-4.dp)
        Column(
            verticalArrangement = Arrangement.spacedBy((-4).dp)
        ) {
            Text(
                text = title,
                color = AppConfig.textDark,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Text(
                text = subtitle,
                color = AppConfig.textMuted,
                fontSize = 7.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ActionButtonCustom(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    buttonColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(28.dp)
            .background(buttonColor, RoundedCornerShape(6.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(6.dp)) // 1dp outline black
            .clickable { }
            .padding(horizontal = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = contentColor,
            modifier = Modifier.size(11.dp)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = title,
            color = contentColor,
            fontSize = 8.sp, // Responsive and readable font size across all phone sizes
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
fun AccountScreenPreview() {
    FINATheme {
        AccountScreen()
    }
}
