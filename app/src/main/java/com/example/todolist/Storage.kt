package com.example.todolist

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.lang.reflect.Type

enum class Diretory{
    document,
    caches
    ;

    var url: String? = null
        get(){
            when(this){
                document -> return "/document/"
                caches -> return "/caches/"
            }
        }
}


class Storage {
    companion object{
        val TAG = "StorageManager"

        fun <T> retriveFromAsset(context: Context, fileName: String, type: Type): T?{
            val gson = Gson()
            try{
                var jsonString: String = context.assets.open(fileName).bufferedReader().use {
                    it.readText()
                }

                Log.d(TAG, jsonString)
                return gson.fromJson(jsonString, type)
            } catch (ioException: IOException){
                ioException.printStackTrace()
                return null
            }
        }

        fun <T> store(context: Context, obj: T, fileName: String){
            val gson = GsonBuilder().setPrettyPrinting().create()
            try {
                val data = gson.toJson(obj)
                val outputFile: FileOutputStream = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE
                )
                outputFile.write(data.toByteArray())
                outputFile.close()
            }catch (ioException: IOException){
                ioException.printStackTrace()
            }
        }

        fun <T> retrive(context: Context, fileName: String, type: Type) : T? {
            val gson = Gson()
            try{
                val openFile: FileInputStream = context.openFileInput(fileName)
                var data = openFile.reader().readText()
                return gson.fromJson(data, type)
            }catch (ioException: IOException){
                ioException.printStackTrace()
                return null
            }

        }
    }

}