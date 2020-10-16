package controller

import controllers
import electronics
import extensions.WarningType
import extensions.changeListener
import extensions.firstDuplicateOrNull
import extensions.setWarning
import icons
import ideaTheme
import jetbrainsMono
import mvc.ScrollingViewController
import mvc.ViewController
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea
import org.fife.ui.rsyntaxtextarea.SyntaxConstants
import org.fife.ui.rtextarea.RTextScrollPane
import java.awt.*
import javax.swing.*

class ControllerCreationVC : ScrollingViewController() {

    override val fillSpace = true

    private val nameField = JTextField()

    init {
        paddedTitle("New Controller")

        //Name Label
        gbcLabelAt(0, 0, "Name:")
        scrollView.add(nameField, createGbc(1, 0))

        //--------------------------------------------------------------
        scrollView.add(JSeparator(), createGbc(0, 1, width = 2))

        //Electronics & Controllers
        val addEButton = JButton(icons["plus"])
        val addCButton = JButton(icons["plus"])
        val eAndCManager = ElectronicsAndControllersManager(addEButton, addCButton)

        //Electronics
        gbcLabelAt(0, 2, "Electronics:")
        scrollView.add(addEButton, createGbc(1, 2, fill = GridBagConstraints.NONE).also { it.anchor = GridBagConstraints.WEST })
        scrollView.add(eAndCManager.electronicsView, createGbc(1, 3))

        //Controllers
        gbcLabelAt(0, 4, "Controllers:")
        scrollView.add(addCButton, createGbc(1, 4, fill = GridBagConstraints.NONE).also { it.anchor = GridBagConstraints.WEST })
        scrollView.add(eAndCManager.controllerView, createGbc(1, 5))

        //--------------------------------------------------------------
        scrollView.add(JSeparator(), createGbc(0, 6, width = 2))

        //Methods
        val methodsManager = MethodsManager()

        gbcLabelAt(0, 7, "Methods:")
        scrollView.add(methodsManager.view, createGbc(1, 8))

        //Dummy panel
        scrollView.add(JPanel(), createGbc(0, 20, width = 2, fill = GridBagConstraints.VERTICAL, weightY = 10000.0))
    }

    class ElectronicsAndControllersManager(eButton: JButton, cButton: JButton) : ViewController() {

        var electronicsView = JPanel()
        var controllerView = JPanel()

        var eSelections = mutableListOf<ElectronicSelection>()
        var cSelections = mutableListOf<ControllerSelection>()

        init {
            electronicsView.layout = GridBagLayout()
            controllerView.layout = GridBagLayout()

            addE()
            addC()

            eButton.addActionListener { addE() }
            cButton.addActionListener { addC() }
        }

        private fun checkNames() {
            val allNames = eSelections.map { it.nameTextField.text }.plus(cSelections.map { it.nameTextField.text })
            val f = allNames.firstDuplicateOrNull()
            eSelections.forEach {
                it.nameTextField.setWarning(if (it.nameTextField.text == f) WarningType.ERROR else WarningType.NONE)
                it.nameTextField.toolTipText = if (it.nameTextField.text == f) "There are duplicate names." else null
                it.nameTextField.repaint()
            }
            cSelections.forEach {
                it.nameTextField.setWarning(if (it.nameTextField.text == f) WarningType.ERROR else WarningType.NONE)
                it.nameTextField.toolTipText = if (it.nameTextField.text == f) "There are duplicate names." else null
                it.nameTextField.repaint()
            }
        }

        private fun delete(e: ElectronicSelection) {
            eSelections.remove(e)
            redrawE()
            checkNames()
        }

        private fun delete(c: ControllerSelection) {
            cSelections.remove(c)
            redrawC()
            checkNames()
        }

        private fun addE() {
            val new = ElectronicSelection()
            new.nameTextField.changeListener { checkNames() }
            new.button.addActionListener { delete(new) }
            eSelections.add(new)
            redrawE()
        }

        private fun redrawE() {
            electronicsView.removeAll()
            eSelections.forEachIndexed { i, v -> electronicsView.add(v.view, createGbc(0, i, fill = GridBagConstraints.NONE).also { it.anchor = GridBagConstraints.WEST; it.insets = Insets(0, 0, 0, 0) }) }
            electronicsView.revalidate()
        }

        private fun addC() {
            val new = ControllerSelection()
            new.nameTextField.changeListener { checkNames() }
            new.button.addActionListener { delete(new) }
            cSelections.add(new)
            redrawC()
        }

        private fun redrawC() {
            controllerView.removeAll()
            cSelections.forEachIndexed { i, v -> controllerView.add(v.view, createGbc(0, i, fill = GridBagConstraints.NONE).also { it.anchor = GridBagConstraints.WEST; it.insets = Insets(0, 0, 0, 0) }) }
            controllerView.revalidate()
        }

        class ControllerSelection : ViewController() {

            val nameTextField = JTextField()
            val typeComboBox = JComboBox<String>()
            val button = JButton(icons["minus"])

            init {
                view.layout = GridBagLayout()

                //Name Label
                view.add(JLabel("Name:"), createGbc(0, 0).also { it.anchor = GridBagConstraints.EAST })

                //Name Text Field

                nameTextField.preferredSize = Dimension(300, nameTextField.preferredSize.height)
                view.add(nameTextField, createGbc(1, 0))

                view.add(JSeparator(JSeparator.VERTICAL), createGbc(2, 0))

                //Type Label
                view.add(JLabel("Type:"), createGbc(3, 0).also { it.anchor = GridBagConstraints.EAST })

                //Type Combo Box
                subscribeToModelAndCall(controllers) {
                    typeComboBox.removeAllItems()
                    controllers.controllers.forEach { typeComboBox.addItem(it.name) }
                }
                typeComboBox.preferredSize = Dimension(200, typeComboBox.preferredSize.height)
                view.add(typeComboBox, createGbc(4, 0))

                view.add(JSeparator(JSeparator.VERTICAL), createGbc(5, 0))

                //Remove Button
                view.add(button, createGbc(6, 0, fill = GridBagConstraints.NONE).also { it.anchor = GridBagConstraints.EAST })
            }
        }


        class ElectronicSelection : ViewController() {

            val nameTextField = JTextField()
            val typeComboBox = JComboBox<String>()
            val button = JButton(icons["minus"])

            init {
                view.layout = GridBagLayout()

                //Name Label
                view.add(JLabel("Name:"), createGbc(0, 0).also { it.anchor = GridBagConstraints.EAST })

                //Name Text Field
                nameTextField.preferredSize = Dimension(300, nameTextField.preferredSize.height)
                view.add(nameTextField, createGbc(1, 0))

                view.add(JSeparator(JSeparator.VERTICAL), createGbc(2, 0))

                //Type Label
                view.add(JLabel("Type:"), createGbc(3, 0).also { it.anchor = GridBagConstraints.EAST })

                //Type Combo Box
                electronics.forEach { typeComboBox.addItem(it.name) }
                typeComboBox.preferredSize = Dimension(200, typeComboBox.preferredSize.height)
                view.add(typeComboBox, createGbc(4, 0))

                view.add(JSeparator(JSeparator.VERTICAL), createGbc(5, 0))

                //Remove Button
                view.add(button, createGbc(6, 0, fill = GridBagConstraints.NONE))
            }
        }

    }

    class MethodsManager : ViewController() {
        init {
            view.layout = GridBagLayout()
            view.add(MethodCreate().view, createGbc(0, 0))
//            view.background = Color.BLUE
        }

        class MethodCreate : ViewController() {
            val codeArea = RSyntaxTextArea(10, 0)
            val scrollArea = RTextScrollPane(codeArea)

            init {
                view.layout = GridBagLayout()

                codeArea.syntaxEditingStyle = SyntaxConstants.SYNTAX_STYLE_JAVA
                codeArea.background = UIManager.getColor("Panel.background")
                codeArea.isCodeFoldingEnabled = true
                codeArea.antiAliasingEnabled = true
                codeArea.font = jetbrainsMono
                ideaTheme.apply(codeArea)

                val extraPanel = JPanel()
                extraPanel.layout = BorderLayout()
                extraPanel.add(scrollArea, BorderLayout.CENTER)
                view.add(extraPanel, createGbc(0, 0, fill = GridBagConstraints.BOTH, weightX = 1.0, weightY = 1.0))
            }
        }

    }
}