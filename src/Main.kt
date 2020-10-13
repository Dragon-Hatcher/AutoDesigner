import auto.AutoCreationVC
import auto.AutosModel
import com.formdev.flatlaf.FlatDarculaLaf
import com.formdev.flatlaf.FlatLightLaf
import robot.RobotCreationVC
import robot.RobotsModel
import robot.parts.loadParts
import java.io.File
import javax.swing.UIManager

val res = File("res")

val parts = loadParts()

var robots = RobotsModel()
var autos = AutosModel()

fun main() {


//    FlatLightLaf.install()
    FlatDarculaLaf.install()
    val window = MainWindow()
    window.navigationController.push(AutoCreationVC())
//    window.navigationController.push(RobotCreationVC())
}
