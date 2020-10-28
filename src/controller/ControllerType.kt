package controller

import mvc.FileModel
import robot.parts.ElectronicType
import utils.convertToJavaIdentifier
import java.util.*

class ControllerType : FileModel() {
    val uuid: UUID = UUID.randomUUID()
    var name: String = ""
    var electronics: MutableList<ElectronicField> = mutableListOf()
    var controllers: MutableList<ControllerField> = mutableListOf()
    var methods: MutableList<ControllerMethod> = mutableListOf()

    override fun updateFile() {

    }

    fun convertToJavaCode(): String {
        val sb = StringBuilder()

        sb.append("@Controller(uuid = \"$uuid\", adName = \"$name\")\n")
        sb.append("public class ${convertToJavaIdentifier(name)} {\n")
        sb.append("\n")
        sb.append("    //Controllers:\n")
        for (c in controllers) {
//    @ControllerController(typeUUID = "8f99ab75-4732-41c2-86ec-aba7753b42ae", adName = "Some Controller")
            sb.append("    @ControllerController(typeUUID = \"${c.controller.uuid}\", adName = \"${c.name}\")\n")
            sb.append("    public Type ${convertToJavaIdentifier((c.controller.name))};\n")
        }
        for (e in electronics)

            for (m in methods) {
                //anno
                sb.append("    @ControllerMethod(adName = \"${m.name}\", options = {\n")
                for (i in m.inputs) {
                    sb.append("        @ControllerMethodParam(type = MethodInputType.${i.enumName}, adName = \"${i.name}\",\n")
                }
                sb.removeSuffix(",\n")
                sb.append("\n    })")
                //java code
                sb.append("    public void ${convertToJavaIdentifier(m.name)}(")
                for (i in m.inputs) {
                    sb.append(i.toCode())
                    sb.append(",")
                }
                sb.removeSuffix(",")
                sb.append(") {\n")
                sb.append(m.code)
                sb.append("}\n\n")
            }

        sb.append("}")

        return sb.toString()
    }
}

data class ElectronicField(var name: String, var electronic: ElectronicType)
data class ControllerField(var name: String, var controller: ControllerType)

