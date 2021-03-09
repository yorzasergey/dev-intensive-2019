package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, unit: TimeUnits): Date {
    this.time += when(unit) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val diff = date.time - this.time
    return when(abs(diff)){
        in 0 until SECOND -> "только что"
        in 1 until 45*SECOND -> if (diff > 0) "несколько секунд назад" else "через несколько секунд"
        in 45*SECOND until 75*SECOND -> if (diff > 0) "минуту назад" else "через минуту"
        in 75*SECOND until 45*MINUTE -> {
            val plural = TimeUnits.MINUTE.plural(abs(diff/MINUTE).toInt())
            if (diff > 0) "$plural назад" else "через $plural"
        }
        in 45*MINUTE until 75*MINUTE -> if (diff > 0) "час назад" else "через час"
        in 75*MINUTE until 22*HOUR -> {
            val plural = TimeUnits.HOUR.plural(abs(diff/HOUR).toInt())
            if (diff > 0) "$plural назад" else "через $plural"
        }
        in 22*HOUR until 26*HOUR -> if (diff > 0) "день назад" else "через день"
        in 26*HOUR..360*DAY -> {
            val plural = TimeUnits.DAY.plural(abs(diff/DAY).toInt())
            if (diff > 0) "$plural назад" else "через $plural"
        }
        else -> if (diff > 0) "более года назад" else "более чем через год"
    }
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value: Int): String {
        val exception = 11..19
        val modValue = value%10
        val plural = when(this){
            SECOND -> {
                when {
                    value in exception -> "секунд"
                    modValue == 1 -> "секунду"
                    modValue in 2..4 -> "секунды"
                    else -> "секунд"
                }
            }
            MINUTE -> {
                when {
                    value in exception -> "минут"
                    modValue == 1 -> "минуту"
                    modValue in 2..4 -> "минуты"
                    else -> "минут"
                }
            }
            HOUR -> {
                when {
                    value in exception -> "часов"
                    modValue == 1 -> "час"
                    modValue in 2..4 -> "часа"
                    else -> "часов"
                }
            }
            DAY -> {
                when {
                    value in exception -> "дней"
                    modValue == 1 -> "день"
                    modValue in 2..4 -> "дня"
                    else -> "дней"
                }
            }
        }
        return "$value $plural"
    }
}