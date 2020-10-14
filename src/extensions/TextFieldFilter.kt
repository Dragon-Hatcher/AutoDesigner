package extensions

import javax.swing.JComponent
import javax.swing.JTextField
import javax.swing.text.*

fun JTextField.setFilter(test: (String) -> Boolean) {
    (this.document as PlainDocument).documentFilter = TextFieldFilter(test)
}

internal class TextFieldFilter(val test: (String) -> Boolean) : DocumentFilter() {

    @Throws(BadLocationException::class)
    override fun insertString(fb: FilterBypass, offset: Int, string: String?,
                              attr: AttributeSet?) {

        val doc: Document = fb.document
        val sb = StringBuilder()
        sb.append(doc.getText(0, doc.length))
        sb.insert(offset, string)
        if (test(sb.toString())) {
            super.insertString(fb, offset, string, attr)
        } else {
        }
    }


    @Throws(BadLocationException::class)
    override fun replace(fb: FilterBypass, offset: Int, length: Int, text: String?,
                         attrs: AttributeSet?) {
        val doc: Document = fb.document
        val sb = StringBuilder()
        sb.append(doc.getText(0, doc.length))
        sb.replace(offset, offset + length, text)
        if (test(sb.toString())) {
            super.replace(fb, offset, length, text, attrs)
        } else {
        }
    }

    @Throws(BadLocationException::class)
    override fun remove(fb: FilterBypass, offset: Int, length: Int) {
        val doc: Document = fb.document
        val sb = StringBuilder()
        sb.append(doc.getText(0, doc.length))
        sb.delete(offset, offset + length)

        if (test(sb.toString())) {
            super.remove(fb, offset, length)
        } else {
        }
    }
}

enum class WarningType(val id: String) {
    NONE(""),
    WARNING("warning"),
    ERROR("error"),
}

fun JComponent.setWarning(type: WarningType) {
    this.putClientProperty("JComponent.outline", type.id)
}