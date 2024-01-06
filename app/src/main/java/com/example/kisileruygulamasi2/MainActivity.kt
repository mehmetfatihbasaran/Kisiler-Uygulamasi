package com.example.kisileruygulamasi2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.kisileruygulamasi2.nav.SayfaGecisleri
import com.example.kisileruygulamasi2.ui.theme.KisilerUygulamasi2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KisilerUygulamasi2Theme {
                SayfaGecisleri()
            }
        }
    }
}
