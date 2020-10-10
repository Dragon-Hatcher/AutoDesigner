package mvc

import javax.swing.JPanel

class NavigationController(private val frameDelegate: FrameDelegate? = null): ViewController() {

    private val viewControllers: MutableList<ViewController> = mutableListOf()

    fun push(viewController: ViewController) {
        viewController.navController = this
        viewControllers.add(viewController)
        view.removeAll()
        view.add(viewController.view)
        frameDelegate?.viewUpdated()
    }

    fun pop(): ViewController? {
        val last = viewControllers.removeLastOrNull() ?: return null
        last.deinit()
        view.removeAll()
        view.add(viewControllers.lastOrNull()?.view ?: JPanel())
        frameDelegate?.viewUpdated()
        return last
    }
}