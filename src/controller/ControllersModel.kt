package controller

import mvc.Model

class ControllersModel : Model() {
    var controllers: List<ControllerType> = listOf()
        set(value) {
            field = value; update()
        }

    fun add(ct: ControllerType) {
        controllers = controllers.plus(ct)
    }
}