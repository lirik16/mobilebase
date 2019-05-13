package mdev.mobile.base.kotlin.extensions

import io.reactivex.Observable
import io.reactivex.Single

fun <T> Observable<T>.toSet(): Single<Set<T>> = collect({ HashSet<T>() }, { t1, t2 -> t1.add(t2) }).map { it as Set<T> }
