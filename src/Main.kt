import auto.AutosModel
import com.formdev.flatlaf.FlatDarculaLaf
import controller.ControllerCreationVC
import controller.ControllerType
import controller.ControllersModel
import mvc.ScrollingViewController
import robot.RobotsModel
import robot.parts.loadParts
import utils.loadIcons
import java.io.File
import javax.swing.Icon

val res = File("res")

var robots = RobotsModel()
var autos = AutosModel()

val electronics = loadParts()
var controllers = ControllersModel()

lateinit var icons: Map<String, Icon>

fun main() {
    FlatDarculaLaf.install()

    controllers.add(ControllerType().also { it.name = "Drive Train" })

    icons = loadIcons()

    val window = MainWindow()

    window.navigationController.addToContext(ControllerCreationVC())
//    window.navigationController.addToContext(AutoCreationVC())
//    window.navigationController.push(RobotCreationVC())

}
