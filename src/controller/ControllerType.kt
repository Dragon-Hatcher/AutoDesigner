package controller

import robot.parts.Part
import java.util.*
import kotlin.collections.HashMap

class ControllerType {
    val uuid: UUID = UUID.randomUUID()
    var name: String = ""
    var electronics: MutableMap<String, Part> = HashMap()
    var controllers: MutableMap<String, ControllerType> = HashMap()
    var method: MutableList<ControllerMethod> = mutableListOf()
}

