package com.purple.hello.domain.rooms.feed

import com.purple.data.rooms.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetQuestionUseCase @Inject constructor(
    private val feedRepository: FeedRepository,
) {
    operator fun invoke(roomId: Long, date: LocalDateTime): Flow<String?> =
        feedRepository.getQuestion(roomId, date)
}
