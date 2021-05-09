package com.koshake1.mytranslator.utils

import com.koshake1.mytranslator.model.data.Meanings

fun String.Companion.getEmptyString(): String = ""

fun convertMeaningsToString(meanings: List<Meanings>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translation?.text, ", ")
        } else {
            meaning.translation?.text
        }
    }
    return meaningsSeparatedByComma
}