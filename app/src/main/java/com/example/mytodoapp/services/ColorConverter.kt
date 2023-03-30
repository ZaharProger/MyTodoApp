package com.example.mytodoapp.services

class ColorConverter(private val radix: Int) {
    private val hexSymbols = ('A'..'F') + ('0'..'9')

    fun getRgba(color: String): Array<Int> {
        val rgbaColor = Array(4) { 0 }
        var start = 0

        for (i in rgbaColor.indices) {
            start = if (i == rgbaColor.size - 1) 0 else start + 2
            val end = start + 1

            rgbaColor[i] = color.substring(start..end).toInt(radix)
        }

        return rgbaColor
    }

    fun getStringColor(): String {
        val generatedColor = (1..6)
            .map { hexSymbols.shuffled().first() }
            .joinToString("")

        return "FF${generatedColor}"
    }

    fun convertWithRadix(color: String): ULong {
        return color.toULong(radix)
    }
}