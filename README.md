# Koroutine

## 什么是协程
简言之就是，可以由程序自行控制挂起和恢复的程序

## 协程可以干什么
1、协程可以实现多任务的协作执行

2、协程可以控制异步任务控制流的灵活转移

## 协程的作用
1、协程可以让异步代码同步化

2、协程可以降低异步程序的复杂度

## 协程 VS 线程

## 协程学习 ```三部曲```
1、协程的概念

2、协程的基本要素

3、协程框架的应用

## 协程分类
1、调用栈
    
    有栈协程：每个协程都会分配单独的调用栈，类似线程的调用栈
    
    无栈协程：不会分配单独的调用栈，挂起点的恢复通过闭包或对象保存

2、调用关系

    对称协程：调度权可以转移给任意协程，协程之间是对等的
    
    非对称协程：调度权只能转移给调用自己的协程，协程存在父子关系
    

##　协程的创建
1、协程的创建

```kotlin
public fun <T> (suspend () -> T).createCoroutine(completion: Continuation<T>): Continuation<Unit> =
    SafeContinuation(createCoroutineUnintercepted(completion).intercepted(), COROUTINE_SUSPENDED)

public fun <R, T> (suspend R.() -> T).createCoroutine(receiver: R,completion: Continuation<T>): Continuation<Unit> =
    SafeContinuation(createCoroutineUnintercepted(receiver, completion).intercepted(), COROUTINE_SUSPENDED)
```

2、协程的启动
```kotlin
public fun <T> (suspend () -> T).startCoroutine(completion: Continuation<T>) {
    createCoroutineUnintercepted(completion).intercepted().resume(Unit)
}

public fun <R, T> (suspend R.() -> T).startCoroutine(receiver: R,completion: Continuation<T>) {
    createCoroutineUnintercepted(receiver, completion).intercepted().resume(Unit)
}
```

## 协程上下文
1、协程执行过程中需要携带数据

索引是CorotuineContext.Key

元素是CoroutineContext.Element

## 协程拦截器
1、拦截器CoroutineInterceptor是一类协程上下文的元素

2、可以对协程的Continuation进行拦截

## Continuation
1、SafeContinuation作用是确保resumeWith只会调用一次且如果在当前线程调用栈上则直接调用不会挂起

    注意：

        1、SafeContinuation仅在挂起点时出现

        2、ContinuationInterceptor在每次（恢复）执行协程体时调用

        3、SuspendLambda是协程函数体
