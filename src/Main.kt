import Robot.RobotsModel
import Auto.AutoCreationVC
import Auto.AutosModel
import javax.swing.JFrame

var robots = RobotsModel()
var autos = AutosModel()

fun main() {
    val frame = JFrame()

    val window = MainWindow()
    window.navigationController.push(AutoCreationVC())
}
