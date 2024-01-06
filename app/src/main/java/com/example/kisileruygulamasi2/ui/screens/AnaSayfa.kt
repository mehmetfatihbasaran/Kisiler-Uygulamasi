package com.example.kisileruygulamasi2.ui.screens

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kisileruygulamasi2.R
import com.example.kisileruygulamasi2.data.Kisiler
import com.example.kisileruygulamasi2.view_model.AnaSayfaViewModel
import com.example.kisileruygulamasi2.view_model.factory.AnaSayfaFactory
import com.google.gson.Gson

@Composable
fun AnaSayfa(navController: NavController, gelenKisi: Kisiler? = null) {

    val aramaYapiliyorMu = remember { mutableStateOf(false) }
    val tf = remember { mutableStateOf("") }

    val context = LocalContext.current
    val viewModel: AnaSayfaViewModel = viewModel(
        factory = AnaSayfaFactory(context.applicationContext as Application)
    )

    val kisilerList = viewModel.kisilerListesi.observeAsState(initial = listOf())
    val CRUDkisilerList = viewModel.CRUDkisilerList.observeAsState(initial = listOf())

    LaunchedEffect(key1 = true) {
        viewModel.kisileriYukle()
        viewModel.CRUDkisileriYukle()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (aramaYapiliyorMu.value) {
                        TextField(
                            value = tf.value,
                            onValueChange = {
                                tf.value = it
                                viewModel.ara(it)
                            },
                            label = { Text(text = "Ara") },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = colorResource(id = R.color.teal_200),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedLabelColor = Color.Transparent,
                                unfocusedLabelColor = Color.Transparent
                            )
                        )
                    } else {
                        Text(text = "Kisiler")
                    }
                },
                actions = {
                    if (aramaYapiliyorMu.value) {
                        IconButton(
                            onClick = {
                                aramaYapiliyorMu.value = false
                                tf.value = ""
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.kapat),
                                contentDescription = "Kapat",
                                tint = Color.White
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            aramaYapiliyorMu.value = true
                        }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.arama),
                                contentDescription = "Arama",
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("kisiKayitSayfa")
                },
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
        LazyColumn(modifier = Modifier.padding(it)) {
            items(CRUDkisilerList.value) {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            val kisiJson = Gson().toJson(it)
                            navController.navigate("kisiDetaySayfa/$kisiJson")
                            // navController.currentBackStackEntry?.savedStateHandle?.set("kisi", kisiJson)
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = it.kisi_ad + " - " + it.kisi_tel)
                        IconButton(
                            onClick = {
                                viewModel.sil(it.kisi_id)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.silme),
                                contentDescription = "Silme",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}