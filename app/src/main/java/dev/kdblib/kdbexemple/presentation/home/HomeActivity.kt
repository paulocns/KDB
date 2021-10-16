package dev.kdblib.kdbexemple.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.kdblib.kdbexemple.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_content, HomeFragment())
                    .commit()
        }
    }
}
