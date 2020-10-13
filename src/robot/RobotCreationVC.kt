package robot

import mvc.ViewController
import utils.Size
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.UIManager

class RobotCreationVC : ViewController() {

    private val optionsView = JPanel()
    private val layoutView = JPanel()
    private val robotName = JTextField()

    init {
        view.layout = GridBagLayout()
        paddedTitle("New Robot")

        optionsView.layout = GridBagLayout()
        paddedTitle("Options", view = optionsView)

        //Robot name
        gbcLabelAt(0, 0, "Name:", view = optionsView)

        robotName.preferredSize = Dimension(300, robotName.preferredSize.height)
        optionsView.add(robotName, createGbc(1, 0))

        view.add(optionsView, createGbc(0, 0))

        //Layout
        paddedTitle("Layout", view = layoutView)
        layoutView.add(LayoutCanvas().also { it.preferredSize = Dimension(500, 500) })

        view.add(layoutView, createGbc(0, 1))
    }


    class LayoutCanvas : JPanel(), MouseListener, MouseMotionListener {

        data class Target(var position: Point, var size: Size) {
            fun within(e: MouseEvent): Boolean =
                    e.point.x >= position.x &&
                            e.point.x <= position.x + size.width &&
                            e.point.y >= position.y &&
                            e.point.y <= position.y + size.height
        }

        var currentTarget: Target? = null
        var targetOffset: Size? = null
        val targets: MutableList<Target> = mutableListOf()

        init {
            targets.add(Target(Point(10, 10), Size(30.0, 40.0)))

            addMouseListener(this)
            addMouseMotionListener(this)
        }

        override fun mouseDragged(e: MouseEvent?) {
            currentTarget ?: return
            e ?: return
            currentTarget!!.position.x = (targetOffset!!.width + e.point.x).toInt().coerceIn(0, (width - currentTarget!!.size.width).toInt())
            currentTarget!!.position.y = (targetOffset!!.height + e.point.y).toInt().coerceIn(0, (height - currentTarget!!.size.height).toInt())
            repaint()
        }


        override fun mousePressed(e: MouseEvent?) {
            e ?: return
            currentTarget = targets.firstOrNull { it.within(e) }
            if (currentTarget != null) {
                targetOffset = Size((currentTarget!!.position.x - e.point.x).toDouble(), (currentTarget!!.position.y - e.point.y).toDouble())
            }
        }

        override fun mouseReleased(e: MouseEvent?) {
            currentTarget = null
        }

        override fun paint(g: Graphics?) {
            val g2d = g as Graphics2D
            g2d.color = Color.LIGHT_GRAY
            g2d.color = UIManager.getColor("Panel.background")
            g2d.fillRect(0, 0, width, height)
            g2d.color = Color.RED
            targets.forEach { g2d.fillRect(it.position.x, it.position.y, it.size.width.toInt(), it.size.height.toInt()) }
        }

        override fun mouseClicked(e: MouseEvent?) {}
        override fun mouseEntered(e: MouseEvent?) {}
        override fun mouseExited(e: MouseEvent?) {}
        override fun mouseMoved(e: MouseEvent?) {}

    }

}