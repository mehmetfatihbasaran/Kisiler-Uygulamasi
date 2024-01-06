package com.example.kisileruygulamasi2.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.kisileruygulamasi2.data.repo.KisilerDaoRepository

class KisiKayitViewModel(application: Application): AndroidViewModel(application){

    var kisiRepo = KisilerDaoRepository(application)

    fun kayit(kisiAd: String, kisiTel: String){
        kisiRepo.kisiKayit(kisiAd, kisiTel)
    }

}