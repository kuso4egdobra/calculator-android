package com.kusok_dobra.calculator

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

class MainActivity : BaseActivity() {

    companion object {
        const val DEFAULT_NUM_AFTER_POINT = 2
    }

    private var numAfterPnt = DEFAULT_NUM_AFTER_POINT;

    private val getSettingsResult = registerForActivityResult(SettingsResult()) { result ->
        Toast.makeText(this, "Результат $result", Toast.LENGTH_LONG).show()
        numAfterPnt = result ?: DEFAULT_NUM_AFTER_POINT
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val settings: ImageView = findViewById(R.id.settings_ImageView)
        settings.setOnClickListener {
            openSettings()
        }

        val history: ImageView = findViewById(R.id.history_imageView)
        history.setOnClickListener {
            openHistory()
        }
    }

    private fun openSettings() {
        getSettingsResult.launch(numAfterPnt);
    }

    private fun openHistory() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }
}

