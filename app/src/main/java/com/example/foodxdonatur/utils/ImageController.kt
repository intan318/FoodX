package com.example.foodxdonatur.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.foodxdonatur.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.selector
import org.jetbrains.anko.toast
import java.io.*
import java.util.*
import java.util.jar.Manifest
import kotlin.math.min

@Suppress("DEPRECATION")
class ImageController (
    private val activity: Activity,
    private val imageContainer: ImageView? = null,
    private val callBackProgress: ProgressRequestBody.UploadCallBacks
) {

    val GALLERY = 1
    val CAMERA = 2
    val IMAGE_DIRECTORY = "/foodxdonatur"
    var realPath = ""
    lateinit var imageUri: Uri

    fun requestImageCapturingPermissions(){
        Dexter.withActivity(activity)
            .withPermissions(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        val list = listOf("Ambil foto dari kamera", "Ambil foto dari perangkat")
                        activity.selector("Ambil foto makanan", list) { _, which ->
                            when (which) {
                                0 -> takePhotoFromCamera()
                                1 -> choosePhotoFromGallery()
                            }
                        }
                    }
                    // check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied) {
                        // show alert dialog navigating to Settings
                        //openSettingsDialog();
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).withErrorListener { activity.toast("Some error") }
            .onSameThread()
            .check()
    }

    fun createPartFromString(desc: String?): RequestBody {
        return RequestBody.create(
            MultipartBody.FORM, desc.toString()
        )
    }

    internal fun choosePhotoFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent, GALLERY)
    }

    internal fun takePhotoFromCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera")

        imageUri =
            activity.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        activity.startActivityForResult(intent, CAMERA)
    }

    fun afterCapture(requestCode: Int, resultCode: Int, data: Intent?) {

        //TODO This Method Run inside Override Method ActivityResult

        when (resultCode) {
            Activity.RESULT_CANCELED -> return
            Activity.RESULT_OK -> when (requestCode) {
                GALLERY -> if (data != null) {
                    val contentURI = data.data
                    try {

                        val path = getRealPathFromURI(contentURI)
                        realPath = path
                        Log.e("Real path", realPath)
                        setPict(path, contentURI)

                    } catch (e: IOException) {
                        e.printStackTrace()
                        activity.toast(activity.getString(R.string.fail))
                    }
                }
                CAMERA -> {

                    val thumbnail =
                        MediaStore.Images.Media.getBitmap(activity.contentResolver, imageUri)
                    val fixedImage = PhotoFixer.rotateImageIfRequired(activity, thumbnail, imageUri)

                    val stream = ByteArrayOutputStream()
                    fixedImage?.compress(Bitmap.CompressFormat.JPEG, 70, stream)

                    fixedImage.let {
                        Glide.with(activity)
                            .load(stream.toByteArray())
                            .asBitmap()
                            .into(imageContainer)
                    }
                    saveImage(fixedImage!!)
                    activity.toast(activity.getString(R.string.image_saved))
                }
            }
        }
    }

    private fun setPict(path: String, contentUri: Uri?) {
        // Get the dimensions of the View
        val targetW: Int = imageContainer!!.width
        val targetH: Int = imageContainer.height

        val bmOptions = BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, this)
            val photoW: Int = this.outWidth
            val photoH: Int = this.outHeight

            // Determine how much to scale down the image
            val scaleFactor: Int = Math.min(photoW / targetW, photoH / targetH)

            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
            inPurgeable = true
        }


        BitmapFactory.decodeFile(path, bmOptions)?.also { bitmap ->

            val fixedImage = PhotoFixer.rotateImageIfRequired(activity, bitmap, contentUri!!)

            val stream = ByteArrayOutputStream()
            fixedImage?.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            fixedImage.let {
                Glide.with(activity)
                    .load(stream.toByteArray())
                    .asBitmap()
                    .into(imageContainer)
            }
            //imageContainer.setImageBitmap(bitmap)
        }
    }


    @SuppressLint("LogConditional")
    private fun saveImage(myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()

        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val wallpaperDirectory =
            File(Environment.getExternalStorageDirectory().toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }

        try {
            val f = File(wallpaperDirectory, "${Calendar.getInstance().timeInMillis}.jpg")
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(
                activity,
                arrayOf(f.path),
                arrayOf("image/jpeg"), null
            )
            fo.close()
            Log.e("TAG", """File Saved::--->${f.absolutePath}""")

            realPath = f.absolutePath
            Log.e("Real path", f.absolutePath)
            return f.absolutePath
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    private fun getRealPathFromURI(contentUri: Uri?): String {

        val proj = listOf(MediaStore.Images.Media.DATA)
        val cursor = activity.managedQuery(contentUri, proj.toTypedArray(), null, null, null)
        val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(columnIndex)
    }


    fun getMultiPartBody(path: String, paramName: String): MultipartBody.Part? {

        if (path.isNotEmpty() || path != "") {
            val targetW = 600
            val targetH = 300

            val bmOptions = BitmapFactory.Options().apply {
                // Get the dimensions of the bitmap
                inJustDecodeBounds = true
                BitmapFactory.decodeFile(path, this)
                val photoW: Int = this.outWidth
                val photoH: Int = this.outHeight

                // Determine how much to scale down the image
                val scaleFactor: Int = min(photoW / targetW, photoH / targetH)

                // Decode the image file into a Bitmap sized to fill the View
                inJustDecodeBounds = false
                inSampleSize = scaleFactor
                inPurgeable = true
            }

            return try {
                val file = BitmapFactory.decodeFile(path, bmOptions)
                val bos = ByteArrayOutputStream()
                file.compress(Bitmap.CompressFormat.JPEG, 100, bos)

                val fileReqBody = RequestBody.create(MediaType.parse("image/*"), bos.toByteArray())
                val part = MultipartBody.Part.createFormData(paramName, path, fileReqBody)
                part
            } catch (e: FileNotFoundException) {
                null
            } catch (e: Exception) {
                null
            }
        } else {
            return null
        }
    }


}