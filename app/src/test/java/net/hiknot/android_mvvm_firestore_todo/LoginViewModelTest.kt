package net.hiknot.android_mvvm_firestore_todo

import com.google.common.truth.Truth
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.hiknot.android_mvvm_firestore_todo.data.source.FirebaseProfileService
import net.hiknot.android_mvvm_firestore_todo.ui.entity.Page
import net.hiknot.android_mvvm_firestore_todo.ui.viewmodel.LoginViewModel
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    lateinit var profileMock: FirebaseProfileService
    lateinit var loginViewModel: LoginViewModel

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)

        profileMock = mockk(relaxed = true)
        loginViewModel = LoginViewModel(profileMock)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun signInGuest() = runTest {
        loginViewModel.signInGuest()
        val page = loginViewModel.replaceEvent.first()
        Truth.assertThat(page.getContentIfNotHandled()).isEqualTo(Page.TaskList)
    }
}
