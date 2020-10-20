package utils

/**
 * @param java the java code to remove the comments from
 * @returns the inputted java code with the comments replaced with " "
 */
fun removeJavaComments(java: String) =
        java.replace(Regex("//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/"), "$1 ")