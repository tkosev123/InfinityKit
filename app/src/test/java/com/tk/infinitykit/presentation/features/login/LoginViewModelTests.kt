package com.tk.infinitykit.presentation.features.login

import com.tk.domain.usecase.LoginUseCase
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTests {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val loginUseCase = mockk<LoginUseCase>()
    private val uiMapper = mockk<LoginErrorUiMapper>()

    private val tested: LoginViewModel = LoginViewModel(loginUseCase, uiMapper)

    @Test
    fun test() {
        testCoroutineRule.runTest {
            // when
            tested.onIntent(LoginIntent.TogglePasswordVisibility)

            // then
            assertTrue(tested.state.value.isPasswordVisible)
        }
    }
}