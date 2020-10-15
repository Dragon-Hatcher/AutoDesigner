package mvc

import extensions.packUp
import javax.swing.JPanel

class StackNavigationController(parentWindow: Window? = null, parentVC: ViewController? = null) : ViewController(parentWindow, parentVC) {

    private val viewControllers: MutableList<ViewController> = mutableListOf()

    override fun addToContext(viewController: ViewController) {
        viewController.parentVC = this
        viewControllers.add(viewController)
        view.removeAll()
        view.add(viewController.view)
        println("stack size: ${view.preferredSize}")
        parentWindow?.viewUpdated()
    }

    override fun removeFromContext(viewController: ViewController) {
        println("$viewController === ${viewControllers.lastOrNull()}")
        if (viewController === viewControllers.lastOrNull()) {
            val last = viewControllers.removeLastOrNull() ?: return
            last.deinit()
            view.removeAll()
            view.add(viewControllers.lastOrNull()?.view ?: JPanel())
            parentWindow?.viewUpdated()
            parentWindow?.frame?.packUp()
        }
    }
}