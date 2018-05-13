package com.besaba.anvarov.orenautoturn

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.provider.Settings
import android.support.v4.app.NotificationCompat

@Suppress("DEPRECATION")
class MyService : Service() {
    private lateinit var capOn: String
    private lateinit var capOff: String
    private lateinit var capSw: String
    private lateinit var mNM: NotificationManager

    override fun onCreate() {
        super.onCreate()
        mNM = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        capOn = getString(R.string.capOn)
        capOff = getString(R.string.capOff)
        capSw = getString(R.string.txtSwitch)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        sendNotif()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        mNM.cancel(NOTIFY_ID)
        super.onDestroy()
    }

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    private fun sendNotif() {
        val builder = NotificationCompat.Builder(this)
        builder.setContentTitle(capSw).setOngoing(true).setAutoCancel(true)

        if (android.provider.Settings.System.getInt(contentResolver, Settings.System.ACCELEROMETER_ROTATION,
                        0) == 0) {
            builder.setContentText(capOff)
            builder.setSmallIcon(R.drawable.ic_stat_device_screen_lock_rotation)
        } else {
            builder.setContentText(capOn)
            builder.setSmallIcon(R.drawable.ic_stat_device_screen_rotation)
        }

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)

        val notification = builder.build()
        notification.flags = notification.flags or Notification.FLAG_ONGOING_EVENT
        notification.flags = notification.flags or Notification.FLAG_NO_CLEAR
        mNM.notify(NOTIFY_ID, notification)
    }

    companion object {
        private const val NOTIFY_ID = 66
    }
}
