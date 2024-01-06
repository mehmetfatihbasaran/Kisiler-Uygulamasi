package com.example.kisileruygulamasi2.data.crud

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class KisilerCevap(
    @SerializedName("kisiler")
    @Expose
    var kisiler: List<CRUDKisiler>,
    @SerializedName("success")
    @Expose
    var success: Int
)
