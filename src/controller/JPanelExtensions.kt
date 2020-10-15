package controller

import java.awt.GridBagConstraints
import java.awt.Insets
import javax.swing.JPanel

fun JPanel.createGbc(x: Int, y: Int, width: Int = 1, height: Int = 1, weightX: Double? = null, weightY: Double = 1.0, fill: Int? = null): GridBagConstraints {
    val westInserts = Insets(5, 0, 5, 5)
    val eastInserts = Insets(5, 5, 5, 0)

    val gbc = GridBagConstraints()
    gbc.gridx = x
    gbc.gridy = y
    gbc.gridwidth = width
    gbc.gridheight = height
    gbc.anchor = if (x == 0) GridBagConstraints.WEST else GridBagConstraints.EAST
    gbc.fill = fill ?: if (x == 0) GridBagConstraints.BOTH else GridBagConstraints.HORIZONTAL
    gbc.insets = if (x == 0) westInserts else eastInserts
    gbc.weightx = weightX ?: if (x == 0) 0.1 else 1.0
    gbc.weighty = weightY
    return gbc
}