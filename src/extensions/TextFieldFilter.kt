package extensions

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
            // warn the user and don't allow the insert
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
            // warn the user and don't allow the insert
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
            // warn the user and don't allow the insert
        }
    }
}