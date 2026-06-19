package com.example.medicapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

// Модель данных для анализа
data class AnalysisItem(
    val id: Int,
    val name: String,
    val days: String,
    val price: String? = null,
    val isPopular: Boolean = false
)

// Модель для акции
data class PromotionItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val price: String,
    val color: Color
)

@Composable
fun AnalyticsScreen(
    onAnalysisClick: (AnalysisItem) -> Unit = {},
    onPromotionClick: (PromotionItem) -> Unit = {},
    onCategoryClick: (String) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onBottomNavClick: (String) -> Unit = {}
) {
    // Состояние поиска
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current

    // Данные для акций
    val promotions = listOf(
        PromotionItem(
            id = 1,
            title = "Чек-ап для мужчин",
            subtitle = "9 исследований",
            price = "8000 ₽",
            color = Color(0xFFE3F2FD)
        ),
        PromotionItem(
            id = 2,
            title = "Чек-ап для женщин",
            subtitle = "12 исследований",
            price = "9500 ₽",
            color = Color(0xFFFFF3E0)
        ),
        PromotionItem(
            id = 3,
            title = "Комплексный чек-ап",
            subtitle = "15 исследований",
            price = "12000 ₽",
            color = Color(0xFFE8F5E9)
        )
    )

    // Данные для категорий
    val categories = listOf("Популярные", "Covid", "Комплексные", "Гормоны", "Аллергия")

    // Данные для списка анализов
    val analysisList = listOf(
        AnalysisItem(
            id = 1,
            name = "ПЦР-тест на определение РНК коронавируса стандартный",
            days = "2 дня",
            price = "1800 ₽",
            isPopular = true
        ),
        AnalysisItem(
            id = 2,
            name = "Клинический анализ крови с лейкоцитарной формулировкой",
            days = "1 день",
            price = "690 ₽"
        ),
        AnalysisItem(
            id = 3,
            name = "Биохимический анализ крови, базовый",
            days = "1 день",
            price = "2440 ₽"
        ),
        AnalysisItem(
            id = 4,
            name = "СОЭ (венозная кровь)",
            days = "1 день",
            price = "350 ₽"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Верхняя панель с поиском
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Иконка поиска
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Поиск",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Поле поиска
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    if (it.text.isNotEmpty()) onSearchClick()
                },
                placeholder = {
                    Text(
                        text = "Искать анализы",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF4CAF50),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedLabelColor = Color(0xFF4CAF50)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }

        // Основной контент с вертикальной прокруткой
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp)
        ) {
            // ===== Блок "Акции и новости" (горизонтальный скролл) =====
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Акции и новости",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                // Горизонтальный скролл для акций
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(promotions) { promotion ->
                        PromotionCard(
                            promotion = promotion,
                            onClick = { onPromotionClick(promotion) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ===== Блок "Поддержка" =====
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clickable { /* Переход в поддержку */ },
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE8F5E9)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "💬 Поддержка",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF2E7D32)
                    )
                    Text(
                        text = "Комплексные →",
                        fontSize = 14.sp,
                        color = Color(0xFF4CAF50)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ===== Блок "Каталог анализов" =====
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Каталог анализов",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Смотреть все",
                        fontSize = 14.sp,
                        color = Color(0xFF4CAF50),
                        modifier = Modifier.clickable { /* Переход в каталог */ }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Горизонтальный скролл категорий
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { category ->
                        CategoryChip(
                            category = category,
                            isSelected = category == "Популярные",
                            onClick = { onCategoryClick(category) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ===== Список анализов (вертикальный скролл) =====
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                analysisList.forEach { analysis ->
                    AnalysisItemCard(
                        analysis = analysis,
                        onClick = { onAnalysisClick(analysis) }
                    )
                    if (analysis != analysisList.last()) {
                        Divider(
                            modifier = Modifier.padding(vertical = 4.dp),
                            color = Color(0xFFF5F5F5)
                        )
                    }
                }
            }
        }

        // ===== Нижняя навигация =====
        BottomNavigationBar(
            selectedItem = "Анализы",
            onItemClick = onBottomNavClick
        )
    }
}

// ===== Компонент: Карточка акции =====
@Composable
fun PromotionCard(
    promotion: PromotionItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = promotion.color
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = promotion.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = promotion.subtitle,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = promotion.price,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
        }
    }
}

// ===== Компонент: Чип категории =====
@Composable
fun CategoryChip(
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) Color(0xFF4CAF50) else Color(0xFFF5F5F5)
    ) {
        Text(
            text = category,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color.White else Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

// ===== Компонент: Карточка анализа =====
@Composable
fun AnalysisItemCard(
    analysis: AnalysisItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Название анализа
            Text(
                text = analysis.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Дни выполнения
            Text(
                text = "📅 ${analysis.days}",
                fontSize = 14.sp,
                color = Color.Gray
            )

            // Цена (если есть)
            if (analysis.price != null) {
                Text(
                    text = analysis.price,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
            }
        }

        // Кнопка "Добавить"
        Button(
            onClick = { /* Добавить в корзину */ },
            modifier = Modifier
                .width(90.dp)
                .height(36.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Добавить",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ===== Компонент: Нижняя навигация =====
@Composable
fun BottomNavigationBar(
    selectedItem: String,
    onItemClick: (String) -> Unit
) {
    val items = listOf("Анализы", "Результаты", "Поддержка", "Профиль")
    val icons = mapOf(
        "Анализы" to Icons.Default.Search,
        "Результаты" to Icons.Default.List,
        "Поддержка" to Icons.Default.Chat,
        "Профиль" to Icons.Default.Person
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = selectedItem == item
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { onItemClick(item) }
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = icons[item] ?: Icons.Default.Circle,
                        contentDescription = item,
                        tint = if (isSelected) Color(0xFF4CAF50) else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = item,
                        fontSize = 11.sp,
                        color = if (isSelected) Color(0xFF4CAF50) else Color.Gray,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAnalyticsScreen() {
    AnalyticsScreen()
}