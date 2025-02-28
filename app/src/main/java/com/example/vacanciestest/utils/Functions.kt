package com.example.vacanciestest.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.vacanciestest.R
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

/**
 * Открывает сайт в браузере
 * @param context Контекст
 * @param url Адрес сайта
 */
fun openWebSite(context: Context, url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    } catch(e: Exception) {
        Toast.makeText(context, context.resources.getString(R.string.app_not_found), Toast.LENGTH_SHORT).show()
    }
}