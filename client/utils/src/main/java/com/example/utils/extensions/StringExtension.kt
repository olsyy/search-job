package extension

fun String.getPhoneMask(): String {
    val regex = Regex("(\\d{1})(\\d{3})(\\d{3})(\\d{2})(\\d{2})")
    return when {
        length !in 11..12 -> this
        substring(1..3) == "800" -> replace(regex, "8 ($2) $3-$4-$5")
        startsWith("+7") -> replace(regex, "$1 $2 $3-$4-$5")
        setOf('7', '8').contains(first()) ->  replace(regex, "+7 $2 $3-$4-$5")
        else -> this
    }
}