package ru.skillbranch.devintensive.extensions

fun String.truncate(newLength: Int = 16): String {
    if(trim().length > newLength){
        return "${substring(0 until newLength).trim()}..."
    }
    return this
}

fun String.stripHtml(): String {
    return replace(Regex("<.*?>"),"").replace(Regex("\\s+"), " ")
}