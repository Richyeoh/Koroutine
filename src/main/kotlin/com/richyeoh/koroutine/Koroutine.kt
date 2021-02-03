package com.richyeoh.koroutine

import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.createCoroutineUnintercepted

fun main() {
    launch {
        val image = loadImage()
        println("image=$image")
        image
    }
}

fun <T> launch(block: suspend () -> T) {
    val coroutine = block.createCoroutineUnintercepted(object : Continuation<T> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<T>) {
            println("resumeWith=$result")
        }
    })
    coroutine.resume(Unit)
}

suspend fun loadImage() = suspendCoroutine<String> {
    it.resume("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png")
}
