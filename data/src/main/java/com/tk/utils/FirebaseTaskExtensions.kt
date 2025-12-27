package com.tk.utils

import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeoutException

suspend fun <T> Task<T>.suspendCoroutineResult(timeoutMillis: Long = 30000): T =
    suspendCoroutine { continuation ->
        var completed = false
        
        addOnSuccessListener { result ->
            if (!completed) {
                completed = true
                continuation.resume(result)
            }
        }
        
        addOnFailureListener { e ->
            if (!completed) {
                completed = true
                continuation.resumeWithException(e)
            }
        }
        
        CoroutineScope(Dispatchers.IO).launch {
            delay(timeoutMillis)
            if (!completed) {
                completed = true
                continuation.resumeWithException(
                    TimeoutException("Firebase task timed out after ${timeoutMillis}ms")
                )
            }
        }
    }