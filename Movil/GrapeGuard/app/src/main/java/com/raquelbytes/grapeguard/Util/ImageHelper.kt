package com.raquelbytes.grapeguard.Util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import android.widget.ImageView
import java.io.ByteArrayOutputStream

object ImageHelper {
    fun encodeImageViewToBase64(imageView: ImageView): String {
        val drawable: Drawable? = imageView.drawable
        val bitmap: Bitmap = if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else {
            Bitmap.createBitmap(
                drawable!!.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            ).apply {
                val canvas = Canvas(this)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
            }
        }
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
    fun decodeBase64ToBitmap(base64String: String): Bitmap? {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
}
