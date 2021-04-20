package com.koshake1.mytranslator.rx

import io.reactivex.Scheduler

interface ISchedulerProvider {

    fun ui(): Scheduler
    fun io(): Scheduler
}