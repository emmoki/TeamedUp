package com.example.teamedup.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import java.io.ByteArrayOutputStream
import android.util.Base64

object PictureRelatedTools {
    fun convertImageToBitmap(imageView: ImageView) : Bitmap{
        val drawable = imageView.drawable as BitmapDrawable
        return drawable.bitmap
    }

    fun convertBitmapToBase64(bitmap: Bitmap) : String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        val image = stream.toByteArray()
        return Base64.encodeToString(image, Base64.DEFAULT)
    }
}