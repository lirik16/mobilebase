package mdev.mobile.data.network.rest

import mdev.mobile.data.network.rest.models.SCNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val OFFSET_QUERY: String = "offset"
private const val LIMIT_QUERY: String = "limit"
private const val COUNTRY_QUERY: String = "country"
private const val LANG_QUERY: String = "lang"

const val API_VERSION_1 = "v0.1/"

interface RestApi {
    @GET(API_VERSION_1 + "news")
    fun news(
        @Query(OFFSET_QUERY) offset: Int,
        @Query(LIMIT_QUERY) limit: Int,
        @Query(COUNTRY_QUERY) country: String?,
        @Query(LANG_QUERY) lang: String?
    ): Call<List<SCNews>>
}
