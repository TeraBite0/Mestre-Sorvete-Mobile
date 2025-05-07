package com.example.terabitemobile.data.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.terabitemobile.data.api.LoginApiService
import com.example.terabitemobile.data.classes.LoginRequest
import com.example.terabitemobile.data.classes.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Patterns

class LoginModel(
    private val loginService: LoginApiService,
    private val loginResponse: LoginResponse
) : ViewModel() {

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val response: LoginResponse) : LoginState()
        data class Error(val message: String) : LoginState()
    }

    private val _loginState = MutableLiveData<LoginState>(LoginState.Idle)
    val loginState: LiveData<LoginState> = _loginState

    fun fazerLogin(email: String, senha: String) {
        // Validações iniciais
        val validationError = validateCredentials(email, senha)
        if (validationError != null) {
            _loginState.value = LoginState.Error(validationError)
            return
        }

        _loginState.value = LoginState.Loading

        loginService.login(LoginRequest(email, senha)).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                handleLoginResponse(response)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginState.postValue(LoginState.Error("Falha na comunicação: ${t.message ?: "Erro desconhecido"}"))
            }
        })
    }

    private fun handleLoginResponse(response: Response<LoginResponse>) {
        when {
            response.isSuccessful -> {
                response.body()?.let { dadosLogin ->
                    updateLoginResponse(dadosLogin)
                    _loginState.postValue(LoginState.Success(dadosLogin))
                } ?: run {
                    _loginState.postValue(LoginState.Error("Resposta inválida do servidor"))
                }
            }
            else -> {
                val errorMessage = when (response.code()) {
                    401 -> "Credenciais inválidas"
                    500 -> "Erro interno do servidor"
                    else -> "Erro: ${response.code()} - ${response.message()}"
                }
                _loginState.postValue(LoginState.Error(errorMessage))
            }
        }
    }

    private fun updateLoginResponse(dadosLogin: LoginResponse) {
        loginResponse.apply {
            token = dadosLogin.token
            email = dadosLogin.email
            userId = dadosLogin.userId
        }
        Log.i("api", "LoginResponse atualizada: $loginResponse")
    }

    private fun validateCredentials(email: String, senha: String): String? {
        return when {
            email.isEmpty() -> "Por favor, informe o email"
            !isValidEmail(email) -> "Formato de email inválido"
            senha.isEmpty() -> "Por favor, informe a senha"
            senha.length < 6 -> "Senha deve ter pelo menos 6 caracteres"
            else -> null
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}