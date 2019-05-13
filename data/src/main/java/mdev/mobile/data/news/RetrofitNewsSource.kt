package mdev.mobile.data.news

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import mdev.mobile.data.network.rest.RestApi
import mdev.mobile.data.network.rest.models.toNews
import mdev.mobile.data.network.util.handleCall
import mdev.mobile.domain.repo.DataSourceFactoryT
import mdev.mobile.domain.repo.news.News
import mdev.mobile.domain.repo.news.NewsKey
import mdev.mobile.domain.repo.news.NewsSource

class RetrofitNewsSource(private val restApi: RestApi) : NewsSource {
    override fun news(language: String, country: String): DataSourceFactoryT<NewsKey, News> {
        return object : DataSourceFactoryT<NewsKey, News>() {
            override fun create(): DataSource<NewsKey, News> {
                return SCNewsDataSource(restApi, language, country)
            }
        }
    }
}

class SCNewsDataSource(
    private val scApi: RestApi,
    private val language: String,
    private val country: String
) : PageKeyedDataSource<NewsKey, News>() {

    companion object {
        private const val INITIAL_KEY: NewsKey = 0
        private const val REQUEST_SIZE = 30
    }

    override fun loadInitial(params: LoadInitialParams<NewsKey>, callback: LoadInitialCallback<NewsKey, News>) {
        val (key, list) = request(INITIAL_KEY, KeyOperator.FOR_LOAD_AFTER)
        callback.onResult(list, null, key)
    }

    override fun loadAfter(params: LoadParams<NewsKey>, callback: LoadCallback<NewsKey, News>) {
        val (key, list) = request(params.key, KeyOperator.FOR_LOAD_AFTER)
        callback.onResult(list, key)
    }

    override fun loadBefore(params: LoadParams<NewsKey>, callback: LoadCallback<NewsKey, News>) {
        val (key, list) = request(params.key, KeyOperator.FOR_LOAD_BEFORE)
        callback.onResult(list, key)
    }

    enum class KeyOperator {
        FOR_LOAD_AFTER, FOR_LOAD_BEFORE
    }

    private fun request(key: NewsKey, keyOperator: KeyOperator): Pair<NewsKey?, List<News>> {
        val call = scApi.news(key, REQUEST_SIZE, country, language)
        val scNews = handleCall(call)

        val newKey = when (keyOperator) {
            KeyOperator.FOR_LOAD_AFTER -> key + REQUEST_SIZE
            KeyOperator.FOR_LOAD_BEFORE -> key - REQUEST_SIZE
        }.takeIf { scNews.size == REQUEST_SIZE }

        return newKey to scNews.map { it.toNews() }
    }
}
