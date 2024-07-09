package com.mongs.wear

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mongs.wear.data.dataStore.MemberDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootRunReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val memberDataStore = MemberDataStore(context)
            CoroutineScope(Dispatchers.IO).launch {
                val walkingCount = memberDataStore.getWalkingCount()
                memberDataStore.setEndStepCount(endStepCount = 0)
                memberDataStore.setStartStepCount(startStepCount = -walkingCount)
            }
        }
    }
}