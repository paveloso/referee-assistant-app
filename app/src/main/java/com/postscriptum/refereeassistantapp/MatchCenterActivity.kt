package com.postscriptum.refereeassistantapp

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

class MatchCenterActivity : AppCompatActivity() {

    lateinit var clockText: TextView
    lateinit var chronometer: Chronometer

    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_center)

        chronometer = findViewById(R.id.chrono_view)
        // this allows to set starting point for timer (can be depended on 1st half or 2nd half)
        val baseTime = chronometer.base
        chronometer.base = baseTime - (45 * 60 * 1000)
        chronometer.setOnChronometerTickListener { formatText() }

        clockText = findViewById(R.id.clock_text)
        val startWhistleButton: Button = findViewById(R.id.start_whistle_button)
        startWhistleButton.setOnClickListener { startWhistleHandler() }
    }

    private fun startWhistleHandler() {
        // this allows to set starting point for timer (can be depended on 1st half or 2nd half)
        val baseTime = chronometer.base
        chronometer.base = baseTime - (45 * 60 * 1000)

        if (isRunning) {
            chronometer.stop()
            formatText()
            isRunning = false
        } else {
            chronometer.start()
            isRunning = true
        }
    }

    private fun formatText() {
        val baseTime = chronometer.base
        val time = SystemClock.elapsedRealtime() - baseTime
        val h = (time / 3600000).toInt()
        val m = TimeUnit.MILLISECONDS.toMinutes(time)
        val s = TimeUnit.MILLISECONDS.toSeconds(time) % 60

        chronometer.text = String.format("%02d:%02d", m + (h * 60), s)
    }
}