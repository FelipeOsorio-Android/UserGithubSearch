package devandroid.felipe.usergithubsearch.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import devandroid.felipe.usergithubsearch.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.hide()
        window.statusBarColor = Color.TRANSPARENT
    }
}