package com.example.fina

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.fina.ui.theme.FINATheme

@Composable
fun CardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppConfig.primaryColor)
    ) {
        CardsTopBar()

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
                            // 1. Anchor Card at the top is an image
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = AppConfig.surfaceWhite),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                AsyncImage(
                                    model = "https://images.unsplash.com/photo-1544816155-12df9643f363?w=800&auto=format&fit=crop&q=80",
                                    contentDescription = "Loyalty Card Banner",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                )
                            }

                            // 2. Selected Card Info & Actions Row
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Min),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Left: Card Info with same URL favicon as Account logo
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
                                        model = "https://www.google.com/s2/favicons?domain=itea.sg&sz=128",
                                        contentDescription = "Business Logo",
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                    )
                                    Column(verticalArrangement = Arrangement.spacedBy((-2).dp)) {
                                        Text(
                                            text = "iTea Loyalty",
                                            color = AppConfig.textDark,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "31-DEC-2027",
                                            color = AppConfig.textMuted,
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }

                                // Right: Actions (EDIT & DELETE)
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
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        ActionButtonCustom("EDIT", Icons.Rounded.Edit, AppConfig.buttonBaseColor, Color.White, modifier = Modifier.weight(1f))
                                        ActionButtonCustom("DELETE", Icons.Rounded.Delete, Color(0xFFFF5252), Color.White, modifier = Modifier.weight(1f))
                                    }
                                }
                            }

                            // 3. LINKED ACTIVITIES SECTION
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
                                        text = "LINKED ACTIVITIES",
                                        color = AppConfig.textDark,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(start = 2.dp)
                                    )
                                    // VIEW ALL button hugging both width and height of its inner contents on the same row on the right
                                    Box(
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .background(AppConfig.buttonBaseColor, RoundedCornerShape(6.dp))
                                            .border(1.dp, Color.Black, RoundedCornerShape(6.dp))
                                            .padding(horizontal = 8.dp, vertical = 3.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.Visibility,
                                                contentDescription = "View All",
                                                tint = Color.White,
                                                modifier = Modifier.size(11.dp)
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

                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    repeat(5) {
                                        ActivityRowItem(
                                            item = AppConfig.ActivityItem(
                                                title = "ITEA DRINK PURCHASE",
                                                date = "26-APR-2021",
                                                amount = "$12.34",
                                                categoryName = "FOOD & GROCERIES"
                                            )
                                        )
                                    }
                                }
                            }

                            // 4. VIEW OTHER CARDS SECTION
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
                                        text = "VIEW OTHER CARDS",
                                        color = AppConfig.textDark,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(start = 2.dp)
                                    )
                                    // ADD NEW button hugging both width and height of its inner contents on the same row on the right
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

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OtherCardThumbnail("IKEA Family", Color(0xFF0051BA), modifier = Modifier.weight(1f))
                                    OtherCardThumbnail("Starbucks", Color(0xFF006241), modifier = Modifier.weight(1f))
                                    OtherCardThumbnail("Smiles", Color(0xFFFF5722), modifier = Modifier.weight(1f))
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
fun CardsTopBar() {
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
                    text = "CARDS",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "MANAGE LOYALTY CARDS",
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
fun OtherCardThumbnail(title: String, bgColor: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(55.dp)
            .background(bgColor, RoundedCornerShape(8.dp))
            .padding(6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
fun CardScreenPreview() {
    FINATheme {
        CardScreen()
    }
}
