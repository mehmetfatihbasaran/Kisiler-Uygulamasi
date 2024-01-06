package com.example.kisileruygulamasi2.retrofit

class AppUtils {

    companion object{

        private const val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getRetrofitKisilerDao(): RetrofitKisilerDao {
            return RetrofitClient.getClient(BASE_URL).create(RetrofitKisilerDao::class.java)
        }

    }

}