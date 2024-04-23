package com.github.lzaytseva.search.presentation.ui.util

import android.text.InputFilter
import android.text.Spanned

class CyrillicInputFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        // Проверяем каждый введенный символ
        for (i in start until end) {
            val c = source?.get(i)
            // Разрешаем кириллицу, пробел и дефис
            if (c != null && !c.isCyrillic() && c != ' ' && c != '-') {
                // Если символ не кириллический, пробел или дефис, возвращаем пустую строку
                return ""
            }
        }
        // Все символы валидны, возвращаем null (ничего не фильтруем)
        return null
    }

    // Расширение для проверки, является ли символ кириллическим
    private fun Char.isCyrillic(): Boolean {
        return Character.UnicodeBlock.of(this) == Character.UnicodeBlock.CYRILLIC
    }
}