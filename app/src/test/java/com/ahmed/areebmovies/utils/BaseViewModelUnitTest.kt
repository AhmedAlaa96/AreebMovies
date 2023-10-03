package com.ahmed.areebmovies.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope

@Suppress("DEPRECATION")
@OptIn(ExperimentalCoroutinesApi::class)
open class BaseViewModelUnitTest: BaseUnitTest() {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    suspend fun test(onEvent:() ->Unit, onState:suspend ()-> Unit) {

        val testScope = TestCoroutineScope(testCoroutineDispatcher)
        val collectJob = testScope.launch {
           onState.invoke()
        }
        onEvent.invoke()
        testCoroutineDispatcher.advanceUntilIdle()
        collectJob.join()
        testScope.cleanupTestCoroutines()
    }

}