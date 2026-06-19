// MainActivity.kt
package com.example.medicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.medicapp.ui.theme.MedicAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    // Переменные для управления экранами
    var currentScreen by remember { mutableStateOf("splash") }
    var userEmail by remember { mutableStateOf("") }
    var userPin by remember { mutableStateOf("") }
    var patientCard by remember { mutableStateOf<Map<String, String>?>(null) }

    // "splash" - заставка
    // "login" - экран входа
    // "verification" - экран ввода кода
    // "createPin" - экран создания PIN-кода
    // "createCard" - экран создания карты пациента
    // "dashboard" - главный экран

    when (currentScreen) {
        "splash" -> {
            SplashScreen(
                onLoginClick = {
                    currentScreen = "login"
                }
            )
        }
        "login" -> {
            RegistrationScreen(
                onBackClick = {
                    currentScreen = "splash"
                }
            )
        }
        "verification" -> {
            VerificationCodeScreen(
                email = userEmail,
                onCodeComplete = { code ->
                    println("Код подтверждения: $code")
                    currentScreen = "createPin"
                },
                onBackClick = {
                    currentScreen = "login"
                }
            )
        }
        "createPin" -> {
            CreatePinScreen(
                onPinCreated = { pin ->
                    userPin = pin
                    println("Создан PIN: $pin")
                    currentScreen = "createCard" // Переход на создание карты
                },
                onSkip = {
                    currentScreen = "createCard" // Тоже переходим на создание карты
                }
            )
        }
        "createCard" -> {
            CreatePatientCardScreen(
                onCardCreated = { cardData ->
                    patientCard = cardData
                    println("Создана карта: $cardData")
                    currentScreen = "dashboard"
                },
                onSkip = {
                    currentScreen = "dashboard" // Пропускаем создание карты
                }
            )
        }
    }
}