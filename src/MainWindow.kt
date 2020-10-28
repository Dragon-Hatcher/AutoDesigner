import mvc.StackNavigationController
import mvc.Window
import java.awt.Color
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.UIManager

class MainWindow : Window() {

    val navigationController = StackNavigationController(this)

    init {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.minimumSize = Dimension(500, 500)

        view.layout = GridBagLayout()
        val gbc = GridBagConstraints()
        gbc.fill = GridBagConstraints.BOTH
        gbc.weightx = 1.0
        gbc.weighty = 1.0
        view.add(navigationController.view, gbc)
        navigationController.view.background = UIManager.getColor("Panel.background").darker()

        frame.pack()
        frame.isVisible = true
    }

}

