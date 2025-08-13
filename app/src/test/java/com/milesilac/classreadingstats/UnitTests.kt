package com.milesilac.classreadingstats

import com.milesilac.classreadingstats.helpers.removeExtraZeroes
import org.junit.Assert.assertEquals
import org.junit.Test

class UnitTests {

    @Test
    fun removeExtraZeroes_isCorrect() {
        assertEquals("101", "000101".removeExtraZeroes())
    }

}