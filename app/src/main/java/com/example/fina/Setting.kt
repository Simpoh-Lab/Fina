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
import androidx.compose.runtime.*
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
import com.example.fina.ui.theme.FINATheme

@Composable
fun SettingScreen() {
    var isEditing by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf(UserSettings.username) }
    var email by remember { mutableStateOf("admin@gmail.com") }
    var password by remember { mutableStateOf("*****************") }
    var backupPath by remember { mutableStateOf("/storage/emulated/0/...") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppConfig.primaryColor)
    ) {
        SettingTopBar()

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
                            // 1. Profile Header Card
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = AppConfig.surfaceWhite),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(72.dp)
                                            .background(AppConfig.textDark, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Person,
                                            contentDescription = "User Avatar",
                                            tint = AppConfig.secondaryColor,
                                            modifier = Modifier.size(48.dp)
                                        )
                                    }

                                    Text(
                                        text = username,
                                        color = AppConfig.textDark,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Black
                                    )

                                    Text(
                                        text = "CREATED ON 10-JAN-2021",
                                        color = AppConfig.textMuted,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )
                                }
                            }

                            // 2. Editable Inputs Row (Avatar on left & Inputs on right)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Min),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Left Avatar box
                                Box(
                                    modifier = Modifier
                                        .weight(0.35f)
                                        .fillMaxHeight()
                                        .background(AppConfig.surfaceWhite, RoundedCornerShape(16.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Person,
                                        contentDescription = "Avatar",
                                        tint = AppConfig.textDark,
                                        modifier = Modifier.size(56.dp)
                                    )
                                }

                                // Right Inputs box
                                Column(
                                    modifier = Modifier
                                        .weight(0.65f)
                                        .fillMaxHeight()
                                        .background(AppConfig.surfaceWhite, RoundedCornerShape(16.dp))
                                        .padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    SettingInputField("USERNAME", username, isEnabled = isEditing) { username = it }
                                    SettingInputField("EMAIL", email, isEnabled = isEditing) { email = it }
                                    SettingInputField("PASSWORD", password, isEnabled = isEditing) { password = it }
                                    SettingPathField("BACKUP FILE PATH", backupPath, isEnabled = isEditing) { backupPath = it }
                                }
                            }

                            // 3. ACTIONS SECTION
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(AppConfig.surfaceWhite, RoundedCornerShape(20.dp))
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "ACTIONS",
                                    color = AppConfig.textDark,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(start = 2.dp)
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    // Edit Profile / Save Button
                                    val editButtonColor = if (isEditing) AppConfig.buttonBaseColor else AppConfig.accentColor
                                    val editTextColor = if (isEditing) Color.White else Color.Black
                                    val editIcon = if (isEditing) Icons.Rounded.Check else Icons.Rounded.Edit
                                    val editText = if (isEditing) "SAVE" else "EDIT PROFILE"

                                    SettingActionButton(
                                        title = editText,
                                        icon = editIcon,
                                        bgColor = editButtonColor,
                                        contentColor = editTextColor,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        isEditing = !isEditing
                                    }

                                    SettingActionButton(
                                        title = "BACKUP DATA",
                                        icon = Icons.Rounded.Backup,
                                        bgColor = AppConfig.buttonBaseColor,
                                        contentColor = Color.White,
                                        modifier = Modifier.weight(1f)
                                    ) { }

                                    SettingActionButton(
                                        title = "DELETE PROFILE",
                                        icon = Icons.Rounded.Delete,
                                        bgColor = Color(0xFFFF5252),
                                        contentColor = Color.White,
                                        modifier = Modifier.weight(1f)
                                    ) { }

                                    SettingActionButton(
                                        title = "UNLINK GOOGLE",
                                        icon = Icons.Rounded.LinkOff,
                                        bgColor = Color(0xFFFF5252),
                                        contentColor = Color.White,
                                        modifier = Modifier.weight(1f)
                                    ) { }
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
fun SettingTopBar() {
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
            horizontalArrangement = Arrangement.End, // Logout button on the right, left side left empty
            verticalAlignment = Alignment.Top
        ) {
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
fun SettingInputField(label: String, value: String, isEnabled: Boolean, onValueChanged: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppConfig.eventCardBg, RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy((-2).dp)
    ) {
        Text(
            text = label,
            color = AppConfig.textMuted,
            fontSize = 7.sp,
            fontWeight = FontWeight.Bold
        )
        if (isEnabled) {
            TextField(
                value = value,
                onValueChange = onValueChanged,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = LocalTextStyle.current.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold, color = AppConfig.textDark),
                modifier = Modifier.height(36.dp).fillMaxWidth()
            )
        } else {
            Text(
                text = value,
                color = AppConfig.textDark,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}

@Composable
fun SettingPathField(label: String, value: String, isEnabled: Boolean, onValueChanged: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppConfig.eventCardBg, RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy((-2).dp)
    ) {
        Text(
            text = label,
            color = AppConfig.textMuted,
            fontSize = 7.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isEnabled) {
                TextField(
                    value = value,
                    onValueChange = onValueChanged,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = LocalTextStyle.current.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold, color = AppConfig.textDark),
                    modifier = Modifier
                        .height(36.dp)
                        .weight(1f)
                )
            } else {
                Text(
                    text = value,
                    color = AppConfig.textDark,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
            }
            Icon(
                imageVector = Icons.Rounded.Folder,
                contentDescription = "Folder",
                tint = AppConfig.textDark,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
fun SettingActionButton(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    bgColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(55.dp)
            .background(bgColor, RoundedCornerShape(6.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .padding(horizontal = 2.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = contentColor,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = title,
                color = contentColor,
                fontSize = 7.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
fun SettingScreenPreview() {
    FINATheme {
        SettingScreen()
    }
}
