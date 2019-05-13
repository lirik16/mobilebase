package mdev.mobile.data.network.rest.models

import androidx.annotation.Keep

typealias SCId = Long

@Keep
data class RestError(val code: Int, val message: String)

@Keep
data class SCAuthor(
    val id: SCId,
    val name: String,
    val screenName: String?,
    val uri: String?,
    val imageUri: String?,
    val isVerified: Boolean
)

@Keep
data class SCNewsContent(
    val createdTime: String,
    val title: String,
    val text: String,
    val uri: String,
    val imageUri: String?
)

@Keep
data class SCNews(
    val id: SCId,
    val channelId: SCId,
    val tier: String,
    val type: String,
    val author: SCAuthor,
    val content: SCNewsContent
)
