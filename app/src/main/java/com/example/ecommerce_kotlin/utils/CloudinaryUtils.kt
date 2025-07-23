package com.example.ecommerce_kotlin.utils

import okhttp3.*
import org.json.JSONObject
import java.io.File
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaTypeOrNull

fun uploadImageToCloudinary(
    imageFile: File,
    onSuccess: (String) -> Unit,
    onError: (Exception) -> Unit
) {
    val cloudName = "dp0jpmsws" // tu cloud name
    val preset = "ml_default"   // si creaste uno personalizado, reemplaza este

    val url = "https://api.cloudinary.com/v1_1/$cloudName/image/upload"
    val requestBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("file", imageFile.name, RequestBody.create("image/*".toMediaTypeOrNull(), imageFile))
        .addFormDataPart("upload_preset", preset)
        .build()

    val request = Request.Builder()
        .url(url)
        .post(requestBody)
        .build()

    OkHttpClient().newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            onError(e)
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                val imageUrl = JSONObject(response.body?.string() ?: "").getString("secure_url")
                onSuccess(imageUrl)
            } else {
                onError(Exception("Upload failed: ${response.code}"))
            }
        }
    })
}
