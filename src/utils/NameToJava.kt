package utils

fun convertToJavaName(name: String) =
        name.toCharArray().mapIndexed { index, c ->
            if (Character.isJavaIdentifierStart(c) ||
                    (Character.isJavaIdentifierPart(c) && index != 0)) {
                c.toString()
            } else if (index == 0 && Character.isJavaIdentifierPart(c)) {
                "_$c"
            } else {
                "_"
            }
        }.joinToString(separator = "").let {
            if (it.isEmpty()) "_" else it
        }.let {
            if (it.endsWith("Enum")) it + "_" else it
        }
