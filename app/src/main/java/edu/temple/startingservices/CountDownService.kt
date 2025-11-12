package edu.temple.startingservices

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ConutDownServerice : Service() {

    companion object{
        const val TAG = "Countdown"
        const val EXTRA_SECONDS = "extra_seconds"
    }

    private val serviceJob = SupervisorJob()
    private val serviceScope=CoroutineScope(Dispatchers.Default + serviceJob)
    private var currentCountdown: Job? = null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val seconds = intent?.getIntExtra(EXTRA_SECONDS, 0) ?: 0
        Log.i(TAG, "Countdown started for $seconds seconds")

        if(seconds <= 0){
            Log.i(TAG,"Stopped")
            stopSelfResult(startId)
            return START_NOT_STICKY
        }

        currentCountdown?.cancel()
        currentCountdown = serviceScope.launch {
            try{
                for (t in seconds downTo 0){
                    Log.i(TAG, "Countdown $t")
                    delay(1000L)
                }
                Log.i(TAG, "Countdown Completed")
            }catch (_: CancellationException){
                Log.i(TAG,"Cancelled")
            }finally {
                stopSelf(startId)
            }
        }
        return START_NOT_STICKY
    }
    override fun onBind(intent: Intent): IBinder? = null

    override fun onDestroy() {
        currentCountdown?.cancel()
        serviceJob.cancel()
        Log.i(TAG,"Service Destroyed")
    super.onDestroy()
    }
}