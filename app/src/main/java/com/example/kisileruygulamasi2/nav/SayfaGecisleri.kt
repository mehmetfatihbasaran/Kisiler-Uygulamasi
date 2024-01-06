package com.example.kisileruygulamasi2.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kisileruygulamasi2.data.Kisiler
import com.example.kisileruygulamasi2.ui.screens.AnaSayfa
import com.example.kisileruygulamasi2.ui.screens.KisiDetaySayfa
import com.example.kisileruygulamasi2.ui.screens.KisiKayitSayfa
import com.google.gson.Gson

@Composable
fun SayfaGecisleri() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "anaSayfa/{kisi1}") {
        composable(
            route = "anaSayfa/{kisi1}",
            arguments = listOf(navArgument("kisi1") { type = NavType.StringType })
        ) {
            val json = it.arguments?.getString("kisi1")
            val gelenKisi = Gson().fromJson(json, Kisiler::class.java)
            if (gelenKisi != null) {
                AnaSayfa(navController = navController, gelenKisi = gelenKisi)
            } else {
                AnaSayfa(navController = navController)
            }
        }
        composable(route = "kisiKayitSayfa") {
            KisiKayitSayfa(navController = navController)
        }
        composable(
            route = "kisiDetaySayfa/{kisi}",
            arguments = listOf(navArgument("kisi") { type = NavType.StringType })
        ) { it ->
            val json = it.arguments?.getString("kisi")
            val gelenKisi = Gson().fromJson(json, Kisiler::class.java)
            KisiDetaySayfa(gelenKisi = gelenKisi, navController = navController)
        }

    }

}

/*
composeable("kisiDetaySayfa/{kisi}",
arguments = listOf(navArgument("kisi"){ type = NavType.StringType }
) { backStackEntry ->
val json = it.arguments?.getString("kisi")
val gelenKisi = Gson().fromJson(json, Kisiler::class.java)
KisiDetaySayfa(gelenKisi = gelenKisi)
}
 */