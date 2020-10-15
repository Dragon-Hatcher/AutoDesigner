package controller

import mvc.ViewController
import java.awt.Dimension

class ControllerCreationVC : ViewController() {

    override val fillSpace = true
    val createdController = ControllerType()

    init {
        view.preferredSize = Dimension(1000, 500)
        paddedTitle("New Controller")

    }
}