package mdev.mobile.data.locale

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.github.karczews.rxbroadcastreceiver.RxBroadcastReceivers
import io.reactivex.Observable
import mdev.mobile.domain.repo.locale.LocaleSource
import java.util.Locale

class AndroidLocaleSource(private val context: Context) : LocaleSource {
    override fun language(): Observable<String> {
        return RxBroadcastReceivers.fromIntentFilter(context, IntentFilter(Intent.ACTION_LOCALE_CHANGED))
            .map { Locale.getDefault().isO3Language }
            .startWith(Locale.getDefault().isO3Language)
    }

    override fun getCountry(): String {
        return Locale.getDefault().isO3Country
    }
}
