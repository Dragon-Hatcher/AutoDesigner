package mvc

import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridBagLayout
import javax.swing.JPanel
import javax.swing.JScrollPane

open class ScrollingViewController(parentWindow: Window? = null, parentVC: ViewController? = null) : ViewController(parentWindow, parentVC) {

    private val scrollView = JPanel()
    private val scrollPane = JScrollPane(scrollView)

    init {
        scrollView.layout = GridBagLayout()

        view.preferredSize = Dimension(1000, 500)
        view.layout = BorderLayout()
        scrollPane.border = null
        view.add(scrollPane, BorderLayout.CENTER)
    }

    protected fun gbcLabelAt(x: Int, y: Int, labelString: String) = super.gbcLabelAt(x, y, labelString, view = scrollView)
    protected fun paddedTitle(title: String, padding: Int = 10) {
        super.paddedTitle(title, padding, scrollPane)
    }
}