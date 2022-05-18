package kosmicbor.myserviceapplication.utils

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MyIntentService : IntentService("MyIntentService") {

    companion object {
        const val ACTION_MY_INTENT_SERVICE = "myIntentservice.ACTION"
        const val INTENT_SERVICE_MESSAGE_TAG = "INTENT_SERVICE_MESSAGE_TAG"
        private const val RESULT = "RESULT"
    }

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(p0: Intent?) {

        Log.d("@Service", "onHandleIntent() called with: p0 = $p0")
        val string = p0?.extras?.get(INTENT_SERVICE_MESSAGE_TAG)

        val sb = StringBuilder()
            .append("It is ")
            .append(string)
            .append(" in ")
            .append("Thread with ID: ")
            .append(Thread.currentThread().id)


        val intent = Intent(ACTION_MY_INTENT_SERVICE).apply {
            putExtra(RESULT, sb.toString())
        }

        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
    }
}