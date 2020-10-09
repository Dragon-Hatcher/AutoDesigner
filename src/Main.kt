import Robot.RobotsModel
import Auto.AutoCreationVC
import Auto.AutosModel
import Robot.RobotCreationVC
import javax.swing.JFrame

var robots = RobotsModel()
var autos = AutosModel()

fun main() {

    val window = MainWindow()
    window.navigationController.push(AutoCreationVC())
//    window.navigationController.push(RobotCreationVC())
}
