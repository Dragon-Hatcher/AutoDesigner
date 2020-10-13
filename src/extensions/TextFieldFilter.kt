package extensions

import javax.swing.JTextField
import javax.swing.text.*

fun JTextField.setFilter(warnType: String? = null, test: (String) -> Boolean) {
    (this.document as PlainDocument).documentFilter = TextFieldFilter(test, this, warnType)
}

internal class TextFieldFilter(val test: (String) -> Boolean, val myTextField: JTextField, val possibleError: String?) : DocumentFilter() {

    fun warn() {
        myTextField.putClientProperty("JComponent.outline", possibleError)
    }

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
            warn()
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
            warn()
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
            warn()
        }
    }
}