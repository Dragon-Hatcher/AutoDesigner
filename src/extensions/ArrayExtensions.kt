package extensions

fun <E> List<E>.firstDuplicateOrNull(): E? {
    val found = HashSet<E>()
    for (e in this) {
        if (e in found) return e else found.add(e)
    }
    return null
}