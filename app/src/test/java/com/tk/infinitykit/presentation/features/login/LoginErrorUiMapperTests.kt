package com.tk.infinitykit.presentation.features.login

import android.content.res.Resources
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import com.tk.infinitykit.R

class LoginErrorUiMapperTests {
    private val resources = mockk<Resources>()
    private val tested = LoginErrorUiMapper(resources)

    @Test
    fun testGetIncorrectFieldMessage() {
        // given
        val expectedMessage = "Incorrect field"
        every { resources.getString(R.string.login_error_incorrect_field) } returns expectedMessage

        // when
        val result = tested.getIncorrectFieldMessage()

        // then
        assertEquals(expectedMessage, result)
    }
}