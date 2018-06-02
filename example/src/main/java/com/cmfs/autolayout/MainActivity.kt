package com.cmfs.autolayout

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activityAutoLayout = findViewById<Button>(R.id.activityAutoLayout)
        val activityManualLayout = findViewById<Button>(R.id.activityManualLayout)
        val fragmentLayout = findViewById<Button>(R.id.fragmentLayout)

        activityAutoLayout.setOnClickListener {
            startActivity(Intent(this@MainActivity, AutoLayoutExampleActivity::class.java))
        }

        activityManualLayout.setOnClickListener {
            startActivity(Intent(this@MainActivity, ManualLayoutExampleActivity::class.java))
        }

        fragmentLayout.setOnClickListener {
            startActivity(Intent(this@MainActivity, FragmentLayoutExampleActivity::class.java))
        }

    }
}
