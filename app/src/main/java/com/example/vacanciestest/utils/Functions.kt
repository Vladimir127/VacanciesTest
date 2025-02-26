package com.example.vacanciestest.utils

import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Выполняет преобразование строки даты из формата "2024-02-06" в формат "6 февраля"
 * для отображения даты публикации в пункте списка
 *
 * @param dateString Строка даты в формате "2024-02-06"
 * @return Строка даты в формате "6 февраля" или null, если строку не удалось преобразовать
 */
fun formatPublishedDate(dateString: String?): String? {
    return if (dateString != null) {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d MMMM", Locale.getDefault())
        try {
            val date = inputFormat.parse(dateString)
            outputFormat.format(date)
        } catch (e: Exception) {
            null
        }
    } else {
        null
    }
}