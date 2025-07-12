package com.avanisoam.businesscardapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// Asegurate de que esta ruta sea correcta
import com.avanisoam.businesscardapp.DataStoreInstance

class DataStoreViewModel(application: Application) :
    AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(DataStoreUiState())
    val uiState: StateFlow<DataStoreUiState> = _uiState

    init {
        getDataStoreValues()
    }

    private fun getDataStoreValues() {
        viewModelScope.launch {
            launch {
                DataStoreInstance.getStringPreferences(
                    getApplication(),
                    DataStoreInstance.NAME_KEY
                ).collect { name ->
                    name?.let {
                        _uiState.update { it.copy(name = name) }
                    }
                }
            }

            launch {
                DataStoreInstance.getStringPreferences(
                    getApplication(),
                    DataStoreInstance.ROLE_KEY
                ).collect { role ->
                    role?.let {
                        _uiState.update { it.copy(role = role) }
                    }
                }
            }

            launch {
                DataStoreInstance.getIntPreferences(
                    getApplication(),
                    DataStoreInstance.YEAR_KEY
                ).collect { year ->
                    year?.let {
                        _uiState.update { it.copy(year = year) }
                    }
                }
            }

            launch {
                DataStoreInstance.getStringPreferences(
                    getApplication(),
                    DataStoreInstance.PHONE_KEY
                ).collect { phone ->
                    phone?.let {
                        _uiState.update { it.copy(phone = phone) }
                    }
                }
            }

            launch {
                DataStoreInstance.getStringPreferences(
                    getApplication(),
                    DataStoreInstance.HANDLE_KEY
                ).collect { handle ->
                    handle?.let {
                        _uiState.update { it.copy(handle = handle) }
                    }
                }
            }

            launch {
                DataStoreInstance.getStringPreferences(
                    getApplication(),
                    DataStoreInstance.EMAIL_KEY
                ).collect { email ->
                    email?.let {
                        _uiState.update { it.copy(email = email) }
                    }
                }
            }

            launch {
                DataStoreInstance.getBooleanPreferences(
                    getApplication(),
                    DataStoreInstance.BOOLEAN_KEY
                ).collect { flag ->
                    flag?.let { value ->
                        _uiState.update { currentState ->
                            currentState.copy(showContactInfo = value) // ✅ value es Boolean
                        }
                    }
                }
            }
        }
    }

    fun toggleSettings() {
        _uiState.update {
            it.copy(showSettings = !it.showSettings)
        }
    }

    fun toggleContactInfo() {
        _uiState.update {
            it.copy(showContactInfo = !it.showContactInfo)
        }
    }

    fun saveValuesInDataStore() {
        viewModelScope.launch {
            val ctx = getApplication<Application>()
            with(_uiState.value) {
                if (isNameUpdated) {
                    DataStoreInstance.saveStringPreferences(ctx, DataStoreInstance.NAME_KEY, name)
                }
                if (isRoleUpdated) {
                    DataStoreInstance.saveStringPreferences(ctx, DataStoreInstance.ROLE_KEY, role)
                }
                if (isYearUpdated) {
                    DataStoreInstance.saveIntPreferences(ctx, DataStoreInstance.YEAR_KEY, year)
                }
                if (isPhoneUpdated) {
                    DataStoreInstance.saveStringPreferences(ctx, DataStoreInstance.PHONE_KEY, phone)
                }
                if (isHandleUpdated) {
                    DataStoreInstance.saveStringPreferences(ctx, DataStoreInstance.HANDLE_KEY, handle)
                }
                if (isEmailUpdated) {
                    DataStoreInstance.saveStringPreferences(ctx, DataStoreInstance.EMAIL_KEY, email)
                }
                DataStoreInstance.saveBooleanPreferences(ctx, DataStoreInstance.BOOLEAN_KEY, showContactInfo)
            }
        }
    }

    // --- Update Handlers ---
    fun updateName(value: String) {
        _uiState.update { it.copy(name = value, isNameUpdated = true) }
    }

    fun updateRole(value: String) {
        _uiState.update { it.copy(role = value, isRoleUpdated = true) }
    }

    fun updateYear(value: Int) {
        _uiState.update { it.copy(year = value, isYearUpdated = true) }
    }

    fun updatePhone(value: String) {
        _uiState.update { it.copy(phone = value, isPhoneUpdated = true) }
    }

    fun updateHandle(value: String) {
        _uiState.update { it.copy(handle = value, isHandleUpdated = true) }
    }

    fun updateEmail(value: String) {
        _uiState.update { it.copy(email = value, isEmailUpdated = true) }
    }
}

data class DataStoreUiState(
    val showSettings: Boolean = false,
    val name: String = "Joseph Grant",
    val isNameUpdated: Boolean = false,
    val role: String = "Ingeniero de Software",
    val isRoleUpdated: Boolean = false,
    val year: Int = 2,
    val isYearUpdated: Boolean = false,
    val phone: String = "+507 6666-6666",
    val isPhoneUpdated: Boolean = false,
    val handle: String = "@AndroidDev",
    val isHandleUpdated: Boolean = false,
    val email: String = "joseph.grant@android.com",
    val isEmailUpdated: Boolean = false,
    val showContactInfo: Boolean = true
)
