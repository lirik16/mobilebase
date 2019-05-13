package mdev.mobile.domain.repo.news

import io.reactivex.Single
import mdev.mobile.domain.repo.DataSourceFactoryT

typealias NewsKey = Int

class NewsRepo(private val newsSource: NewsSource) {
    fun news(language: String, country: String) = Single.fromCallable {
        newsSource.news(language, country)
    }
}

interface NewsSource {
    fun news(language: String, country: String): DataSourceFactoryT<NewsKey, News>
}
