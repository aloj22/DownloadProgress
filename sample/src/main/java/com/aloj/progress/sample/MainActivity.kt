package com.aloj.progress.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar.isEnabled = false
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progressInt: Int, fromUser: Boolean) {
                progress.setProgress(progressInt / 100f)
                percentage.text = "$progressInt %"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        progress.progressListener = {
            Toast.makeText(this, if (it) R.string.download_started else R.string.download_stopped, Toast.LENGTH_SHORT).show()
            seekBar.isEnabled = it
        }

    }
}