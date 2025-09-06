package com.milesilac.classreadingstats.persistence.model

import androidx.room.Embedded
import androidx.room.Relation

data class ClassSheetRelationship(
    @Embedded val section: SectionEntity,
    @Relation(
        parentColumn = "section_room_id",
        entityColumn = "section_room_id"
    )
    val students: List<StudentEntity>
)
