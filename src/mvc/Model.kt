package mvc

abstract class Model {

    private val listeners: MutableMap<ViewController, () -> Unit> = HashMap()

    fun addListener(viewController: ViewController, updater: () -> Unit) {
        listeners[viewController] = updater
    }

    fun removeListener(viewController: ViewController) {
        listeners.remove(viewController)
    }

    fun update() = listeners.forEach { it.value() }

}