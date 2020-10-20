package mvc

abstract class Model {

    private val listeners: MutableMap<Any, () -> Unit> = HashMap()

    fun addListener(listener: Any, updater: () -> Unit) {
        listeners[listener] = updater
    }

    fun removeListener(listener: Any) {
        listeners.remove(listener)
    }

    fun update() = listeners.forEach { it.value() }

}