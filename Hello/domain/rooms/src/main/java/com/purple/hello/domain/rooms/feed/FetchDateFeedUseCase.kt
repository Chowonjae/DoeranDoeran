package com.purple.hello.domain.rooms.feed

import android.os.Build
import androidx.annotation.RequiresApi
import com.purple.data.rooms.repository.FeedRepository
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class FetchDateFeedUseCase @Inject constructor(
    private val feedRepository: FeedRepository,
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(roomId: Long, date: LocalDateTime) {
        if (date.toLocalDate() == LocalDate.now()) {
            feedRepository.updateDateFeed(roomId, date)
        } else {
            if (!feedRepository.isFeedExistByDate(roomId, date)) {
                feedRepository.updateDateFeed(roomId, date)
            }
        }
        if (!feedRepository.isExistQuestionInRoomByDate(roomId, date)) {
            feedRepository.updateQuestion(roomId, date)
        }

    }
}
