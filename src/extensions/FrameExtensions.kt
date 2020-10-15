package extensions

import javax.swing.JFrame

fun JFrame.packUp() {
    val prevMin = this.minimumSize
    this.minimumSize = this.size
    this.pack()
    this.minimumSize = prevMin
}