package mdev.mobile.domain.repo.locale

import io.reactivex.Observable

class LocaleRepo(private val localeSource: LocaleSource) {
    fun language() = localeSource.language()

    fun getCountry() = localeSource.getCountry()
}

interface LocaleSource {
    fun language(): Observable<String>

    fun getCountry(): String
}
