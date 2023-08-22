package org.techtown.handtxver1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MyDataLoading : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_data_loading)

        val fragment = BottomMenuBar(0)

        supportFragmentManager.beginTransaction().add(R.id.menuBar, fragment).commit()

    }

    override fun onResume() {
        super.onResume()

        val fragment = BottomMenuBar(0)

        supportFragmentManager.beginTransaction().add(R.id.menuBar, fragment).commit()

    }
}