package robot

import mvc.ViewController
import robots
import utils.Size
import java.awt.*
import java.awt.event.*
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.UIManager
import kotlin.math.PI
import kotlin.math.cos

class RobotCreationVC : ViewController(), MouseListener {

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

        val button = JButton("Create")
        button.addActionListener {
            robots.add(Robot(robotName.text))
            println(parentVC)
            parentVC?.removeFromContext(this)
        }
        optionsView.add(button, createGbc(0, 1, width = 2))

        //Layout
        paddedTitle("Layout", view = layoutView)
        layoutView.add(LayoutCanvas().also { it.preferredSize = Dimension(500, 500) })

        view.add(layoutView, createGbc(0, 1))

        view.addMouseListener(this)
    }


    class LayoutCanvas : JPanel(), MouseListener, MouseMotionListener, KeyListener {

        data class Target(var position: Point, var size: Size) {
            fun within(e: MouseEvent): Boolean =
                    e.point.x >= position.x &&
                            e.point.x <= position.x + size.width &&
                            e.point.y >= position.y &&
                            e.point.y <= position.y + size.height
        }

        var currentTarget: Target? = null
//        var targetOffset: Size? = null
//        val targets: MutableList<Target> = mutableListOf()

        var oW = 50.0
        var oH = 100.0
        var oX = 0.0
        var oY = 0.0
        var oRX = 0.0
        var oRY = 0.0
        var oRZ = 0.0

        init {
//            targets.add(Target(Point(10, 10), Size(30.0, 40.0)))

            addMouseListener(this)
            addMouseMotionListener(this)
            addKeyListener(this)
        }

        override fun mouseDragged(e: MouseEvent?) {
//            currentTarget ?: return
//            e ?: return
//            currentTarget!!.position.x = (targetOffset!!.width + e.point.x).toInt().coerceIn(0, (width - currentTarget!!.size.width).toInt())
//            currentTarget!!.position.y = (targetOffset!!.height + e.point.y).toInt().coerceIn(0, (height - currentTarget!!.size.height).toInt())
//            repaint()
        }


        override fun mousePressed(e: MouseEvent?) {
            this.requestFocus()
//            e ?: return
//            currentTarget = targets.firstOrNull { it.within(e) }
//            if (currentTarget != null) {
//                targetOffset = Size((currentTarget!!.position.x - e.point.x).toDouble(), (currentTarget!!.position.y - e.point.y).toDouble())
//            }
        }

        override fun mouseReleased(e: MouseEvent?) {
//            currentTarget = null
        }

        override fun keyTyped(e: KeyEvent?) {
            e ?: return
            val mult = if (e.isShiftDown) -1 else 1
            when (e.keyChar.toLowerCase()) {
                'x' -> oX += mult * 10
                'y' -> oY += mult * 10
                'w' -> oW += mult * 10
                'h' -> oH += mult * 10
                'r' -> oRX += PI / 10 * mult
                'f' -> oRY += PI / 10 * mult
                'v' -> oRZ += PI / 10 * mult
            }
            println("w:$oW h:$oH x:$oX y:$oY rx:$oRX ry:$oRY rz:$oRZ")
            repaint()
        }

        override fun paint(g: Graphics?) {
            val g2d = g as Graphics2D
            g2d.color = UIManager.getColor("Panel.background")
            g2d.fillRect(0, 0, width, height)
            g2d.color = Color.RED

            val transform = g2d.transform
            g2d.translate(-oX, -oY)
            g2d.rotate(oRZ)
            g2d.scale(cos(oRX), cos(oRY))
            for (i in 0..5) {
                for (j in 0..5) {
                    g2d.color = if ((i + j) % 2 == 0) Color.RED else Color.GREEN
                    g2d.fillRect((oW / 5 * i).toInt(), (oH / 5 * j).toInt(), oW.toInt() / 5, oH.toInt() / 5)
                }
            }

//            targets.forEach { g2d.fillRect(it.position.x, it.position.y, it.size.width.toInt(), it.size.height.toInt()) }
            g2d.transform = transform
        }


        override fun mouseClicked(e: MouseEvent?) {}
        override fun mouseEntered(e: MouseEvent?) {}
        override fun mouseExited(e: MouseEvent?) {}
        override fun mouseMoved(e: MouseEvent?) {}
        override fun keyPressed(e: KeyEvent?) {}
        override fun keyReleased(e: KeyEvent?) {}

    }

    override fun mouseClicked(e: MouseEvent?) {}

    override fun mousePressed(e: MouseEvent?) {
//        view.requestFocus()
    }

    override fun mouseReleased(e: MouseEvent?) {}

    override fun mouseEntered(e: MouseEvent?) {}

    override fun mouseExited(e: MouseEvent?) {}

}