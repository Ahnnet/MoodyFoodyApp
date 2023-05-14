package com.example.moodyfoodyprototype

import android.content.Context
import android.graphics.Bitmap
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.example.moodyfoodyprototype.databinding.ActivityMoodyBinding

class MoodyActivity : AppCompatActivity() {

    lateinit var binding: ActivityMoodyBinding
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoodyBinding.inflate(layoutInflater)

        /* load & set user image */
        var dataUri = intent.getStringExtra("uri")
        var userPhoto : Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dataUri?.toUri())
        binding.imgUser.setImageBitmap(userPhoto)

        /* instagram screen capture */
        binding.btnInsta.setOnClickListener { screenCapture() }



        setContentView(binding.root)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun screenCapture() {
        Log.d("캡쳐","함수 시작")
        val mediaProjectionManager = getSystemService(MediaProjectionManager::class.java)
        var mediaProjection: MediaProjection

        Log.d("캡쳐","함수 선언 완료")
        val startMediaProjection = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            result->if(result.resultCode == RESULT_OK){
            Log.d("캡쳐","result OK")
            mediaProjection = mediaProjectionManager.getMediaProjection(result.resultCode, result.data!!)
            }
        }
        Log.d("캡쳐","프로젝션 시작")
        startMediaProjection.launch(mediaProjectionManager.createScreenCaptureIntent())
        Log.d("캡쳐","프로젝션 완료")




    }
}