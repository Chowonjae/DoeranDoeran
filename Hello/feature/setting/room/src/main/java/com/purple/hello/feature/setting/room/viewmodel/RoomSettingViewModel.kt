package com.purple.hello.feature.setting.room.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.core.model.RoomSettingOptions
import com.purple.hello.domain.rooms.*
import com.purple.hello.feature.setting.room.navigation.roomIdArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomSettingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val updateRoomNameUseCase: UpdateRoomNameUseCase,
    private val updateUserNameUseCase: UpdateUserNameUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val exitRoomUseCase: ExitRoomUseCase,
    private val deleteRoomUseCase: DeleteRoomUseCase,
    getRoomSettingsUseCase: GetRoomSettingsUseCase,
) : ViewModel() {

    private val roomId: Long = checkNotNull(savedStateHandle[roomIdArg])

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateRoomName(newRoomName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            updateRoomNameUseCase(userRoomId = roomSettingInfo.value.userRoomId, newRoomName = newRoomName)
        }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateUserName(newUserName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            updateUserNameUseCase(userRoomId = roomSettingInfo.value.userRoomId, newUserName = newUserName)
        }

    fun updateRoomPassword(newPasswordQuestion: String, newPassword: String) =
        viewModelScope.launch(Dispatchers.IO) {
            updatePasswordUseCase(roomId = roomId, newPasswordQuestion = newPasswordQuestion, newPassword = newPassword)
        }

    @RequiresApi(Build.VERSION_CODES.O)
    fun exitRoom() =
        viewModelScope.launch(Dispatchers.IO) {
            exitRoomUseCase(userRoomId = roomSettingInfo.value.userRoomId)
        }

    fun deleteRoom() =
        viewModelScope.launch(Dispatchers.IO) {
            deleteRoomUseCase(roomId = roomId)
        }

    @RequiresApi(Build.VERSION_CODES.O)
    val roomSettingInfo: StateFlow<RoomSettingOptions> = getRoomSettingsUseCase(roomId).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        initialValue = RoomSettingOptions("", 0L, "", "", ""),
    )
}
