package ru.skillbranch.devintensive.utils

import java.lang.StringBuilder
import java.util.*

object Utils {

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = if (fullName.isNullOrBlank()) null else fullName.split(" ")
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return Pair(firstName, lastName)
    }

    fun toInitials(firstName: String?, lastName: String?): String? {

        val lambdaInitial: (String?) -> Char? = { name: String? ->
            if (name.isNullOrBlank()) null else name.trim().first().toUpperCase() }

        val firstInitial = lambdaInitial(firstName)
        val lastInitial = lambdaInitial(lastName)

        return if(firstInitial != null || lastInitial != null) "${firstInitial ?: ""}${lastInitial ?: ""}" else null
    }

    fun transliteration(payload: String, divider: String = " "): String {

        val cyrillicList = listOf("а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м",
            "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы",
            "ь", "э", "ю", "я")

        val latinList = listOf("a", "b", "v", "g", "d", "e", "e", "zh", "z", "i", "i", "k", "l", "m",
            "n", "o", "p", "r", "s", "t", "u", "f", "h", "c", "ch", "sh", "sh'", "", "i", "",
            "e", "yu", "ya")

        val stringBuilder = StringBuilder()
        payload.forEach { item ->
            var index = -1
            var iterator = 0
            while (index == -1 && iterator < 2){
                val itemForSearch = if(iterator == 0) item.toString() else item.toString().toLowerCase(Locale.ROOT)
                index = cyrillicList.indexOf(itemForSearch)
                iterator++
            }
            if(index != -1){
                stringBuilder.append(if(iterator == 1) latinList[index] else latinList[index].capitalize(Locale.ROOT))
            } else {
                stringBuilder.append(item)
            }
        }
        val transliteration = stringBuilder.toString()
        return if (divider == " ") transliteration else transliteration.replace(" ", divider)
    }
}