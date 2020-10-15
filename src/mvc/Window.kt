package mvc

import extensions.max
import extensions.packUp
import java.awt.Color
import java.awt.Dimension
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.JFrame
import javax.swing.JPanel


open class Window {
    companion object {
        val defaultMinSize = Dimension(500, 500)
    }

    var frame = JFrame().also { it.minimumSize = defaultMinSize; it.background = Color(255, 0, 0) }
    var view: JPanel = frame.contentPane as JPanel

    init {
        frame.addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent) {
                frame.size = frame.size.max(frame.minimumSize)
            }
        })
    }

    fun viewUpdated() {
        frame.minimumSize = frame.preferredSize.max(defaultMinSize)
        frame.packUp()
        frame.repaint()
    }
}