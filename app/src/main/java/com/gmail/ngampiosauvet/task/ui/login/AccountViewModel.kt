package com.gmail.ngampiosauvet.task.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.ngampiosauvet.task.data.AccountRepository
import com.gmail.ngampiosauvet.task.data.User
import com.gmail.ngampiosauvet.task.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor (
    private val accountRepository: AccountRepository) : ViewModel() {

    private val _signupUiState = MutableStateFlow<Resource<User>?>(null)
    val signupUiState: StateFlow<Resource<User>?> = _signupUiState.asStateFlow()


    private val _loginUiState = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginUiState: StateFlow<Resource<FirebaseUser>?> = _loginUiState.asStateFlow()

    val currentUser:StateFlow<User?>
        get() = accountRepository.currentUser.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            User()
        )

    /*
    init {
        if (accountRepository.currentUser != null) {
            _signupUiState.value = Resource.Success(accountRepository.currentUser!!)
        }
    } */

       fun signup(email:String, password:String) =
           viewModelScope.launch {
               _signupUiState.value = Resource.Loading
               val result = accountRepository.createAccount(email=email,password= password)
               _signupUiState.value = result
           }


    fun login(email: String, password: String) =
        viewModelScope.launch {
            _loginUiState.value = Resource.Loading
            val result = accountRepository.login(email, password)
            _loginUiState.value = result
        }



}