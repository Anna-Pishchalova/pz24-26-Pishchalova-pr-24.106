package com.example.medicapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import kotlinx.coroutines.delay

@Composable
fun VerificationCodeScreen(
    email: String = "example@mail.ru",
    onCodeComplete: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var code by remember { mutableStateOf("") }

    var timeLeft by remember { mutableStateOf(59) }
    var isTimerActive by remember { mutableStateOf(true) }

    LaunchedEffect(isTimerActive) {
        while (isTimerActive && timeLeft > 0) {
            delay(1000)
            timeLeft--
        }
        if (timeLeft == 0) {
            isTimerActive = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "9:41",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 40.dp)
        )

        Text(
            text = "Введите код из E-mail",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Text(
            text = if (isTimerActive) {
                "Отправить код повторно можно будет через $timeLeft секунд"
            } else {
                "Введите код, отправленный на $email"
            },
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = code,
            onValueChange = { newValue: String ->
                if (newValue.length <= 4 && newValue.all { it.isDigit() }) {
                    code = newValue
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(bottom = 40.dp),
            placeholder = {
                Text(
                    text = "_ _ _ _", fontSize = 24.sp,
                    letterSpacing = 8.sp
                )
            },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            textAlign = TextAlign.Center,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF4CAF50),
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedLabelColor = Color(0xFF4CAF50)
            ),
            maxLines = 1,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 16.sp
        )

        val isCodeComplete = code.length == 4

        Button(
            onClick = {
                if (isCodeComplete) {
                    onCodeComplete(code)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isCodeComplete) Color(0xFF4CAF50) else Color(0xFFE0E0E0),
                contentColor = if (isCodeComplete) Color.White else Color.Gray
            ),
            enabled = isCodeComplete
        ) {
            Text(
                text = "Подтвердить",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = {
                timeLeft = 59
                isTimerActive = true
            },
            enabled = !isTimerActive
        ) {
            Text(
                text = "Отправить код повторно",
                color = if (!isTimerActive) Color(0xFF4CAF50) else Color.Gray,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(
            onClick = onBackClick,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(
                text = "← Назад",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewVerificationCodeScreen() {
    VerificationCodeScreen()
}