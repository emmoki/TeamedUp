package com.example.teamedup.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import android.util.Base64
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.*

object PictureRelatedTools {
    var uploadedImage1 : String = ""
    var uploadedImage2 : String = ""
    fun convertBase64ToBitmap(base64: String): Bitmap {
        val decodeString = Base64.decode(base64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodeString, 0, decodeString.size)
    }

    fun convertBitmapToBase64(bitmap: Bitmap?) : String {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        val image = stream.toByteArray()
        return Base64.encodeToString(image, Base64.DEFAULT)
    }

    fun uploadImage1(imageUri : Uri?){
        if(imageUri != null){
            val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
            val now = Date()
            val fileName: String = formatter.format(now)
            lateinit var storageReference : StorageReference
            storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")
            storageReference.putFile(imageUri)
                .addOnSuccessListener {
                    storageReference.downloadUrl.addOnSuccessListener { uri ->
                        uploadedImage1 = uri.toString()
                        Log.d(TAG, "uploadImage: $uploadedImage1")
                    }
                }
                .addOnFailureListener {
                    Log.d(TAG, "uploadImage: Failed")
                }
        }
    }

    fun uploadImage2(imageUri : Uri?){
        if(imageUri != null){
            val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
            val now = Date()
            val fileName: String = formatter.format(now)
            lateinit var storageReference : StorageReference
            storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")
            storageReference.putFile(imageUri)
                .addOnSuccessListener {
                    storageReference.downloadUrl.addOnSuccessListener { uri ->
                        uploadedImage2= uri.toString()
                        Log.d(TAG, "uploadImage: $uploadedImage2")
                    }
                }
                .addOnFailureListener {
                    Log.d(TAG, "uploadImage: Failed")
                }
        }
    }
}