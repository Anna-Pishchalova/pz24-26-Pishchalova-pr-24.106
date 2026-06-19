package com.example.medicapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainDashboardScreen(
    userEmail: String = "",
    patientCard: Map<String, String>? = null,
    onLogout: () -> Unit = {}
) {
    // Состояние для пагинации (5 страниц)
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 5 } // 5 страниц: Анализы, Уведомления, Мониторинг, Результаты, Настройки
    )

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Верхняя панель с временем и кнопкой "Выйти"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
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
                onClick = onLogout,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "Выйти",
                    color = Color(0xFFFF6B6B),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // HorizontalPager для свайпа между страницами
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            userScrollEnabled = true,
            flingBehavior = PagerDefaults.flingBehavior(state = pagerState)
        ) { page ->
            when (page) {
                0 -> AnalyticsScreen(
                    onAnalysisClick = { analysis ->
                        // Переход к деталям анализа
                        println("Выбран анализ: ${analysis.name}")
                    },
                    onPromotionClick = { promotion ->
                        // Переход к акции
                        println("Выбрана акция: ${promotion.title}")
                    },
                    onCategoryClick = { category ->
                        // Фильтр по категории
                        println("Выбрана категория: $category")
                    },
                    onSearchClick = {
                        // Поиск анализов
                        println("Поиск анализов")
                    },
                    onBottomNavClick = { item ->
                        // Обработка нажатия на нижнюю навигацию
                        when (item) {
                            "Анализы" -> {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(0)
                                }
                            }
                            "Результаты" -> {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(3)
                                }
                            }
                            "Поддержка" -> {
                                // Переход на страницу поддержки
                                println("Переход в поддержку")
                            }
                            "Профиль" -> {
                                // Переход на страницу профиля
                                println("Переход в профиль")
                            }
                        }
                    }
                )
                1 -> DashboardPage2() // Уведомления
                2 -> DashboardPage3() // Мониторинг
                3 -> DashboardPage4() // Результаты
                4 -> DashboardPage5() // Настройки
            }
        }

        // Индикатор страниц (точки внизу) - показываем только если не на странице анализов
        // (на странице анализов есть своя нижняя навигация)
        if (pagerState.currentPage != 0) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(pagerState.pageCount) { index ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .size(if (isSelected) 12.dp else 8.dp)
                            .padding(horizontal = 4.dp)
                            .background(
                                color = if (isSelected) Color(0xFF4CAF50) else Color(0xFFE0E0E0),
                                shape = CircleShape
                            )
                            .clickable {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                    )
                }
            }
        }
    }
}

// ===== Страница 1: Уведомления =====
@Composable
fun DashboardPage2() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🔔 Уведомления",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, bottom = 8.dp)
        )

        Text(
            text = "Вы быстро узнаете о результатах",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF5F5F5)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                NotificationItem(
                    icon = "📊",
                    title = "Результаты анализов готовы",
                    description = "Ваши результаты анализов от 15.06.2026 готовы",
                    time = "2 часа назад",
                    isRead = false
                )

                Divider()

                NotificationItem(
                    icon = "📅",
                    title = "Напоминание о приеме",
                    description = "Завтра в 10:00 прием у терапевта",
                    time = "1 день назад",
                    isRead = true
                )

                Divider()

                NotificationItem(
                    icon = "💊",
                    title = "Пора принять лекарство",
                    description = "Не забудьте принять назначенные лекарства",
                    time = "2 дня назад",
                    isRead = true
                )

                Divider()

                NotificationItem(
                    icon = "🏥",
                    title = "Запись к врачу подтверждена",
                    description = "Ваша запись к кардиологу подтверждена",
                    time = "3 дня назад",
                    isRead = true
                )
            }
        }

        TextButton(
            onClick = { /* Очистка уведомлений */ },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "Очистить все уведомления",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun NotificationItem(
    icon: String,
    title: String,
    description: String,
    time: String,
    isRead: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Открыть уведомление */ }
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = if (isRead) Color(0xFFF5F5F5) else Color(0xFFE8F5E9),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = icon, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = if (isRead) FontWeight.Normal else FontWeight.Bold,
                    color = if (isRead) Color.Gray else Color.Black
                )
                Text(
                    text = time,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                fontSize = 13.sp,
                color = if (isRead) Color.Gray else Color(0xFF555555),
                maxLines = 2
            )
        }

        if (!isRead) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = Color(0xFF4CAF50),
                        shape = CircleShape
                    )
            )
        }
    }
}

// ===== Страница 2: Мониторинг =====
@Composable
fun DashboardPage3() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "📊 Мониторинг",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, bottom = 8.dp)
        )

        Text(
            text = "Наши врачи всегда наблюдают за вашими показателями здоровья",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            textAlign = TextAlign.Center
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE3F2FD)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "❤️ Показатели здоровья",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1565C0)
                )

                Spacer(modifier = Modifier.height(16.dp))

                HealthMetricItem(
                    icon = "💓",
                    label = "Артериальное давление",
                    value = "120/80",
                    status = "Норма",
                    statusColor = Color(0xFF4CAF50)
                )

                Divider()

                HealthMetricItem(
                    icon = "❤️",
                    label = "Пульс",
                    value = "72 уд/мин",
                    status = "Норма",
                    statusColor = Color(0xFF4CAF50)
                )

                Divider()

                HealthMetricItem(
                    icon = "⚖️",
                    label = "Вес",
                    value = "75 кг",
                    status = "В норме",
                    statusColor = Color(0xFFFF9800)
                )

                Divider()

                HealthMetricItem(
                    icon = "🩸",
                    label = "Уровень глюкозы",
                    value = "5.2 ммоль/л",
                    status = "Норма",
                    statusColor = Color(0xFF4CAF50)
                )
            }
        }

        Button(
            onClick = { /* Обновление данных */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Обновить показатели",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Последнее обновление: сегодня, 10:30",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun HealthMetricItem(
    icon: String,
    label: String,
    value: String,
    status: String,
    statusColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = icon, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = label,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = value,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        Box(
            modifier = Modifier
                .background(
                    color = statusColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(
                text = status,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = statusColor
            )
        }
    }
}

// ===== Страница 3: Результаты =====
@Composable
fun DashboardPage4() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "📊 Результаты анализов",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, bottom = 16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE3F2FD)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "📋 Последние результаты",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1565C0)
                )

                Spacer(modifier = Modifier.height(16.dp))

                listOf(
                    "Общий анализ крови" to "12.06.2026",
                    "Биохимия" to "10.06.2026",
                    "Глюкоза" to "08.06.2026"
                ).forEach { (name, date) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(name, fontSize = 14.sp)
                        Text(date, fontSize = 14.sp, color = Color.Gray)
                    }
                    Divider()
                }
            }
        }
    }
}

// ===== Страница 4: Настройки =====
@Composable
fun DashboardPage5() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "⚙️ Настройки",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, bottom = 16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFF3E0)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                SettingsItem(icon = "🔔", title = "Уведомления")
                Divider()
                SettingsItem(icon = "🔒", title = "Конфиденциальность")
                Divider()
                SettingsItem(icon = "🌙", title = "Темная тема")
                Divider()
                SettingsItem(icon = "❓", title = "Помощь")
            }
        }
    }
}

@Composable
fun SettingsItem(icon: String, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Обработка нажатия */ }
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, fontSize = 16.sp)
        }
        Text(text = "›", fontSize = 20.sp, color = Color.Gray)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainDashboardScreen() {
    MainDashboardScreen(
        userEmail = "test@mail.ru",
        patientCard = mapOf(
            "firstName" to "Эдуард",
            "middleName" to "Олегович",
            "lastName" to "Тицкий",
            "birthDate" to "28 февраля 1991",
            "gender" to "Мужской"
        )
    )
}