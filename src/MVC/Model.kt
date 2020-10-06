package MVC

abstract class Model {

    private val listeners: MutableMap<ViewController, () -> Unit> = HashMap()

    fun addListener(viewController: ViewController, updater: () -> Unit) {
        listeners[viewController] = updater
    }

    fun removeListener(viewController: ViewController) {
        listeners.remove(viewController)
    }

    protected fun update() = listeners.forEach { it.value() }

}