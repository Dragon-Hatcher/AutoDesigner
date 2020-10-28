package controller

import utils.convertToJavaName

abstract class InputMethod(var name: String) {
    abstract val enumName: String
    abstract fun toCode(): String
}

class CheckBoxInputMethod(name: String) : InputMethod(name) {
    override val enumName = "CHECKBOX"
    override fun toCode() = "boolean ${convertToJavaName(name)}"
}

class DropDownInputMethod(name: String, var option: MutableList<String>) : InputMethod(name) {
    override val enumName = "DROPDOWN"
    override fun toCode() = "${convertToJavaName(name) + "_Enum"} ${convertToJavaName(name)}"
}

class NumberInputMethod(name: String) : InputMethod(name) {
    override val enumName = "NUMBER"
    override fun toCode() = "double ${convertToJavaName(name)}"
}
