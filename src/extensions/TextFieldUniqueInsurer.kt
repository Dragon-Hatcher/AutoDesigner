package extensions

import javax.swing.JTextField

class TextFieldUniqueInsurer(private val message: String, private val preprocess: (String) -> String = { it }) {
    private val boxes: MutableList<JTextField> = mutableListOf()

    fun add(box: JTextField) {
        boxes.add(box)
        box.changeListener { checkNames() }
        checkNames()
    }

    fun remove(box: JTextField) {
        boxes.remove(box)
        checkNames()
    }

    private fun checkNames() {
        val f = boxes.map { preprocess(it.text) }.firstDuplicateOrNull()
        boxes.forEach {
            it.setWarning(if (preprocess(it.text) == f) WarningType.ERROR else WarningType.NONE)
            it.toolTipText = if (preprocess(it.text) == f) message else null
            it.repaint()
        }
    }

    fun areThereDuplicates() = boxes.map { it.text }.firstDuplicateOrNull() == null
}