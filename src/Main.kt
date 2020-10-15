import auto.AutoCreationVC
import auto.AutosModel
import com.formdev.flatlaf.FlatDarculaLaf
import controller.ControllerCreationVC
import robot.RobotsModel
import robot.parts.loadParts
import utils.loadIcons
import java.io.File
import javax.swing.Icon
import javax.swing.UIManager

val res = File("res")

val parts = loadParts()

var robots = RobotsModel()
var autos = AutosModel()

lateinit var icons: Map<String, Icon>

fun main() {
    FlatDarculaLaf.install()

    icons = loadIcons()

    val window = MainWindow()

    window.navigationController.addToContext(ControllerCreationVC())
//    window.navigationController.addToContext(AutoCreationVC())
//    window.navigationController.push(RobotCreationVC())

}
