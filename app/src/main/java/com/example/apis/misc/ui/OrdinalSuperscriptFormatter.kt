package com.example.apis.misc.ui

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.SuperscriptSpan
import java.util.regex.Pattern

private const val SUPERSCRIPT_REGEX = "(?<=\\b\\d{0,10})(st|nd|rd|th)(?=\\b)"
private const val PROPORTION = 0.6f
class OrdinalSuperscriptFormatter {

    private val pattern = Pattern.compile(SUPERSCRIPT_REGEX)

    fun format(text: String): SpannableStringBuilder {
        val matcher = pattern.matcher(text)

        val stringBuilder = SpannableStringBuilder()
        stringBuilder.append(text)

        while (matcher.find()) {
            val start: Int = matcher.start()
            val end: Int = matcher.end()
            createSuperscriptSpan(start, end, stringBuilder)
        }

        return stringBuilder
    }

    private fun createSuperscriptSpan(start: Int, end: Int, stringBuilder: SpannableStringBuilder) {
        val superscript = SuperscriptSpan()
        val size = RelativeSizeSpan(PROPORTION)
        stringBuilder.setSpan(superscript, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        stringBuilder.setSpan(size, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}