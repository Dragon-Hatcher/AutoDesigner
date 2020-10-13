package auto

import mvc.Model

class AutosModel() : Model() {
    var autos: List<Auto> = listOf()
        set(value) {
            field = value; update()
        }

    fun add(auto: Auto) {
        autos = autos.plus(auto)
    }
}