package mdev.mobile.domain.repo.time

import io.reactivex.Observable
import org.threeten.bp.OffsetDateTime

class TimeRepo(private val timeSource: TimeSource) {
    fun currentDay() = timeSource.currentDay()

    fun currentEpochDay() = timeSource.currentEpochDay()
}

interface TimeSource {
    fun currentDay(): Observable<OffsetDateTime>

    fun currentEpochDay(): Observable<Long>
}
