package com.example.medicapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CreatePinScreen(
    onPinCreated: (String) -> Unit = {},
    onSkip: () -> Unit = {}
) {
    // Переменная для хранения PIN-кода
    var pin by remember { mutableStateOf("") }

    // Состояние для отображения точек (скрытие цифр)
    val displayText = "*".repeat(pin.length) + "_".repeat(4 - pin.length)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Верхняя панель с временем и кнопкой "Пропустить"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "9:41",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            TextButton(
                onClick = onSkip,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "Пропустить",
                    color = Color(0xFF4CAF50),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(60.dp))

        // Заголовок
        Text(
            text = "Создайте пароль",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Center
        )

        // Подзаголовок
        Text(
            text = "Для защиты ваших персональных данных",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 48.dp),
            textAlign = TextAlign.Center
        )

        // Отображение PIN (4 точки или звездочки)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 48.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 4 кружка для PIN-кода
            repeat(4) { index ->
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(horizontal = 8.dp)
                        .background(
                            color = if (index < pin.length) Color(0xFF4CAF50) else Color(0xFFE0E0E0),
                            shape = CircleShape
                        )
                )
            }
        }

        // Цифровая клавиатура (как на скриншоте)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Ряд 1: 1, 2, 3
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PinKeyButton(number = "1") { pin = addDigit(pin, it) }
                PinKeyButton(number = "2") { pin = addDigit(pin, it) }
                PinKeyButton(number = "3") { pin = addDigit(pin, it) }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ряд 2: 4, 5, 6
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PinKeyButton(number = "4") { pin = addDigit(pin, it) }
                PinKeyButton(number = "5") { pin = addDigit(pin, it) }
                PinKeyButton(number = "6") { pin = addDigit(pin, it) }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ряд 3: 7, 8, 9
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PinKeyButton(number = "7") { pin = addDigit(pin, it) }
                PinKeyButton(number = "8") { pin = addDigit(pin, it) }
                PinKeyButton(number = "9") { pin = addDigit(pin, it) }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ряд 4: пусто, 0, удалить
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Пустое место слева (для выравнивания)
                Box(modifier = Modifier.size(56.dp))

                // Кнопка 0
                PinKeyButton(number = "0") { pin = addDigit(pin, it) }

                // Кнопка удаления (←)
                PinKeyButton(
                    number = "⌫",
                    onPinClick = {
                        if (pin.isNotEmpty()) {
                            pin = pin.dropLast(1)
                        }
                    }
                )
            }
        }

        // Проверяем, введен ли PIN
        LaunchedEffect(pin) {
            if (pin.length == 4) {
                onPinCreated(pin) // Автоматически переходим дальше
            }
        }
    }
}

// Функция для добавления цифры в PIN
fun addDigit(currentPin: String, digit: String): String {
    return if (currentPin.length < 4) currentPin + digit else currentPin
}

// Компонент кнопки цифровой клавиатуры
@Composable
fun PinKeyButton(
    number: String,
    onPinClick: (String) -> Unit
) {
    // Если это кнопка удаления, делаем ее меньше
    val isDelete = number == "⌫"

    Box(
        modifier = Modifier
            .size(if (isDelete) 48.dp else 56.dp)
            .clickable { onPinClick(number) }
            .background(
                color = Color.Transparent,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number,
            fontSize = if (isDelete) 24.sp else 32.sp,
            fontWeight = if (isDelete) FontWeight.Normal else FontWeight.Bold,
            color = if (isDelete) Color.Gray else Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreatePinScreen() {
    CreatePinScreen()
}