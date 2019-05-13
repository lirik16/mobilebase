package mdev.mobile.data.network.rest.models

import mdev.mobile.data.network.error.ServerResponseFormatError
import mdev.mobile.domain.repo.news.Author
import mdev.mobile.domain.repo.news.News
import mdev.mobile.domain.repo.news.NewsTier
import mdev.mobile.domain.repo.news.NewsType
import mdev.mobile.domain.repo.news.SiteContent
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

fun SCNews.toNews(): News {
    val newsType = when (type) {
        "blog" -> NewsType.BLOG
        "twitter" -> NewsType.TWITTER
        "site" -> NewsType.SITE
        else -> throw ServerResponseFormatError()
    }

    val newsTier = when (tier) {
        "first" -> NewsTier.FIRST
        "second" -> NewsTier.SECOND
        else -> throw ServerResponseFormatError()
    }

    val createdTime = OffsetDateTime.parse(content.createdTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    val siteContent = SiteContent(createdTime, content.title, content.text, content.uri, content.imageUri)

    return News(id, channelId, newsType, newsTier, author.toAuthor(), siteContent)
}

fun SCAuthor.toAuthor() = Author(id, name, screenName, uri, imageUri, isVerified)
