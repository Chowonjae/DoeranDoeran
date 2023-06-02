package com.purple.hello.feature.setting.profile.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.core.model.Profile
import com.purple.hello.domain.user.GetProfileUseCase
import com.purple.hello.domain.user.SetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileSettingViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    private val setProfileUseCase: SetProfileUseCase,
) : ViewModel() {

    // TODO : Fake
    val isFirst = false

    fun setProfile(newProfileUrl: String, newBirth: String) =
        viewModelScope.launch(Dispatchers.IO) {
            setProfileUseCase(newProfileUrl = newProfileUrl, newBirth = newBirth)
        }

    @RequiresApi(Build.VERSION_CODES.O)
    val profile: StateFlow<Profile> = getProfileUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        initialValue = Profile(null, null),
    )
}
