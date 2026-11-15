package com.example.fina

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fina.ui.theme.FINATheme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Settings

import androidx.compose.material.icons.outlined.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FINATheme {
                MainAppScreen()
            }
        }
    }
}

enum class AppScreen {
    HOME, ACCOUNTS, CARDS, CALENDAR, SETTINGS, SIGNIN, SIGNUP, RESET_PASSWORD
}

@Composable
fun MainAppScreen() {
    // Default to signin on app launch
    var currentScreen by remember { mutableStateOf(AppScreen.SIGNIN) }

    Scaffold(
        bottomBar = {
            if (currentScreen != AppScreen.SIGNIN && currentScreen != AppScreen.SIGNUP && currentScreen != AppScreen.RESET_PASSWORD) {
                SingleSourceBottomBar(
                    selectedScreen = currentScreen,
                    onScreenSelected = { currentScreen = it },
                )
            }
        },
        containerColor = AppConfig.primaryColor,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(if (currentScreen == AppScreen.SIGNIN || currentScreen == AppScreen.SIGNUP || currentScreen == AppScreen.RESET_PASSWORD) PaddingValues(0.dp) else innerPadding),
        ) {
            when (currentScreen) {
                AppScreen.HOME -> HomeScreen()
                AppScreen.ACCOUNTS -> AccountScreen()
                AppScreen.CARDS -> CardScreen()
                AppScreen.CALENDAR -> CalendarScreen()
                AppScreen.SETTINGS -> SettingScreen()
                AppScreen.SIGNIN -> SigninScreen(
                    onSignIn = { currentScreen = AppScreen.HOME },
                    onNavigateToSignup = { currentScreen = AppScreen.SIGNUP },
                    onNavigateToResetPassword = { currentScreen = AppScreen.RESET_PASSWORD }
                )
                AppScreen.SIGNUP -> SignupScreen(
                    onBack = { currentScreen = AppScreen.SIGNIN },
                    onRegister = { currentScreen = AppScreen.HOME }
                )
                AppScreen.RESET_PASSWORD -> ResetPasswordScreen(
                    onBack = { currentScreen = AppScreen.SIGNIN },
                    onResetSuccess = { currentScreen = AppScreen.SIGNIN }
                )
            }
        }
    }
}

@Composable
fun SingleSourceBottomBar(
    selectedScreen: AppScreen,
    onScreenSelected: (AppScreen) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .background(AppConfig.primaryColor)
            .padding(horizontal = 24.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home Icon
            IconButton(onClick = { onScreenSelected(AppScreen.HOME) }, modifier = Modifier.size(32.dp)) {
                Icon(
                    imageVector = if (selectedScreen == AppScreen.HOME) Icons.Rounded.Home else Icons.Outlined.Home,
                    contentDescription = "Home",
                    tint = if (selectedScreen == AppScreen.HOME) AppConfig.secondaryColor else AppConfig.secondaryColor.copy(alpha = 0.4f),
                    modifier = Modifier.size(24.dp)
                )
            }
            // Bank Icon
            IconButton(onClick = { onScreenSelected(AppScreen.ACCOUNTS) }, modifier = Modifier.size(32.dp)) {
                Icon(
                    imageVector = if (selectedScreen == AppScreen.ACCOUNTS) Icons.Rounded.AccountBalance else Icons.Outlined.AccountBalance,
                    contentDescription = "Bank",
                    tint = if (selectedScreen == AppScreen.ACCOUNTS) AppConfig.secondaryColor else AppConfig.secondaryColor.copy(alpha = 0.4f),
                    modifier = Modifier.size(24.dp)
                )
            }
            // Card Icon
            IconButton(onClick = { onScreenSelected(AppScreen.CARDS) }, modifier = Modifier.size(32.dp)) {
                Icon(
                    imageVector = if (selectedScreen == AppScreen.CARDS) Icons.Rounded.CreditCard else Icons.Outlined.CreditCard,
                    contentDescription = "Card",
                    tint = if (selectedScreen == AppScreen.CARDS) AppConfig.secondaryColor else AppConfig.secondaryColor.copy(alpha = 0.4f),
                    modifier = Modifier.size(24.dp)
                )
            }
            // Calendar Icon
            IconButton(onClick = { onScreenSelected(AppScreen.CALENDAR) }, modifier = Modifier.size(32.dp)) {
                Icon(
                    imageVector = if (selectedScreen == AppScreen.CALENDAR) Icons.Rounded.CalendarMonth else Icons.Outlined.CalendarMonth,
                    contentDescription = "Calendar",
                    tint = if (selectedScreen == AppScreen.CALENDAR) AppConfig.secondaryColor else AppConfig.secondaryColor.copy(alpha = 0.4f),
                    modifier = Modifier.size(24.dp)
                )
            }
            // Settings Icon
            IconButton(onClick = { onScreenSelected(AppScreen.SETTINGS) }, modifier = Modifier.size(32.dp)) {
                Icon(
                    imageVector = if (selectedScreen == AppScreen.SETTINGS) Icons.Rounded.Settings else Icons.Outlined.Settings,
                    contentDescription = "Settings",
                    tint = if (selectedScreen == AppScreen.SETTINGS) AppConfig.secondaryColor else AppConfig.secondaryColor.copy(alpha = 0.4f),
                    modifier = Modifier.size(24.dp)
                )
            }
        }

Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = AppConfig.VERSION,
            color = AppConfig.secondaryColor.copy(alpha = 0.7f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 800)
@Composable
fun MainAppPreview() {
    FINATheme {
        MainAppScreen()
    }
}
