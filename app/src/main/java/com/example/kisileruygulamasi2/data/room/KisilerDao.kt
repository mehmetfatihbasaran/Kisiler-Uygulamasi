package com.example.kisileruygulamasi2.data.room

import androidx.room.*
import com.example.kisileruygulamasi2.data.Kisiler

@Dao
interface KisilerDao {

    @Query("SELECT * FROM kisiler")
    suspend fun tumKisiler():List<Kisiler>

    @Insert
    suspend fun kisiEkle(kisiler: Kisiler)

    @Update
    suspend fun kisiGuncelle(kisiler: Kisiler)

    @Delete
    suspend fun kisiSil(kisiler: Kisiler)

    @Query("SELECT * FROM kisiler WHERE kisi_ad like '%' || :aramaKelimesi || '%'")
    suspend fun kisiArama(aramaKelimesi:String):List<Kisiler>

}