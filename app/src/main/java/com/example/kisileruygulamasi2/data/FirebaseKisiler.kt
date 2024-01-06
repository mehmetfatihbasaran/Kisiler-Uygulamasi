package com.example.kisileruygulamasi2.data

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class FirebaseKisiler(
    @get:Exclude @set:Exclude
    var kisi_id: String = "",
    var kisi_ad: String = "",
    var kisi_tel: String = ""
)