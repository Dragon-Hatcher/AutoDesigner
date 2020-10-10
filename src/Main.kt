import auto.AutoCreationVC
import auto.AutosModel
import robot.RobotsModel
import robot.parts.loadParts
import java.io.File

val res = File("res")

val parts = loadParts()

var robots = RobotsModel()
var autos = AutosModel()

fun main() {
    parts.forEach { println(it) }

    val window = MainWindow()
    window.navigationController.push(AutoCreationVC())
//    window.navigationController.push(RobotCreationVC())
}
