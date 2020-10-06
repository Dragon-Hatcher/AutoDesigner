package MVC

import java.awt.Color
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JPanel

open class Window: FrameDelegate {
    var frame = JFrame().also{it.preferredSize = Dimension(500, 500); it.background = Color(255, 0, 0) }
    var view = frame.contentPane
        set(value) {field = value; println("set")}

    override fun viewUpdated() {
        frame.pack()
        frame.repaint()
    }
}