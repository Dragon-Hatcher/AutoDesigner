import MVC.NavigationController
import MVC.Window
import java.awt.Dimension
import java.awt.GridBagLayout
import javax.swing.JFrame

class MainWindow: Window() {

    val navigationController = NavigationController(this)

    init {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.preferredSize = Dimension(1000, 1000)

        view.layout = GridBagLayout()
        view.add(navigationController.view)

        frame.pack()
        frame.isVisible = true
    }

}

