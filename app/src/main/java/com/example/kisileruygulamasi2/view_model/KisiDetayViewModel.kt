package com.example.kisileruygulamasi2.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.kisileruygulamasi2.data.repo.KisilerDaoRepository

class KisiDetayViewModel(application: Application): AndroidViewModel(application){

    var kisiRepo = KisilerDaoRepository(application)

    fun guncelle(kisi_id: Int, kisi_ad: String, kisi_tel: String){
        kisiRepo.kisiGuncelle(kisi_id,kisi_ad,kisi_tel)
    }

}