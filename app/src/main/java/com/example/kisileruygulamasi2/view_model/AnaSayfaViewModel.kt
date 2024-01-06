package com.example.kisileruygulamasi2.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.kisileruygulamasi2.data.Kisiler
import com.example.kisileruygulamasi2.data.crud.CRUDKisiler
import com.example.kisileruygulamasi2.data.repo.KisilerDaoRepository

class AnaSayfaViewModel(application: Application): AndroidViewModel(application) {

    var kisiRepo = KisilerDaoRepository(application)
    var kisilerListesi = MutableLiveData<List<Kisiler>>()
    var CRUDkisilerList = MutableLiveData<List<CRUDKisiler>>()

    init {
        kisileriYukle()
        CRUDkisileriYukle()
        kisilerListesi = kisiRepo.kisileriGetir()
        CRUDkisilerList = kisiRepo.CRUDkisileriGetir()
    }

    fun kisileriYukle(){
        kisiRepo.tumKisileriAl()
    }

    fun CRUDkisileriYukle(){
        kisiRepo.retrofitTumKisileriAl()
    }

    fun ara(kelime: String){
        kisiRepo.kisiAra(kelime)
    }

    fun sil(kisiId: Int){
        kisiRepo.kisiSil(kisiId)
    }

}