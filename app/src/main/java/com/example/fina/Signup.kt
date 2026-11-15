package com.example.fina

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.rounded.Email
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
import androidx.compose.ui.graphics.Path
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
fun SignupScreen(
    onBack: () -> Unit = {},
    onRegister: () -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    BackHandler {
        onBack()
    }

    // Root container with cream background for the lower section
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppConfig.secondaryColor)
    ) {
        // Top wave background extended down to BACK button alignment (~440.dp)
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
                    text = "SIGN UP",
                    color = AppConfig.secondaryColor,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )

                Text(
                    text = "FOR AN ACCOUNT",
                    color = AppConfig.secondaryColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
        }

        // Lower form content area with navigation bars padding
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
                Spacer(modifier = Modifier.height(290.dp)) // push below wave to BACK button alignment

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

                // USERNAME Field
                SignupFloatingLabelField(
                    label = "USERNAME",
                    value = username,
                    onValueChange = { username = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // EMAIL Field
                SignupFloatingLabelField(
                    label = "EMAIL",
                    value = email,
                    onValueChange = { email = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // PASSWORD Field
                SignupFloatingLabelField(
                    label = "PASSWORD",
                    value = password,
                    onValueChange = { password = it },
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onTogglePasswordVisibility = { passwordVisible = !passwordVisible }
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Action Buttons Row: REGISTER and LINK WITH GOOGLE
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // REGISTER Button (Orange)
                    Button(
                        onClick = {
                            if (AppConfig.isValidMajorEmail(email)) {
                                onRegister()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please enter a valid email with a major provider (e.g. gmail.com, outlook.com)",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppConfig.accentColor),
                        border = BorderStroke(1.dp, Color.Black),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Email,
                                contentDescription = "Register",
                                tint = Color.Black,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "REGISTER",
                                color = Color.Black,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // LINK WITH GOOGLE Button (Dark Green)
                    OutlinedButton(
                        onClick = {
                            if (AppConfig.isValidMajorEmail(email)) {
                                onRegister()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please enter a valid email with a major provider (e.g. gmail.com, outlook.com)",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        },
                        border = BorderStroke(1.dp, Color.Black),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFF2C5745)
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1.15f)
                            .height(44.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            GoogleIconForSignup(modifier = Modifier.size(15.dp))
                            Text(
                                text = "LINK WITH GOOGLE",
                                color = AppConfig.secondaryColor,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Version Footer matching Signin.kt alignment
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
fun SignupFloatingLabelField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onTogglePasswordVisibility: (() -> Unit)? = null
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
                color = Color.Black.copy(alpha = if (isExpanded) 0.7f else 0.5f),
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
                    visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                    keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions.Default,
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    cursorBrush = SolidColor(Color.Black),
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester)
                        .onFocusChanged { isFocused = it.isFocused }
                )

                if (isPassword && onTogglePasswordVisibility != null) {
                    IconButton(
                        onClick = onTogglePasswordVisibility,
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                            contentDescription = "Toggle Password Visibility",
                            tint = Color.Black.copy(alpha = 0.8f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color.Black.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun GoogleIconForSignup(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val color = AppConfig.secondaryColor

        val path = Path().apply {
            moveTo(w * 0.85f, h * 0.35f)
            lineTo(w * 0.52f, h * 0.35f)
            lineTo(w * 0.52f, h * 0.58f)
            lineTo(w * 0.72f, h * 0.58f)
            quadraticTo(w * 0.68f, h * 0.78f, w * 0.5f, h * 0.78f)
            quadraticTo(w * 0.3f, h * 0.78f, w * 0.3f, h * 0.5f)
            quadraticTo(w * 0.3f, h * 0.22f, w * 0.5f, h * 0.22f)
            quadraticTo(w * 0.64f, h * 0.22f, w * 0.73f, h * 0.31f)
            lineTo(w * 0.86f, h * 0.18f)
            quadraticTo(w * 0.72f, h * 0.04f, w * 0.5f, h * 0.04f)
            quadraticTo(w * 0.12f, h * 0.04f, w * 0.12f, h * 0.5f)
            quadraticTo(w * 0.12f, h * 0.96f, w * 0.5f, h * 0.96f)
            quadraticTo(w * 0.9f, h * 0.96f, w * 0.9f, h * 0.5f)
            quadraticTo(w * 0.9f, h * 0.42f, w * 0.85f, h * 0.35f)
            close()
        }
        drawPath(path = path, color = color)
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
fun SignupScreenPreview() {
    FINATheme {
        SignupScreen()
    }
}
