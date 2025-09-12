package com.milesilac.classreadingstats

import com.milesilac.classreadingstats.helpers.inputFullCheckForDecimalString
import com.milesilac.classreadingstats.helpers.inputFullCheckForIntString
import com.milesilac.classreadingstats.helpers.removeExtraZeroes
import com.milesilac.classreadingstats.helpers.repairInput
import org.junit.Assert.assertEquals
import org.junit.Test

class UnitTests {

    @Test
    fun removeExtraZeroes_isCorrect() {
        assertEquals("101", "000101".removeExtraZeroes())
    }

    @Test
    fun repairInput_isCorrect() {
        assertEquals(
            "1", "-11".repairInput(
                currentValue = "-1",
                errorValue = "-1"
            )
        )
        assertEquals(
            "1", "1-1".repairInput(
                currentValue = "-1",
                errorValue = "-1"
            )
        )
        assertEquals(
            "2", "2-1".repairInput(
                currentValue = "-1",
                errorValue = "-1"
            )
        )
        assertEquals(
            "2", "-12".repairInput(
                currentValue = "-1",
                errorValue = "-1"
            )
        )
        assertEquals(
            "2", "-21".repairInput(
                currentValue = "-1",
                errorValue = "-1"
            )
        )
    }

    @Test
    fun inputFullCheckForIntString_isCorrect() {
        "000101".inputFullCheckForIntString(
            currentValue = "000101",
            errorValue = "-1",
            returnValue = { returnValue -> assertEquals("101", returnValue)}
        )
    }

    @Test
    fun inputFullCheckForDecimalString_isCorrect() {
        "000101.0".inputFullCheckForDecimalString(
            currentValue = "000101.0",
            errorValue = "-1",
            returnValue = { returnValue -> assertEquals("101.0", returnValue)}
        )
    }

}