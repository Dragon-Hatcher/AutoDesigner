package extensions

import javax.swing.JFrame

fun JFrame.packUp() {
    val prevMin = this.minimumSize
    this.minimumSize = this.size
    println("pack size: ${this.contentPane.preferredSize}")
    this.pack()
    this.minimumSize = prevMin
}