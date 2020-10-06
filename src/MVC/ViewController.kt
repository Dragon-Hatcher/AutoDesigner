package MVC

import java.awt.GridBagConstraints
import java.awt.Insets
import javax.swing.JLabel
import javax.swing.JPanel

abstract class ViewController() {

    var navController: NavigationController? = null
    var view: JPanel = JPanel()
    private val models: MutableList<Model> = mutableListOf()

    protected fun subscribeToModel(model: Model, updater: () -> Unit): Model {
        model.addListener(this, updater)
        models.add(model)
        return model
    }

    protected fun subscribeToModelAndCall(model: Model, updater: () -> Unit): Model {
        subscribeToModel(model, updater)
        updater()
        return model
    }

    fun deinit() {
        models.forEach { it.removeListener(this) }
    }

    protected fun gbcLabelAt(x: Int, y: Int, labelString: String): JLabel {
        val gbc = createGbc(x, y)
        val label = JLabel(labelString)
        view.add(label, gbc)
        return label
    }

    protected fun createGbc(x: Int, y: Int, width: Int = 1, height: Int = 1, weightX: Double? = null, weightY: Double = 1.0): GridBagConstraints {
        val westInserts = Insets(5, 0, 5, 5)
        val eastInserts = Insets(5, 5, 5, 0)

        val gbc = GridBagConstraints()
        gbc.gridx = x
        gbc.gridy = y
        gbc.gridwidth = width
        gbc.gridheight = height
        gbc.anchor = if (x == 0) GridBagConstraints.WEST else GridBagConstraints.EAST
        gbc.fill = if (x == 0) GridBagConstraints.BOTH else GridBagConstraints.HORIZONTAL
        gbc.insets = if (x == 0) westInserts else eastInserts
        gbc.weightx = weightX ?: if (x == 0) 0.1 else 1.0
        gbc.weighty = weightY
        return gbc
    }

}
