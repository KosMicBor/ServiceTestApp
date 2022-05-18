package kosmicbor.myserviceapplication.utils

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MyCustomService : CustomService("CustomService") {

    companion object {
        const val ACTION_MY_CUSTOM_SERVICE = "mycustomservice.ACTION"
        const val CUSTOM_SERVICE_MESSAGE_TAG = "CUSTOM_SERVICE_MESSAGE_TAG"
        private const val RESULT = "RESULT"
    }

    override fun onHandleIntent(intent: Intent?) {
        val message = intent?.extras?.get(CUSTOM_SERVICE_MESSAGE_TAG)

        val sb = StringBuilder()
            .append("It is ")
            .append(message)
            .append(" in ")
            .append("${Thread.currentThread()}")

        val outerIntent = Intent(ACTION_MY_CUSTOM_SERVICE).apply {
            putExtra(RESULT, sb.toString())
        }

        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(outerIntent)
    }
}