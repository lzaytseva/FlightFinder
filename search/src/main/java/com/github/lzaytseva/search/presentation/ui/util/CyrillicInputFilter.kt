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
            if (c != null && !Character.UnicodeBlock.of(c)?.equals(Character.UnicodeBlock.CYRILLIC)!!) {
                // Если символ не кириллический, возвращаем пустую строку
                return ""
            }
        }
        // Все символы валидны, возвращаем null (ничего не фильтруем)
        return null
    }
}