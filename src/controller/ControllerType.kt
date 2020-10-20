package controller

import mvc.FileModel
import robot.parts.ElectronicType
import java.util.*

class ControllerType : FileModel() {
    val uuid: UUID = UUID.randomUUID()
    var name: String = ""
    var electronics: MutableList<ElectronicField> = mutableListOf()
    var controllers: MutableList<ControllerField> = mutableListOf()
    var methods: MutableList<ControllerMethod> = mutableListOf()

    override fun updateFile() {

    }
}

data class ElectronicField(var name: String, var electronic: ElectronicType)
data class ControllerField(var name: String, var controller: ControllerType)
