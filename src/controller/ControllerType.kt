package controller

import robot.parts.Part

class ControllerType {
    var name: String = ""
    var electronics: MutableMap<String, Part> = HashMap()
    var controllers: MutableMap<String, ControllerType> = HashMap()
    var method: MutableList<ControllerMethod> = mutableListOf()
}

