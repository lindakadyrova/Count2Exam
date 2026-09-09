package com.kadyrova.count2exam.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import androidx.compose.runtime.State

class RegisterViewModel @JvmOverloads constructor(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ViewModel() {

    private val _firstName = mutableStateOf("")
    val firstName: State<String> = _firstName
    fun onFirstNameChange(value: String) { _firstName.value = value }

    private val _lastName = mutableStateOf("")
    val lastName: State<String> = _lastName
    fun onLastNameChange(value: String) { _lastName.value = value }

    private val _username = mutableStateOf("")
    val username: State<String> = _username
    fun onUsernameChange(value: String) { _username.value = value }

    private val _email = mutableStateOf("")
    val email: State<String> = _email
    fun onEmailChange(value: String) { _email.value = value }

    private val _password = mutableStateOf("")
    val password: State<String> = _password
    fun onPasswordChange(value: String) { _password.value = value }

    private val _confirmPassword = mutableStateOf("")
    val confirmPassword: State<String> = _confirmPassword
    fun onConfirmPasswordChange(value: String) { _confirmPassword.value = value }

    val isLoading = mutableStateOf(false)

    sealed interface RegisterError {
        object EmptyFields : RegisterError
        object PasswordsDoNotMatch : RegisterError
        object InvalidEmailFormat : RegisterError
        object EmailAlreadyInUse : RegisterError
        object WeakPassword : RegisterError
        object NetworkError : RegisterError
        data class Unknown(val message: String?) : RegisterError
    }

    val error = mutableStateOf<RegisterError?>(null)

    sealed interface RegisterEvent {
        object NavigateToHome : RegisterEvent
    }

    private val _events = Channel<RegisterEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun register() {
        if (
            firstName.value.isBlank() ||
            lastName.value.isBlank() ||
            username.value.isBlank() ||
            email.value.isBlank() ||
            password.value.isBlank() ||
            confirmPassword.value.isBlank()
        ) {
            error.value = RegisterError.EmptyFields
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            error.value = RegisterError.InvalidEmailFormat
            return
        }

        if (password.value != confirmPassword.value) {
            error.value = RegisterError.PasswordsDoNotMatch
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            val authResult = try {
                auth.createUserWithEmailAndPassword(email.value, password.value).await()
            } catch (e: Exception) {
                error.value = mapFirebaseError(e)
                isLoading.value = false
                return@launch
            }

            val uid = authResult.user?.uid
            if (uid == null) {
                error.value = RegisterError.Unknown(null)
                isLoading.value = false
                return@launch
            }

            val user = hashMapOf(
                "firstName" to firstName.value,
                "lastName" to lastName.value,
                "username" to username.value,
                "email" to email.value
            )

            try {
                db.collection("users").document(uid).set(user).await()
                _events.send(RegisterEvent.NavigateToHome)
            } catch (e: Exception) {
                authResult.user?.delete()?.await()
                error.value = mapFirebaseError(e)
            } finally {
                isLoading.value = false
            }
        }
    }

    private fun mapFirebaseError(e: Exception): RegisterError = when (e) {
        is FirebaseAuthUserCollisionException -> RegisterError.EmailAlreadyInUse
        is FirebaseAuthWeakPasswordException -> RegisterError.WeakPassword
        is FirebaseNetworkException -> RegisterError.NetworkError
        else -> RegisterError.Unknown(e.message)
    }
}