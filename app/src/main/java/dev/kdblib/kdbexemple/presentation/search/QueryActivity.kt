package dev.kdblib.kdbexemple.presentation.search

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import dev.kdblib.kdbexemple.R
import dev.kdblib.kdbexemple.infrastructure.bindView

class QueryActivity : AppCompatActivity() {
    val searchButton: Button by bindView(R.id.search_button)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_content, QueryFragment())
                    .commit()
        }
    }
}
