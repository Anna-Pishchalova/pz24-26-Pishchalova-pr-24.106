package com.example.medicapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.Key
import java.text.SimpleDateFormat
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreatePatientCardScreen(
    onCardCreated: (Map<String, String>) -> Unit = {}, // Когда карта создана
    onSkip: () -> Unit = {} // Когда нажали "Пропустить"
) {
    // Переменные для всех полей
    var firstName by remember { mutableStateOf("") }
    var middleName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    // Для отображения выбора пола
    var showGenderDialog by remember { mutableStateOf(false) }
    val genderOptions = listOf("Мужской", "Женский")

    // Проверяем, заполнены ли все поля
    val isFormComplete = firstName.isNotEmpty() &&
            lastName.isNotEmpty() &&
            birthDate.isNotEmpty() &&
            gender.isNotEmpty()

    // Фокус для навигации между полями
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Верхняя панель с временем и кнопкой "Пропустить"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp),
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

        // Заголовок
        Text(
            text = "Создание карты пациента",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        // Описание
        Text(
            text = "Без карты пациента вы не сможете заказать анализы.\nВ картах пациентов будут храниться результаты анализов вас и ваших близких.",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            lineHeight = 20.sp
        )

        // Поля ввода
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Имя
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("Имя") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF4CAF50),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedLabelColor = Color(0xFF4CAF50)
                )
            )

            // Отчество
            OutlinedTextField(
                value = middleName,
                onValueChange = { middleName = it },
                label = { Text("Отчество") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF4CAF50),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedLabelColor = Color(0xFF4CAF50)
                )
            )

            // Фамилия
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Фамилия") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF4CAF50),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedLabelColor = Color(0xFF4CAF50)
                )
            )

            // Дата рождения
            OutlinedTextField(
                value = birthDate,
                onValueChange = { birthDate = it },
                label = { Text("Дата рождения") },
                placeholder = { Text("28 февраля 1991") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF4CAF50),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedLabelColor = Color(0xFF4CAF50)
                )
            )

            // Пол (выпадающий список)
            OutlinedTextField(
                value = gender,
                onValueChange = { },
                label = { Text("Пол") },
                placeholder = { Text("Выберите пол") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showGenderDialog = true },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Выбрать пол"
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF4CAF50),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedLabelColor = Color(0xFF4CAF50)
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Кнопка "Создать"
        Button(
            onClick = {
                if (isFormComplete) {
                    val cardData = mapOf(
                        "firstName" to firstName,
                        "middleName" to middleName,
                        "lastName" to lastName,
                        "birthDate" to birthDate,
                        "gender" to gender
                    )
                    onCardCreated(cardData)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isFormComplete) Color(0xFF4CAF50) else Color(0xFFE0E0E0),
                contentColor = if (isFormComplete) Color.White else Color.Gray
            ),
            enabled = isFormComplete
        ) {
            Text(
                text = "Создать",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }

    // Диалог выбора пола
    if (showGenderDialog) {
        AlertDialog(
            onDismissRequest = { showGenderDialog = false },
            title = { Text("Выберите пол") },
            text = {
                Column {
                    genderOptions.forEach { option ->
                        TextButton(
                            onClick = {
                                gender = option
                                showGenderDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = option,
                                fontSize = 16.sp,
                                color = if (gender == option) Color(0xFF4CAF50) else Color.Black,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                        Divider()
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showGenderDialog = false }
                ) {
                    Text("Отмена")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreatePatientCardScreen() {
    CreatePatientCardScreen()
}