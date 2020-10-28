package controller

import utils.convertToJavaIdentifier

abstract class InputMethod(var name: String) {
    abstract val enumName: String
    abstract fun toCode(): String
}

class CheckBoxInputMethod(name: String) : InputMethod(name) {
    override val enumName = "CHECKBOX"
    override fun toCode() = "boolean ${convertToJavaIdentifier(name)}"
}

class DropDownInputMethod(name: String, var option: MutableList<String>) : InputMethod(name) {
    override val enumName = "DROPDOWN"
    override fun toCode() = "${convertToJavaIdentifier(name) + "_Enum"} ${convertToJavaIdentifier(name)}"
}

class NumberInputMethod(name: String) : InputMethod(name) {
    override val enumName = "NUMBER"
    override fun toCode() = "double ${convertToJavaIdentifier(name)}"
}
