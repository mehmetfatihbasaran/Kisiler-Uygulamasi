package com.example.kisileruygulamasi2.data.crud

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CRUDKisiler(
    @SerializedName("kisi_id")
    @Expose
    var kisi_id: Int,
    @SerializedName("kisi_ad")
    @Expose
    var kisi_ad: String,
    @SerializedName("kisi_tel")
    @Expose
    var kisi_tel: String
)
