package com.milesilac.classreadingstats.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.milesilac.classreadingstats.model.GradeLevel

@Entity(tableName = "sections")
data class SectionEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "section_room_id") val sectionRoomId: Long = 0,
    @ColumnInfo(name = "grade_level") val gradeLevel: GradeLevel,
    @ColumnInfo(name = "section_name") val sectionName: String,
)
