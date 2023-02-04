package com.example.moodyfoodyprototype

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.net.toUri
import com.example.moodyfoodyprototype.databinding.ActivityMoodyBinding

class MoodyActivity : AppCompatActivity() {

    lateinit var binding: ActivityMoodyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoodyBinding.inflate(layoutInflater)

        /* set & load user image */
        var dataUri = intent.getStringExtra("uri")
        var userPhoto : Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dataUri?.toUri())
        binding.imgUser.setImageBitmap(userPhoto)

        /*  */


        setContentView(binding.root)
    }
}