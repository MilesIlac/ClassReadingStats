package com.milesilac.classreadingstats.model

data class ClassSheet(
    var classSection: ClassSection,
    var students: List<Pair<String, List<Student>>>,
)
