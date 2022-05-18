package kosmicbor.myserviceapplication.utils

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.util.Log

abstract class CustomService(name: String) : Service() {

    companion object {
        private const val TAG = "@Service"
    }

    private val thread = HandlerThread("$name's thread")

    @Volatile
    private lateinit var looper: Looper

    @Volatile
    private lateinit var handler: Handler

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    abstract fun onHandleIntent(intent: Intent?)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        handler.postAtFrontOfQueue {
            onHandleIntent(intent)
        }

        handler.post {
            stopSelf()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {

        super.onCreate()
        Log.d(TAG, "onCreate() called")

        thread.start()

        looper = thread.looper
        handler = Handler(looper)
    }

    @Deprecated("Deprecated in Java")
    override fun onStart(intent: Intent?, startId: Int) {
        Log.d(TAG, "onStart() called with: intent = $intent, startId = $startId")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}