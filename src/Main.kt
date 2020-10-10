import robot.RobotsModel
import auto.AutoCreationVC
import auto.AutosModel
import javax.swing.JFrame

var robots = RobotsModel()
var autos = AutosModel()

fun main() {
    val frame = JFrame()

    val window = MainWindow()
    window.navigationController.push(AutoCreationVC())
//    window.navigationController.push(RobotCreationVC())
}
