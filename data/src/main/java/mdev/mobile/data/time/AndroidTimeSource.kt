package mdev.mobile.data.time

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.github.karczews.rxbroadcastreceiver.RxBroadcastReceivers
import io.reactivex.Observable
import mdev.mobile.domain.repo.time.TimeSource
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime

class AndroidTimeSource(private val context: Context) : TimeSource {
    override fun currentDay(): Observable<OffsetDateTime> =
        RxBroadcastReceivers.fromIntentFilter(context, IntentFilter(Intent.ACTION_DATE_CHANGED))
            .map { OffsetDateTime.now() }
            .startWith(OffsetDateTime.now())

    // Android has Intent.ACTION_DATE_CHANGED bug on some versions https://issuetracker.google.com/issues/36908018
    override fun currentEpochDay(): Observable<Long> =
        RxBroadcastReceivers.fromIntentFilter(context, IntentFilter(Intent.ACTION_DATE_CHANGED))
            .map { LocalDate.now().toEpochDay() }
            .startWith(LocalDate.now().toEpochDay())
}
