package com.example.kisileruygulamasi2.ui.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kisileruygulamasi2.R
import com.example.kisileruygulamasi2.data.Kisiler
import com.example.kisileruygulamasi2.view_model.KisiDetayViewModel
import com.example.kisileruygulamasi2.view_model.factory.KisiDetayFactory
import com.google.gson.Gson

@Composable
fun KisiDetaySayfa(gelenKisi: Kisiler, navController: NavController) {

    val kisiAd = remember { mutableStateOf("") }
    val kisiTel = remember { mutableStateOf("") }
    val localFocusManager = LocalFocusManager.current

    val context = LocalContext.current
    val viewModel: KisiDetayViewModel = viewModel(
        factory = KisiDetayFactory(context.applicationContext as Application)
    )

    LaunchedEffect(key1 = true,){
        kisiAd.value = gelenKisi.kisi_ad
        kisiTel.value = gelenKisi.kisi_tel
    }

    Scaffold(
        topBar = {
            TopAppBar {
                Text(text = "Kisi Detay Sayfa")
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                backgroundColor = colorResource(id = R.color.teal_200)
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "Ekle",
                    tint = Color.White
                )
            }
        }
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
                    val guncelKisi = Kisiler(gelenKisi.kisi_id, kisi_ad, kisi_tel)
                    val kisiJson = Gson().toJson(guncelKisi)
                    viewModel.guncelle(guncelKisi.kisi_id, guncelKisi.kisi_ad, guncelKisi.kisi_tel)
                    navController.navigate("anaSayfa/$kisiJson")
                    localFocusManager.clearFocus()
                },
                modifier = Modifier.size(250.dp, 50.dp)
            ) {
                Text(text = "Güncelle")
            }
        }
    }
}