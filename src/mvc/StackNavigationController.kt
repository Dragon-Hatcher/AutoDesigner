package mvc

import extensions.packUp
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JPanel

class StackNavigationController(parentWindow: Window? = null, parentVC: ViewController? = null) : ViewController(parentWindow, parentVC) {

    init {
        view.layout = GridBagLayout()
    }


    private val viewControllers: MutableList<ViewController> = mutableListOf()

    override fun addToContext(viewController: ViewController) {
        viewController.parentVC = this
        viewControllers.add(viewController)
        view.removeAll()

        val gbc = GridBagConstraints()
        gbc.weightx = 1.0
        gbc.weighty = 1.0
        gbc.fill = if (viewController.fillSpace) GridBagConstraints.BOTH else GridBagConstraints.NONE
        gbc.insets = viewController.navControllerPadding
        view.add(viewController.view, gbc)

        parentWindow?.viewUpdated()
    }

    override fun removeFromContext(viewController: ViewController) {
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