package controller

abstract class InputMethod(var name: String)

class CheckBoxInputMethod(name: String) : InputMethod(name)
class DropDownInputMethod(name: String, var option: MutableList<String>) : InputMethod(name)
class NumberInputMethod(name: String) : InputMethod(name)
