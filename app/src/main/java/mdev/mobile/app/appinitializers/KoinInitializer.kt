package mdev.mobile.app.appinitializers

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.rx2.asCoroutineDispatcher
import mdev.mobile.app.util.common.DEVELOPER_MODE
import mdev.mobile.app.util.logger.KoinLogger
import mdev.mobile.app.util.logger.RetrofitLogger
import mdev.mobile.base.kotlin.util.AppCoroutineDispatchers
import mdev.mobile.base.kotlin.util.AppRxSchedulers
import mdev.mobile.base.kotlin.util.Initializer
import mdev.mobile.data.database.AppDatabase
import mdev.mobile.data.locale.AndroidLocaleSource
import mdev.mobile.data.network.rest.RestApi
import mdev.mobile.data.news.RetrofitNewsSource
import mdev.mobile.data.time.AndroidTimeSource
import mdev.mobile.domain.repo.locale.LocaleRepo
import mdev.mobile.domain.repo.locale.LocaleSource
import mdev.mobile.domain.repo.news.NewsRepo
import mdev.mobile.domain.repo.news.NewsSource
import mdev.mobile.domain.repo.time.TimeRepo
import mdev.mobile.domain.repo.time.TimeSource
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.with
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.startKoin
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val OKHTTP_CACHE_SIZE = 1024L * 1024L * 2L // 2MB

const val GLIDE_OKHTTP_CLIENT = "glide_okhttp_client"
const val NETWORK_OKHTTP_CLIENT = "network_okhttp_client"

private val commonModule = module {
    single {
        AppRxSchedulers(
            io = Schedulers.io(),
            computation = Schedulers.computation(),
            main = AndroidSchedulers.mainThread()
        )
    }
    single {
        val appRxSchedulers: AppRxSchedulers = get()
        AppCoroutineDispatchers(
            io = appRxSchedulers.io.asCoroutineDispatcher(),
            computation = appRxSchedulers.computation.asCoroutineDispatcher(),
            main = Dispatchers.Main
        )
    }
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database").apply {
            if (DEVELOPER_MODE) {
                fallbackToDestructiveMigration()
            }
        }.build()
    }

    single {
        GsonBuilder().create()
    }
}

private val networkModule = module {
    single<OkHttpClient>(GLIDE_OKHTTP_CLIENT) {
        OkHttpClient.Builder().apply {
            StethoInitializer.addStethoInterceptor(this@apply)
        }.build()
    }

    single<OkHttpClient>(NETWORK_OKHTTP_CLIENT) {
        val context: Context = get()
        val okHttpCache = Cache(context.cacheDir, OKHTTP_CACHE_SIZE)

        val logging = HttpLoggingInterceptor(RetrofitLogger())
        logging.level = HttpLoggingInterceptor.Level.BODY

        // Glide client is like a base client
        val glideOkHttpClient: OkHttpClient = get(GLIDE_OKHTTP_CLIENT)
        val builder: OkHttpClient.Builder = glideOkHttpClient.newBuilder()
        builder.apply {
            cache(okHttpCache)
            addInterceptor(logging)
        }.build()
    }

    single<RestApi> {
        Retrofit.Builder()
            .baseUrl("https://api.rest.net/")
            .client(get(NETWORK_OKHTTP_CLIENT))
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RestApi::class.java)
    }
}

private val sourceModule = module {
    single<TimeSource> { AndroidTimeSource(get()) }
    single<NewsSource> { RetrofitNewsSource(get()) }
    single<LocaleSource> { AndroidLocaleSource(get()) }
}

private val repoModule = module {
    single { TimeRepo(get()) }
    single { NewsRepo(get()) }
    single { LocaleRepo(get()) }
}

private val interactorModule = module {
}

class KoinInitializer(val context: Context) : Initializer {
    override fun init() {
        startKoin(
            listOf(
                commonModule,
                networkModule,
                sourceModule,
                repoModule,
                interactorModule
            ),
            logger = KoinLogger()
        ) with context
    }
}
