import mvc.StackNavigationController
import mvc.Window
import java.awt.Dimension
import java.awt.GridBagLayout
import javax.swing.JFrame

class MainWindow : Window() {

    val navigationController = StackNavigationController(this)

    init {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.minimumSize = Dimension(500, 500)

        view.layout = GridBagLayout()
        view.add(navigationController.view)

        frame.pack()
        frame.isVisible = true
    }

}

