package com.purple.hello.domain.rooms.feed

import com.purple.data.rooms.repository.FeedRepository
import java.io.File
import javax.inject.Inject

class UploadFeedUseCase @Inject constructor(
    private val feedRepository: FeedRepository,
) {
    suspend operator fun invoke(roomId: Long, image: File) {
        feedRepository.uploadFeed(roomId, image)
    }
}
