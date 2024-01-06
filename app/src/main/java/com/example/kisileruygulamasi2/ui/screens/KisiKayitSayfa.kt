package com.example.kisileruygulamasi2.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kisileruygulamasi2.data.Kisiler
import com.example.kisileruygulamasi2.view_model.KisiKayitViewModel
import com.example.kisileruygulamasi2.view_model.factory.KisiKayitFactory
import com.google.gson.Gson
import java.lang.Math.random

@Composable
fun KisiKayitSayfa(navController: NavController) {

    val kisiAd = remember { mutableStateOf("") }
    val kisiTel = remember { mutableStateOf("") }
    val localFocusManager = LocalFocusManager.current

    val context = LocalContext.current
    val viewModel: KisiKayitViewModel = viewModel(
        factory = KisiKayitFactory(context.applicationContext as Application)
    )

    Scaffold(
        topBar = {
            TopAppBar {
                Text(text = "Kisi Kayit Sayfa")
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = kisiAd.value,
                onValueChange = { kisiAd.value = it },
                label = { Text(text = "Kişi Ad") }
            )
            TextField(
                value = kisiTel.value,
                onValueChange = { kisiTel.value = it },
                label = { Text(text = "Kişi Tel") }
            )
            Button(
                onClick = {
                    val kisi_ad = kisiAd.value
                    val kisi_tel = kisiTel.value
                    val kisi_id = random().toInt()
                    val gidenKisi = Kisiler(kisi_id, kisi_ad, kisi_tel)
                    val kisiJson = Gson().toJson(gidenKisi)
                    navController.navigate("anaSayfa/$kisiJson")
                    viewModel.kayit(kisi_ad, kisi_tel)
                    localFocusManager.clearFocus()
                },
                modifier = Modifier.size(250.dp, 50.dp)
            ) {
                Text(text = "Kaydet")
            }
        }
    }

}