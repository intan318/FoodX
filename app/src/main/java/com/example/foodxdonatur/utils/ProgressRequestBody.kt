package com.example.foodxdonatur.utils

import android.util.Log
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*
import java.io.IOException

class ProgressRequestBody(
    private val delegate: RequestBody,
    private val mListener: UploadCallBacks
) :
    RequestBody(){

    interface UploadCallBacks{
        fun onProgressUpdate(percentage: Int)
    }

    override fun contentType(): MediaType? {
        return delegate.contentType()    }

    @Throws(IOException::class)
    override fun contentLength(): Long {
        try {
            return delegate.contentLength()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return -1
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val countingSink = CountingSink(sink)
        val bufferedSink = Okio.buffer(countingSink)
        delegate.writeTo(bufferedSink)
        bufferedSink.flush()
    }

    protected inner class CountingSink(delegate: Sink) : ForwardingSink(delegate) {
        private var bytesWritten: Long = 0

        @Throws(IOException::class)
        override fun write(source: Buffer, byteCount: Long) {
            super.write(source, byteCount)
            bytesWritten += byteCount
            Log.e("writter", bytesWritten.toString())
            Log.e("content Length", contentLength().toString())
            mListener.onProgressUpdate((100f * bytesWritten / contentLength()).toInt())
        }

    }

}