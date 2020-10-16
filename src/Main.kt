import auto.AutosModel
import com.formdev.flatlaf.FlatDarculaLaf
import com.formdev.flatlaf.FlatIntelliJLaf
import controller.ControllerCreationVC
import controller.ControllerType
import controller.ControllersModel
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea
import org.fife.ui.rsyntaxtextarea.Theme
import robot.RobotsModel
import robot.parts.loadParts
import utils.loadIcons
import java.awt.Font
import java.io.File
import javax.swing.Icon
import javax.swing.JEditorPane
import javax.swing.UIManager

val resFolder = File("res")
val fontFolder = File(resFolder, "fonts")
val themeFolder = File(resFolder, "themes")

val jetbrainsMono = Font.createFont(Font.TRUETYPE_FONT, File(fontFolder, "JetBrainsMono-Regular.ttf")).deriveFont(14.0f)
lateinit var ideaTheme: Theme

var robots = RobotsModel()
var autos = AutosModel()

val electronics = loadParts()
var controllers = ControllersModel()

lateinit var icons: Map<String, Icon>

var isDarkMode = true

fun main() {
    if (isDarkMode) {
        FlatDarculaLaf.install()
    } else {
        FlatIntelliJLaf.install()
    }

    //do this at the start for faster load
    RSyntaxTextArea().font = jetbrainsMono

    println(UIManager.getColor("Panel.foreground"))

    ideaTheme = Theme.load(File(themeFolder, if (isDarkMode) "ideadark.xml" else "idea.xml").inputStream())

    controllers.add(ControllerType().also { it.name = "Drive Train" })

    icons = loadIcons()

    val window = MainWindow()

    window.navigationController.addToContext(ControllerCreationVC())
//    window.navigationController.addToContext(AutoCreationVC())
//    window.navigationController.push(RobotCreationVC())

}
