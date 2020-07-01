package com.example.foodxdonatur.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import java.io.IOException

object PhotoFixer {

    @Throws(IOException::class)

    fun rotateImageIfRequired(context: Context, img: Bitmap, selectedImage: Uri): Bitmap? {

        val input = context.contentResolver.openInputStream(selectedImage)
        val ei: ExifInterface

        ei = if (Build.VERSION.SDK_INT > 23)
            ExifInterface(input)
        else
            ExifInterface(selectedImage.path)

        val orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90.toFloat())
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180.toFloat())
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270.toFloat())
            else -> img
        }
    }

    private fun rotateImage(img: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree)
        val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
        img.recycle()
        return rotatedImg
    }

}