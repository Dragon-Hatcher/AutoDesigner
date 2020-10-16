package controller

import controllers
import electronics
import extensions.TextFieldUniqueInsurer
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

        private val uniqueInsurer = TextFieldUniqueInsurer("There are duplicate names.")

        var electronicsView = JPanel()
        var controllerView = JPanel()

        var selections = mutableListOf<SelectionClass>()

        init {
            electronicsView.layout = GridBagLayout()
            controllerView.layout = GridBagLayout()

            add(::ElectronicSelection)
            add(::ControllerSelection)

            eButton.addActionListener { add(::ElectronicSelection) }
            cButton.addActionListener { add(::ControllerSelection) }
        }

        private fun add(init: () -> SelectionClass) {
            val new = init()
            uniqueInsurer.add(new.nameTextField)
            new.button.addActionListener { delete(new) }
            selections.add(new)
            redraw()
        }

        private fun delete(sc: SelectionClass) {
            selections.remove(sc)
            uniqueInsurer.remove(sc.nameTextField)
            redraw()
        }

        private fun redraw() {
            electronicsView.removeAll()
            controllerView.removeAll()
            var eCount = 0
            var cCount = 0
            selections.forEach { v ->
                if (v is ElectronicSelection) {
                    electronicsView.add(v.view, createGbc(0, eCount, fill = GridBagConstraints.NONE)
                            .also { it.anchor = GridBagConstraints.WEST; it.insets = Insets(0, 0, 0, 0) })
                    eCount++
                } else if (v is ControllerSelection) {
                    controllerView.add(v.view, createGbc(0, cCount, fill = GridBagConstraints.NONE)
                            .also { it.anchor = GridBagConstraints.WEST; it.insets = Insets(0, 0, 0, 0) })
                    cCount++
                }
            }
            electronicsView.revalidate()
            controllerView.revalidate()
        }

        interface SelectionClass {
            val nameTextField: JTextField
            val button: JButton
        }

        class ControllerSelection : ViewController(), SelectionClass {

            override val nameTextField = JTextField()
            val typeComboBox = JComboBox<String>()
            override val button = JButton(icons["minus"])

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

        class ElectronicSelection : ViewController(), SelectionClass {

            override val nameTextField = JTextField()
            val typeComboBox = JComboBox<String>()
            override val button = JButton(icons["minus"])

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
            view.add(MethodCreate().view, createGbc(0, 1))
        }

        class MethodCreate : ViewController() {
            val codeArea = RSyntaxTextArea(10, 0)
            val scrollArea = RTextScrollPane(codeArea)
            val addButton = JButton(icons["plus"])
            val inputSelection = InputSelection(addButton)

            init {
                view.layout = GridBagLayout()

                //Inputs
                gbcLabelAt(0, 0, "Inputs:", weightX = 0.0)
                view.add(addButton, createGbc(1, 0, fill = GridBagConstraints.NONE).also { it.anchor = GridBagConstraints.WEST })
                view.add(inputSelection.view, createGbc(1, 1))

                //Java code
                gbcLabelAt(0, 2, "Implementation:", weightX = 0.0)

                codeArea.syntaxEditingStyle = SyntaxConstants.SYNTAX_STYLE_JAVA
                codeArea.isCodeFoldingEnabled = true
                codeArea.font = jetbrainsMono
                ideaTheme.apply(codeArea)

                val extraPanel = JPanel()
                extraPanel.layout = BorderLayout()
                extraPanel.add(scrollArea, BorderLayout.CENTER)
                view.add(extraPanel, createGbc(0, 3, fill = GridBagConstraints.BOTH, width = 2, weightX = 1.0, weightY = 1.0))

                //--------------------------------------------------------------
                view.add(JSeparator(), createGbc(0, 4, width = 2))

            }

            class InputSelection(addButton: JButton) : ViewController() {

                val uniqueInsurer = TextFieldUniqueInsurer("There are duplicate names.")

                val inputTypeMenu = JPopupMenu()
                val inputObjects: MutableList<ViewController> = mutableListOf()

                init {
                    view.layout = GridBagLayout()

                    addButton.addActionListener {
                        inputTypeMenu.show(addButton, addButton.width, 0)
                    }

                    val checkbox = JMenuItem("Checkbox")
                    val number = JMenuItem("Number")
                    val dropdown = JMenuItem("Dropdown menu")

                    inputTypeMenu.add(checkbox)
                    inputTypeMenu.add(number)
                    inputTypeMenu.add(dropdown)

                    checkbox.addActionListener { add(::CheckBoxVC) }
                    number.addActionListener { add(::NumberInputVC) }
                    dropdown.addActionListener { add(::DropdownInputVC) }

                }

                private fun add(init: () -> InputClass) {
                    val new = init()
                    uniqueInsurer.add(new.nameTextField)
                    new.minusButton.addActionListener { remove(new) }
                    inputObjects.add(new as ViewController)
                    redraw()
                }

                private fun remove(ic: InputClass) {
                    inputObjects.remove(ic as ViewController)
                    uniqueInsurer.remove(ic.nameTextField)
                    redraw()
                }

                private fun redraw() {
                    view.removeAll()
                    inputObjects.forEachIndexed { index, viewController ->
                        view.add(viewController.view, createGbc(0, index, fill = GridBagConstraints.NONE).also { it.insets = Insets(0, 0, 0, 0) })
                    }
                    view.revalidate()
                }

                interface InputClass {
                    val nameTextField: JTextField
                    val minusButton: JButton
                }

                class CheckBoxVC : ViewController(), InputClass {
                    override val nameTextField = JTextField()
                    override val minusButton = JButton(icons["minus"])

                    init {
                        view.layout = GridBagLayout()

                        val cbLabel = gbcLabelAt(0, 0, "Checkbox:")
                        cbLabel.preferredSize = Dimension(100, cbLabel.preferredSize.height)

                        gbcLabelAt(1, 0, "Name:")

                        nameTextField.preferredSize = Dimension(300, nameTextField.preferredSize.height)
                        view.add(nameTextField, createGbc(2, 0))

                        view.add(JSeparator(JSeparator.VERTICAL), createGbc(3, 0))
                        view.add(minusButton, createGbc(4, 0))
                    }
                }

                class DropdownInputVC : ViewController(), InputClass {
                    override val nameTextField = JTextField()
                    override val minusButton = JButton(icons["minus"])
                    val dropdownOptionsPanel = DropdownOptionManager()

                    init {
                        view.layout = GridBagLayout()

                        val cbLabel = gbcLabelAt(0, 0, "Dropdown:")
                        cbLabel.preferredSize = Dimension(100, cbLabel.preferredSize.height)

                        gbcLabelAt(1, 0, "Name:")

                        nameTextField.preferredSize = Dimension(300, nameTextField.preferredSize.height)
                        view.add(nameTextField, createGbc(2, 0))

                        view.add(JSeparator(JSeparator.VERTICAL), createGbc(3, 0))
                        view.add(minusButton, createGbc(4, 0, fill = GridBagConstraints.NONE))
                        view.add(JPanel().also { it.preferredSize = Dimension(40, 1) }, createGbc(5, 0))

                        view.add(dropdownOptionsPanel.view, createGbc(2, 1, width = 4).also { it.insets = Insets(0, 0, 0, 0) })

                    }

                    class DropdownOptionManager : ViewController() {

                        val uniqueInsurer = TextFieldUniqueInsurer("There are duplicate names.")

                        val addButton = JButton("Option", icons["plus"])

                        val dropdownOptions = mutableListOf<DropdownOption>()

                        init {
                            view.layout = GridBagLayout()

                            addButton.addActionListener { add() }

                            add()
                            add()
                        }

                        fun redraw() {
                            view.removeAll()
                            view.add(addButton, createGbc(0, 0, fill = GridBagConstraints.NONE, weightX = 0.0))
                            dropdownOptions.forEachIndexed { index, dropdownOption ->
                                view.add(dropdownOption.view, createGbc(1, index).also { it.insets = Insets(0, 0, 0, 0) })
                            }
                            view.revalidate()
                        }

                        fun add() {
                            val new = DropdownOption()
                            uniqueInsurer.add(new.optionName)
                            dropdownOptions.add(new)
                            new.minusButton.addActionListener { remove(new) }
                            if (dropdownOptions.size == 3) {
                                dropdownOptions[0].minusButton.isEnabled = true
                                dropdownOptions[1].minusButton.isEnabled = true
                            } else if (dropdownOptions.size == 2) {
                                dropdownOptions[0].minusButton.isEnabled = false
                                dropdownOptions[1].minusButton.isEnabled = false
                            }
                            redraw()
                        }

                        fun remove(dO: DropdownOption) {
                            uniqueInsurer.remove(dO.optionName)
                            dropdownOptions.remove(dO)
                            dropdownOptions[0].minusButton.isEnabled = dropdownOptions.size > 2
                            dropdownOptions[1].minusButton.isEnabled = dropdownOptions.size > 2
                            redraw()
                        }

                        class DropdownOption : ViewController() {
                            val optionName = JTextField()
                            val minusButton = JButton(icons["minus"])

                            init {
                                view.layout = GridBagLayout()

//                                optionName.preferredSize = Dimension(300, optionName.preferredSize.height)
                                view.add(optionName, createGbc(0, 0, fill = GridBagConstraints.HORIZONTAL, weightX = 1.0))

                                view.add(minusButton, createGbc(1, 0, fill = GridBagConstraints.NONE, weightX = 0.0))
                            }
                        }
                    }
                }

                class NumberInputVC : ViewController(), InputClass {
                    override val nameTextField = JTextField()
                    override val minusButton = JButton(icons["minus"])

                    init {
                        view.layout = GridBagLayout()

                        val cbLabel = gbcLabelAt(0, 0, "Number Input:")
                        cbLabel.preferredSize = Dimension(100, cbLabel.preferredSize.height)

                        gbcLabelAt(1, 0, "Name:")

                        nameTextField.preferredSize = Dimension(300, nameTextField.preferredSize.height)
                        view.add(nameTextField, createGbc(2, 0))

                        view.add(JSeparator(JSeparator.VERTICAL), createGbc(3, 0))
                        view.add(minusButton, createGbc(4, 0))
                    }
                }

            }
        }

    }
}