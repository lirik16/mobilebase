package mdev.mobile.domain.interactor

import io.reactivex.Observable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

interface RxInteractor<in P, T> {
    operator fun invoke(param: P): Observable<T>
}

interface CoroutineInteractor<in P> {
    val dispatcher: CoroutineDispatcher

    suspend operator fun invoke(param: P)
}

fun <P> CoroutineScope.launchInteractor(interactor: CoroutineInteractor<P>, param: P): Job {
    return launch(coroutineContext + interactor.dispatcher) { interactor(param) }
}

fun CoroutineScope.launchInteractor(interactor: CoroutineInteractor<Unit>) = launchInteractor(interactor, Unit)
