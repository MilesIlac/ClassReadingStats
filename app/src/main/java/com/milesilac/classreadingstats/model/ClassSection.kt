package com.milesilac.classreadingstats.model

import com.milesilac.classreadingstats.helpers.capitalizeMaybe
import kotlinx.serialization.Serializable
import kotlin.enums.enumEntries

@Serializable
data class ClassSection(
    var gradeLevel: GradeLevel = GradeLevel.EIGHT,
    var sectionName: String
)

fun initClassSection() = ClassSection(
    gradeLevel = GradeLevel.ERROR,
    sectionName = ""
)

fun ClassSection.toSectionString(
    isSpaced: Boolean = false,
    isSectionNameUpperCased: Boolean = true
): String {
    val sectionName = when {
        isSectionNameUpperCased -> this.sectionName.uppercase()
        else -> this.sectionName
    }
    return when {
        isSpaced -> "${this.gradeLevel.grade} - $sectionName".trim()
        else -> "${this.gradeLevel.grade}-$sectionName".trim()
    }
}

fun String.toClassSection(): ClassSection {
    val (gradeLevelString, sectionNameString) = this.split("-", limit = 2)
    val gradeLevels =  enumEntries<GradeLevel>()
    val gradeLevel = gradeLevels.find { it.name == gradeLevelString.trim() }
        ?: gradeLevels.find { it.grade.toString() == gradeLevelString.trim() }
        ?: GradeLevel.ERROR
    val sectionName = sectionNameString.trim().capitalizeMaybe()
    return when {
        gradeLevel == GradeLevel.ERROR -> ClassSection(gradeLevel = gradeLevel, sectionName = "")
        else -> ClassSection(gradeLevel = gradeLevel, sectionName = sectionName)
    }
}