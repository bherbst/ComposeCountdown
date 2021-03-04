package com.example.androiddevchallenge

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.fold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@OptIn(ExperimentalTime::class)
class Timer(val duration: Duration) {
  private val secondsTicker = ticker(
    delayMillis = 1000, // tick every second
    initialDelayMillis = 0
  )

  var remaining by mutableStateOf(duration)
  var isRunning by mutableStateOf(false)

  private lateinit var secondsChannel: ReceiveChannel<Unit>

  suspend fun start() {
    secondsChannel = ticker(
      delayMillis = 1000, // tick every second
      initialDelayMillis = 0
    )

    isRunning = true

    for (event in secondsChannel) {
      remaining = remaining.minus(1.seconds)

      if (remaining.inSeconds <= 0) {
        secondsChannel.cancel()
      }
    }
  }

  fun pause() {
    isRunning = false
    secondsChannel.cancel()
  }
}