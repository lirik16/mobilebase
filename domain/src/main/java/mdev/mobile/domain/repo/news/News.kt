package mdev.mobile.domain.repo.news

import mdev.mobile.domain.repo.IdType
import org.threeten.bp.OffsetDateTime

data class Author(
    val id: IdType,
    val name: String,
    val screenName: String?,
    val uri: String?,
    val imageUri: String?,
    val isVerified: Boolean
)

enum class NewsType {
    BLOG, TWITTER, SITE, GROUP;
}

enum class NewsTier {
    FIRST, SECOND
}

data class SiteContent(
    val createdTime: OffsetDateTime,
    val title: String,
    val text: String,
    val uri: String,
    val imageUri: String?
)

data class News(
    val id: IdType,
    val channelId: IdType,
    val type: NewsType,
    val tier: NewsTier,
    val author: Author,
    val siteContent: SiteContent
)
