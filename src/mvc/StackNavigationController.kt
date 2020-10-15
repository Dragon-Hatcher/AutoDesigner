package mvc

import extensions.packUp
import java.awt.Color
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JPanel

class StackNavigationController(parentWindow: Window? = null, parentVC: ViewController? = null) : ViewController(parentWindow, parentVC) {

    companion object {
        val gbcFill = GridBagConstraints()
        val gbcNoFill = GridBagConstraints()
    }

    init {
        view.layout = GridBagLayout()
        gbcFill.fill = GridBagConstraints.BOTH
        gbcFill.weightx = 1.0
        gbcFill.weighty = 1.0
        gbcFill.insets = Insets(10, 10, 10, 10)
        gbcNoFill.weightx = 1.0
        gbcNoFill.weighty = 1.0
        gbcNoFill.insets = Insets(10, 10, 10, 10)
    }


    private val viewControllers: MutableList<ViewController> = mutableListOf()

    override fun addToContext(viewController: ViewController) {
        viewController.parentVC = this
        viewControllers.add(viewController)
        view.removeAll()
        view.add(viewController.view, if (viewController.fillSpace) gbcFill else gbcNoFill)
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