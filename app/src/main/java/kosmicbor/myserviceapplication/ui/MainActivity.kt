package kosmicbor.myserviceapplication.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kosmicbor.myserviceapplication.databinding.ActivityMainBinding
import kosmicbor.myserviceapplication.utils.MyCustomService
import kosmicbor.myserviceapplication.utils.MyIntentService

class MainActivity : AppCompatActivity() {

    companion object {
        private const val RESULT = "RESULT"
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var broadcastReceiver: MainBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        initButtonClickListeners()
    }

    override fun onResume() {
        super.onResume()
        initServiceBroadcastReceiver()
    }

    private fun initServiceBroadcastReceiver() {

        broadcastReceiver = MainBroadcastReceiver()

        val intentFilter = IntentFilter(MyIntentService.ACTION_MY_INTENT_SERVICE).apply {
            addAction(MyCustomService.ACTION_MY_CUSTOM_SERVICE)
        }

        LocalBroadcastManager
            .getInstance(applicationContext)
            .registerReceiver(broadcastReceiver, intentFilter)
    }

    private fun startDefaultIntentService() {
        val intent = Intent(applicationContext, MyIntentService::class.java).apply {
            putExtra(MyIntentService.INTENT_SERVICE_MESSAGE_TAG, "IntentService")
        }

        startService(intent)
    }

    private fun startCustomService() {
        val intent = Intent(applicationContext, MyCustomService::class.java).apply {
            putExtra(MyCustomService.CUSTOM_SERVICE_MESSAGE_TAG, "CustomService")
        }

        startService(intent)
    }

    private fun initButtonClickListeners() {
        binding.apply {
            intentServiceButton.setOnClickListener {
                startDefaultIntentService()
            }

            customServiceButton.setOnClickListener {
                startCustomService()
            }
        }
    }

    inner class MainBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            val messageFromIntentService = intent?.extras?.getString(RESULT)

            binding.resultTextview.text = messageFromIntentService
        }
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager
            .getInstance(applicationContext)
            .unregisterReceiver(broadcastReceiver)
    }
}