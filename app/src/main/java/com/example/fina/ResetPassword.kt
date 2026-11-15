package com.example.fina

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Undo
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fina.ui.theme.FINATheme

@Composable
fun ResetPasswordScreen(
    onBack: () -> Unit = {},
    onResetSuccess: () -> Unit = {}
) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val passwordsMatch = newPassword == confirmPassword
    val showMismatchError = confirmPassword.isNotEmpty() && !passwordsMatch

    BackHandler {
        onBack()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppConfig.secondaryColor)
    ) {
        // Top wave background matching design
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(440.dp)
                .background(AppConfig.primaryColor)
        ) {
            androidx.compose.foundation.Image(
                painter = painterResource(id = R.drawable.img_wave_bg),
                contentDescription = "Background Wave",
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = 150.dp),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Icon(
                    painter = painterResource(id = R.drawable.ic_logo_simple_white),
                    contentDescription = "Fina Logo",
                    tint = AppConfig.secondaryColor,
                    modifier = Modifier.size(50.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "RESET",
                    color = AppConfig.secondaryColor,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )

                Text(
                    text = "PASSWORD",
                    color = AppConfig.secondaryColor,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
        }

        // Lower form content area
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(290.dp))

                // BACK button
                Row(
                    modifier = Modifier
                        .clickable { onBack() }
                        .background(Color.Transparent)
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Undo,
                        contentDescription = "Back",
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "BACK",
                        color = Color.Black,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // NEW PASSWORD Field
                ResetFloatingLabelField(
                    label = "NEW PASSWORD",
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    passwordVisible = newPasswordVisible,
                    onTogglePasswordVisibility = { newPasswordVisible = !newPasswordVisible }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // CONFIRM NEW PASSWORD Field
                ResetFloatingLabelField(
                    label = "CONFIRM NEW PASSWORD",
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    passwordVisible = confirmPasswordVisible,
                    onTogglePasswordVisibility = { confirmPasswordVisible = !confirmPasswordVisible },
                    isError = showMismatchError,
                    errorMessage = if (showMismatchError) "PASSWORD DOES NOT MATCH" else null
                )

                Spacer(modifier = Modifier.height(32.dp))

                // RESET Button
                Button(
                    onClick = {
                        if (newPassword.isNotEmpty() && passwordsMatch) {
                            Toast.makeText(context, "Password reset successfully!", Toast.LENGTH_SHORT).show()
                            onResetSuccess()
                        } else {
                            Toast.makeText(context, "Please ensure passwords match and are not empty.", Toast.LENGTH_LONG).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppConfig.accentColor),
                    border = BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
                            contentDescription = "Reset",
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "RESET",
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Version Footer
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = AppConfig.VERSION,
                    color = Color.Black.copy(alpha = 0.5f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun ResetFloatingLabelField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    passwordVisible: Boolean = false,
    onTogglePasswordVisibility: () -> Unit = {},
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val isExpanded = isFocused || value.isNotEmpty()

    val labelOffsetY by animateDpAsState(
        targetValue = if (isExpanded) 0.dp else 20.dp,
        label = "labelOffsetY"
    )
    val labelFontSize by animateFloatAsState(
        targetValue = if (isExpanded) 8.5f else 12.5f,
        label = "labelFontSize"
    )

    val textColor = if (isError) Color.Red else Color.Black

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    focusRequester.requestFocus()
                }
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = label,
                color = textColor.copy(alpha = if (isExpanded) 0.7f else 0.5f),
                fontSize = labelFontSize.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(y = labelOffsetY)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    textStyle = TextStyle(
                        color = textColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    cursorBrush = SolidColor(textColor),
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester)
                        .onFocusChanged { isFocused = it.isFocused }
                )

                IconButton(
                    onClick = onTogglePasswordVisibility,
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                        contentDescription = "Toggle Password Visibility",
                        tint = textColor.copy(alpha = 0.8f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = textColor.copy(alpha = 0.5f)
        )

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
fun ResetPasswordScreenPreview() {
    FINATheme {
        ResetPasswordScreen()
    }
}
