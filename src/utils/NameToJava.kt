package utils

/**
 * Converts a user inputted string to a Java identifier by replacing any invalid characters with an underscore.
 * An empty string is converted to `_`. If the name ends with Enum an `_` is added at the
 * end to avoid collision with autogenerated enums for dropdown menu inputs.
 * @param name the name to be converted to a Java identifier
 * @return the inputted string formatted as a valid Java identifier
 */
fun convertToJavaIdentifier(name: String) =
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
