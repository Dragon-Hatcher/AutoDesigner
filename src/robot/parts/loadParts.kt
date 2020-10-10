package robot.parts

import res
import java.io.File

fun loadParts(): List<Part> {
    val partsTXT = File(res, "parts.txt")
    val text = partsTXT.readText()

    val partLines = text.split("\n").filterNot { it.startsWith("|") }
    val splitPartLines = partLines.map { it.split(",") }
    val noWhiteSpaceSplitPartLines = splitPartLines.map { line -> line.map { it.trim() } }

    val typeLoc = 0
    val nameLoc = 2
    val classNameLoc = 1

    //TODO these parts should be ordered how they are in the app
    return noWhiteSpaceSplitPartLines.map { Part(it[nameLoc], it[classNameLoc], PartType.valueOf(it[typeLoc])) }
}