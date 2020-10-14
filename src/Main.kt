import auto.Auto
import auto.AutoCreationVC
import auto.AutosModel
import com.formdev.flatlaf.FlatDarculaLaf
import gameProperties.AllianceColor
import robot.Robot
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

    autos.add(Auto("test", "fred", AllianceColor.BLUE, null))

    window.navigationController.push(AutoCreationVC())
//    window.navigationController.push(RobotCreationVC())

}
