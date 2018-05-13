package com.besaba.anvarov.orenautoturn

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btCloseView.setOnClickListener { finish() }

        btKillApp.setOnClickListener {
            stopService(Intent(this, MyService::class.java))
            finish()
        }

        swRotate.isChecked = android.provider.Settings.System.getInt(contentResolver,
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1
        val onCheckedChangeListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
            when {
                isChecked -> Settings.System.putInt(contentResolver, Settings.System.ACCELEROMETER_ROTATION, 1)
                else -> Settings.System.putInt(contentResolver, Settings.System.ACCELEROMETER_ROTATION, 0)
            }
            startService(Intent(this@MainActivity, MyService::class.java))
        }
        swRotate.setOnCheckedChangeListener(onCheckedChangeListener)

        startService(Intent(this@MainActivity, MyService::class.java))
    }
}
