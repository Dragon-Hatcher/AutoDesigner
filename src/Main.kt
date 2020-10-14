import auto.AutoCreationVC
import auto.AutosModel
import com.formdev.flatlaf.FlatDarculaLaf
import robot.RobotsModel
import robot.parts.loadParts
import java.io.File

val res = File("res")

val parts = loadParts()

var robots = RobotsModel()
var autos = AutosModel()

fun main() {
    FlatDarculaLaf.install()
    val window = MainWindow()
    window.navigationController.push(AutoCreationVC())
//    window.navigationController.push(RobotCreationVC())

}
