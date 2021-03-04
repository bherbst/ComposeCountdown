package com.example.androiddevchallenge

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun Duration.hoursComponent(): Int = toComponents { hours, _, _, _ -> hours }

@OptIn(ExperimentalTime::class)
fun Duration.minutesComponent(): Int = toComponents { _, minutes, _, _ -> minutes }

@OptIn(ExperimentalTime::class)
fun Duration.secondsComponent(): Int = toComponents { _, _, seconds, _ -> seconds }