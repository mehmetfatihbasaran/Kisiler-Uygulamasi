package com.example.kisileruygulamasi2.view_model.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kisileruygulamasi2.view_model.KisiKayitViewModel

class KisiKayitFactory(var application: Application): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return KisiKayitViewModel(application) as T
    }
}