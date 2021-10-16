/**
 * (C) Copyright 2018 Paulo Vitor Sato Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package dev.kdblib.kdbexemple.usecase

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 * <p>
 * By convention each UseCase implementation will return the result using a coroutine
 * that will execute its job in a runAsync thread and will post the result in the UI thread.
 */

typealias CompletionBlock<TYPE> = UseCase.Result<TYPE>.() -> Unit

abstract class UseCase<REQUEST, RESPONSE> {

    private var parentJob: Job? = null
    private var backgroundContext: CoroutineContext = Dispatchers.IO
    private var foregroundContext: CoroutineContext = Dispatchers.Main
    private var useCaseScope: CoroutineScope = CoroutineScope(foregroundContext)

    internal abstract suspend fun executeOnBackground(request: REQUEST): RESPONSE

    operator fun invoke(scope: CoroutineScope, request: REQUEST, block: CompletionBlock<RESPONSE>) {
        val response = Result<RESPONSE>().apply { block() }
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            response(throwable)
        }
        unsubscribe()
        useCaseScope = scope + foregroundContext + exceptionHandler
        parentJob = useCaseScope.launch {
            val result = withContext(backgroundContext) {
                executeOnBackground(request)
            }
            response(result)
        }
    }

    private fun unsubscribe() {
        parentJob?.apply {
            cancelChildren()
            cancel()
        }
    }

    protected suspend fun <UNKNOWN> runAsync(
        context: CoroutineContext = backgroundContext,
        block: suspend () -> UNKNOWN
    ): Deferred<UNKNOWN> {
        return useCaseScope.async(context) {
            block()
        }
    }

    class Result<TYPE> {
        private var onComplete: ((TYPE) -> Unit)? = null
        private var onError: ((Throwable) -> Unit)? = null

        fun onComplete(block: (TYPE) -> Unit) {
            onComplete = block
        }

        fun onError(block: (Throwable) -> Unit) {
            onError = block
        }

        operator fun invoke(result: TYPE) {
            onComplete?.invoke(result)
        }

        operator fun invoke(error: Throwable) {
            onError?.invoke(error)
        }
    }
}
