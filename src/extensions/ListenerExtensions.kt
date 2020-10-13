package extensions

import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

fun JTextField.changeListener(listener: (DocumentEvent?) -> Unit) {
    this.document.addDocumentListener(object : DocumentListener {
        override fun insertUpdate(e: DocumentEvent?) {
            listener(e)
        }

        override fun removeUpdate(e: DocumentEvent?) {
            listener(e)
        }

        override fun changedUpdate(e: DocumentEvent?) {
            listener(e)
        }
    })
}