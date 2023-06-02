package com.purple.hello.feature.rooms.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.purple.hello.domain.rooms.feed.UploadFeedUseCase
import com.purple.hello.feature.rooms.navigation.roomIdArg
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val uploadFeedUseCase: UploadFeedUseCase,
) : ViewModel() {

    val roomId: Long = checkNotNull(savedStateHandle[roomIdArg])

    suspend fun uploadFeed(image: File) = uploadFeedUseCase(roomId, image)
}
