package mdev.mobile.app.util.common

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import io.reactivex.Observable
import mdev.mobile.app.appinitializers.EpoxyInitializer

// TODO: Do we need to change this parameters?
private const val PAGE_SIZE = 60
private const val PREFETCH_DISTANCE = 20

private val pagingConfig = PagedList.Config.Builder()
    .setPageSize(PAGE_SIZE)
    .setPrefetchDistance(PREFETCH_DISTANCE)
    .setEnablePlaceholders(false)
    .build()

fun <T> dataSourceToObservable(f: DataSource.Factory<Int, T>): Observable<PagedList<T>> {
    val pagedListBuilder = RxPagedListBuilder(f, pagingConfig)
    EpoxyInitializer.setSchedulers(pagedListBuilder)
    return pagedListBuilder.buildObservable()
}
