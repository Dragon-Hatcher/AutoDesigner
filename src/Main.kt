import auto.AutoCreationVC
import auto.AutosModel
import com.formdev.flatlaf.FlatDarculaLaf
import robot.RobotsModel
import robot.parts.loadParts
import java.io.File
import javax.swing.UIManager

val res = File("res")

val parts = loadParts()

var robots = RobotsModel()
var autos = AutosModel()

val icons = listOf(
        "CheckBox.icon",
        "CheckBoxMenuItem.arrowIcon",
        "CheckBoxMenuItem.checkIcon",
        "FileChooser.detailsViewIcon",
        "FileChooser.homeFolderIcon",
        "FileChooser.listViewIcon",
        "FileChooser.newFolderIcon",
        "FileChooser.upFolderIcon",
        "FileView.computerIcon",
        "FileView.directoryIcon",
        "FileView.fileIcon",
        "FileView.floppyDriveIcon",
        "FileView.hardDriveIcon",
        "InternalFrame.closeIcon",
        "InternalFrame.iconifyIcon",
        "InternalFrame.maximizeIcon",
        "InternalFrame.minimizeIcon",
        "Menu.arrowIcon",
        "MenuItem.arrowIcon",
        "OptionPane.errorIcon",
        "OptionPane.informationIcon",
        "OptionPane.questionIcon",
        "OptionPane.warningIcon",
        "RadioButton.icon",
        "RadioButtonMenuItem.arrowIcon",
        "RadioButtonMenuItem.checkIcon",
        "Table.ascendingSortIcon",
        "Table.descendingSortIcon",
        "Tree.closedIcon",
        "Tree.collapsedIcon",
        "Tree.expandedIcon",
        "Tree.leafIcon",
        "Tree.openIcon",
)

fun main() {
    FlatDarculaLaf.install()
    val window = MainWindow()

    window.navigationController.addToContext(AutoCreationVC())
//    window.navigationController.push(RobotCreationVC())

}
