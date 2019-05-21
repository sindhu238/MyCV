package com.example.mycv.extensions

import junit.framework.TestCase.assertEquals
import org.junit.Test

class CommonExtensionsKtTest {

    @Test
    fun `test new line separated list of strings`() {
        val list = listOf("a", "b", "c")
        assertEquals("a\nb\nc", list.newLineSeparatedString)
    }
}