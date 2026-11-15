package com.example.fina

import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.rounded.Fingerprint
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fina.ui.theme.FINATheme

@Composable
fun SigninScreen(
    onSignIn: () -> Unit = {},
    onNavigateToSignup: () -> Unit = {},
    onNavigateToResetPassword: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppConfig.primaryColor)
    ) {
        // Background watermark silhouette + circle
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val path = Path().apply {
                moveTo(size.width * 0.15f, size.height * 0.60f)
                quadraticTo(size.width * 0.85f, size.height * 0.62f, size.width * 0.72f, size.height * 0.98f)
                quadraticTo(size.width * 0.20f, size.height * 0.96f, size.width * 0.15f, size.height * 0.60f)
                close()
            }
            drawPath(
                path = path,
                color = Color.White.copy(alpha = 0.04f)
            )

            // Bottom right subtle watermark circle
            drawCircle(
                color = Color.White.copy(alpha = 0.035f),
                radius = size.width * 0.06f,
                center = Offset(size.width * 0.85f, size.height * 0.92f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(90.dp))

                // Logo Icon
                Icon(
                    painter = painterResource(id = R.drawable.ic_logo_simple_white),
                    contentDescription = "Fina Logo",
                    tint = AppConfig.secondaryColor,
                    modifier = Modifier.size(56.dp)
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Title section
                Text(
                    text = "HELLO",
                    color = AppConfig.secondaryColor,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )

                Text(
                    text = "Sign into your Account",
                    color = AppConfig.secondaryColor.copy(alpha = 0.85f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Spacer(modifier = Modifier.height(36.dp))

                // EMAIL Field with Animated Floating Title
                FloatingLabelInputField(
                    label = "EMAIL",
                    value = email,
                    onValueChange = { email = it }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // PASSWORD Field with Animated Floating Title
                FloatingLabelInputField(
                    label = "PASSWORD",
                    value = password,
                    onValueChange = { password = it },
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onTogglePasswordVisibility = { passwordVisible = !passwordVisible }
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Forgot Password Link right-aligned
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "FORGOT PASSWORD ?",
                        color = AppConfig.secondaryColor.copy(alpha = 0.85f),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.clickable { onNavigateToResetPassword() }
                    )
                }

                Spacer(modifier = Modifier.height(36.dp))

                // SIGN IN OPTIONS Section
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "SIGN IN OPTIONS",
                        color = AppConfig.secondaryColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // EMAIL Button
                        Button(
                            onClick = {
                                if (AppConfig.isValidMajorEmail(email)) {
                                    onSignIn()
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
                                .height(42.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Email,
                                    contentDescription = "Email",
                                    tint = Color.Black,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "EMAIL",
                                    color = Color.Black,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // GOOGLE Button
                        OutlinedButton(
                            onClick = {
                                if (AppConfig.isValidMajorEmail(email)) {
                                    onSignIn()
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
                                .weight(1f)
                                .height(42.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                GoogleIcon(modifier = Modifier.size(15.dp))
                                Text(
                                    text = "GOOGLE",
                                    color = AppConfig.secondaryColor,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // BIOMETRICS Button
                        OutlinedButton(
                            onClick = {
                                if (AppConfig.isValidMajorEmail(email)) {
                                    onSignIn()
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
                                .height(42.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Fingerprint,
                                    contentDescription = "Biometrics",
                                    tint = AppConfig.secondaryColor,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "BIOMETRICS",
                                    color = AppConfig.secondaryColor,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Bottom Section: NEW USER ? + SIGN UP & Version
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "NEW USER ?",
                        color = AppConfig.secondaryColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Box(
                        modifier = Modifier
                            .background(Color(0xFF2C5745), RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                            .clickable { onNavigateToSignup() }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.PersonAdd,
                                contentDescription = "Sign Up",
                                tint = AppConfig.secondaryColor,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "SIGN UP",
                                color = AppConfig.secondaryColor,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }

                Text(
                    text = AppConfig.VERSION,
                    color = AppConfig.secondaryColor.copy(alpha = 0.7f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun FloatingLabelInputField(
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
                color = AppConfig.secondaryColor.copy(alpha = if (isExpanded) 0.7f else 0.5f),
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
                        color = AppConfig.secondaryColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    cursorBrush = SolidColor(AppConfig.secondaryColor),
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
                            tint = AppConfig.secondaryColor.copy(alpha = 0.8f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = AppConfig.secondaryColor.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun GoogleIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val color = AppConfig.secondaryColor

        // Draw Google "G" silhouette / icon
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
fun SigninScreenPreview() {
    FINATheme {
        SigninScreen()
    }
}
