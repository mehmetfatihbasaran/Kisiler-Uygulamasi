package com.example.kisileruygulamasi2.data.repo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.kisileruygulamasi2.data.FirebaseKisiler
import com.example.kisileruygulamasi2.data.Kisiler
import com.example.kisileruygulamasi2.data.crud.CRUDCevap
import com.example.kisileruygulamasi2.data.crud.CRUDKisiler
import com.example.kisileruygulamasi2.data.crud.KisilerCevap
import com.example.kisileruygulamasi2.data.room.Veritabani
import com.example.kisileruygulamasi2.retrofit.AppUtils
import com.example.kisileruygulamasi2.retrofit.RetrofitKisilerDao
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KisilerDaoRepository(var application: Application) {

    var kisilerListesi = MutableLiveData<List<Kisiler>>()
    var vt: Veritabani
    var retrofitKisilerDao: RetrofitKisilerDao
    var CRUDkisilerListesi = MutableLiveData<List<CRUDKisiler>>()
    var kisilerRef: DatabaseReference

    init {
        retrofitKisilerDao = AppUtils.getRetrofitKisilerDao()
        vt = Veritabani.veritabaniErisim(application)!!
        kisilerListesi = MutableLiveData()
        CRUDkisilerListesi = MutableLiveData()
        val db = FirebaseDatabase.getInstance()
        kisilerRef = db.getReference("kisiler")
    }

    fun kisileriGetir(): MutableLiveData<List<Kisiler>> {
        return kisilerListesi
    }

    fun CRUDkisileriGetir(): MutableLiveData<List<CRUDKisiler>> {
        return CRUDkisilerListesi
    }

    fun tumKisileriAl() {
        CoroutineScope(Dispatchers.Main).launch {
            kisilerListesi.value = vt.kisilerDao().tumKisiler()
        }
    }

    fun retrofitTumKisileriAl() {
        retrofitKisilerDao.tumKisiler().enqueue(object : Callback<KisilerCevap> {
            override fun onResponse(call: Call<KisilerCevap>, response: Response<KisilerCevap>) {
                if (response.isSuccessful) {
                    val kisilerCevap = response.body()!!.kisiler
                    // Cevap başarılı ise burada yapılacak işlemleri gerçekleştirin
                    CRUDkisilerListesi.value = kisilerCevap
                } else {
                    // Cevap başarısız ise burada hata işleme yapabilirsiniz
                }
            }

            override fun onFailure(call: Call<KisilerCevap>, t: Throwable) {
                // İstek başarısız olduğunda burada hata işleme yapabilirsiniz
            }
        })

    }

    fun firebaseKisileriAl() {
        kisilerRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val liste = ArrayList<Kisiler>()
                for (d in snapshot.children) {
                    val kisi = dataSnapshotToKisiler(d)
                    if (kisi != null) {
                        liste.add(kisi)
                    }
                }
                kisilerListesi.value = kisilerListesi.value?.plus(liste)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun dataSnapshotToKisiler(dataSnapshot: DataSnapshot): Kisiler? {
        val kisi = dataSnapshot.getValue(Kisiler::class.java)
        if (kisi != null) {
            kisi.kisi_id = (dataSnapshot.key ?: "") as Int
        }
        return kisi
    }


    fun kisiAra(aramaKelimesi: String) {

        CoroutineScope(Dispatchers.Main).launch {
            kisilerListesi.value = vt.kisilerDao().kisiArama(aramaKelimesi)
        }

        retrofitKisilerDao.kisiAra(aramaKelimesi).enqueue(object : Callback<KisilerCevap> {
            override fun onResponse(call: Call<KisilerCevap>, response: Response<KisilerCevap>) {
                if (response.isSuccessful) {
                    val kisilerCevap = response.body()!!.kisiler
                    // Cevap başarılı ise burada yapılacak işlemleri gerçekleştirin
                    CRUDkisilerListesi.value = kisilerCevap
                } else {
                    // Cevap başarısız ise burada hata işleme yapabilirsiniz
                }
            }

            override fun onFailure(call: Call<KisilerCevap>, t: Throwable) {
            }

        }
        )
    }

    fun kisiKayit(kisi_ad: String, kisi_tel: String) {

        CoroutineScope(Dispatchers.Main).launch {
            val yeniKisi = Kisiler(0, kisi_ad, kisi_tel)
            vt.kisilerDao().kisiEkle(yeniKisi)
        }

        retrofitKisilerDao.kisiEkle(kisi_ad, kisi_tel).enqueue(object : Callback<CRUDCevap> {
            override fun onResponse(call: Call<CRUDCevap>, response: Response<CRUDCevap>) {
                if (response.isSuccessful) {
                    retrofitTumKisileriAl()
                } else {
                    // Cevap başarısız ise burada hata işleme yapabilirsiniz
                }
            }

            override fun onFailure(call: Call<CRUDCevap>, t: Throwable) {
            }

        }
        )

        val yeniKisi = FirebaseKisiler("", kisi_ad, kisi_tel)
        kisilerRef.push().setValue(yeniKisi)

    }

    fun kisiGuncelle(kisi_id: Int, kisi_ad: String, kisi_tel: String) {

        CoroutineScope(Dispatchers.Main).launch {
            val guncellenenKisi = Kisiler(kisi_id, kisi_ad, kisi_tel)
            vt.kisilerDao().kisiGuncelle(guncellenenKisi)
        }

        retrofitKisilerDao.kisiGuncelle(kisi_id, kisi_ad, kisi_tel)
            .enqueue(object : Callback<CRUDCevap> {
                override fun onResponse(call: Call<CRUDCevap>, response: Response<CRUDCevap>) {
                    if (response.isSuccessful) {
                        retrofitTumKisileriAl()
                    } else {
                        // Cevap başarısız ise burada hata işleme yapabilirsiniz
                    }
                }

                override fun onFailure(call: Call<CRUDCevap>, t: Throwable) {
                }

            }
            )

    }

    fun kisiSil(kisi_id: Int) {

        CoroutineScope(Dispatchers.Main).launch {
            val silinenKisi = Kisiler(kisi_id, "", "")
            vt.kisilerDao().kisiSil(silinenKisi)
            tumKisileriAl()
        }

        retrofitKisilerDao.kisiSil(kisi_id).enqueue(object : Callback<CRUDCevap> {
            override fun onResponse(call: Call<CRUDCevap>, response: Response<CRUDCevap>) {
                if (response.isSuccessful) {
                    retrofitTumKisileriAl()
                } else {
                    // Cevap başarısız ise burada hata işleme yapabilirsiniz
                }
            }

            override fun onFailure(call: Call<CRUDCevap>, t: Throwable) {
            }

        }
        )
    }

}






