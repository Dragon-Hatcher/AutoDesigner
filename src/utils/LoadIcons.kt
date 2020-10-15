package utils

import res
import java.awt.Color
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.Icon
import javax.swing.ImageIcon
import javax.swing.UIManager

val builtInIconNames = listOf(
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

fun loadIcons(): Map<String, Icon> {
    //icons in the icons folder
    val iconFolder = File(res, "icons")
    val pngFolder = File(iconFolder, "png")
    val iconFiles = pngFolder.listFiles()!!
    val images = iconFiles.map { it.name.removeSuffix(".png") to ImageIO.read(it) }
    val coloredImages = images.map { it.first to changeColor(it.second, UIManager.getColor("TextField.foreground")) }
    val scaledImages = coloredImages.map { it.first to it.second.getScaledInstance(16, 16, Image.SCALE_DEFAULT) }
    val iconFolderIcons = scaledImages.map { it.first to ImageIcon(it.second) as Icon }.toMap()

    //built in icons

    val builtInIcons = builtInIconNames.map { it.removeSuffix("Icon") to UIManager.getIcon(it) }.toMap()

    val ret = HashMap<String, Icon>()
    ret.putAll(iconFolderIcons)
    ret.putAll(builtInIcons)
    return ret
}

fun changeColor(image: BufferedImage, foreground: Color): Image {
    val time = System.nanoTime()
    val width = image.width
    val height = image.height
    val raster = image.raster
    for (xx in 0 until width) {
        for (yy in 0 until height) {
            val pixels = raster.getPixel(xx, yy, null as IntArray?)
            val percent = ((255 - pixels[0]).toDouble() / 255.0)
            val newColor = intArrayOf(
                    (percent * foreground.red).toInt(),
                    (percent * foreground.green).toInt(),
                    (percent * foreground.blue).toInt(),
                    pixels[3])
            raster.setPixel(xx, yy, newColor)
        }
    }
    total += (System.nanoTime() - time)
    return image
}

var total = 0L

/*
Available icons:
activity
alert
archive
arrow-bottom
arrow-left
arrow-right
arrow-top
backwards
bag
ban
bell
book
bookmark
calendar
camera
caret-bottom
caret-left
caret-right
caret-top
cart
CheckBox.icon
CheckBoxMenuItem.arrow
CheckBoxMenuItem.check
checkmark
chevron-bottom
chevron-left
chevron-right
chevron-top
clipboard
clock
close
code
compose
creditcard
desktop
download
edit
eject
ellipsis-horizontal
ellipsis-vertical
end
export
external
eye
feed
file
FileChooser.detailsView
FileChooser.homeFolder
FileChooser.listView
FileChooser.newFolder
FileChooser.upFolder
FileView.computer
FileView.directory
FileView.file
FileView.floppyDrive
FileView.hardDrive
filter
flag
folder
folder-open
forwards
fullscreen
fullscreen-exit
gift
github
heart
home
import
inbox
info
InternalFrame.close
InternalFrame.iconify
InternalFrame.maximize
InternalFrame.minimize
lightning
link
location
lock
mail
menu
Menu.arrow
MenuItem.arrow
message
microphone
minus
mobile
moon
move
music
mute
OptionPane.error
OptionPane.information
OptionPane.question
OptionPane.warning
options
paperclip
pause
photo
play
plus
portfolio
print
RadioButton.icon
RadioButtonMenuItem.arrow
RadioButtonMenuItem.check
reload
reply
search
send
settings
sign-in
sign-out
star
start
Table.ascendingSort
Table.descendingSort
tag
telephone
trash
Tree.closed
Tree.collapsed
Tree.expanded
Tree.leaf
Tree.open
twitter
unlock
upload
user
video
volume
work
zoom-in
zoom-out
zoom-reset
 */