package com.besaba.anvarov.orenautoturn

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootBroadReceiv : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.startService(Intent(context, MyService::class.java))
    }
}
