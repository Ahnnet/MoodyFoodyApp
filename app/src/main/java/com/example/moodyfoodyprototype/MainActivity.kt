package com.example.moodyfoodyprototype

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.moodyfoodyprototype.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var dataUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* user image upload */
        binding.btnUpload.setOnClickListener {
//            ActivityCompat.requirePermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE), 101)
            permission()
            loadImage()
        }

        /* button analyze */
        binding.btnAnalyze.setOnClickListener {
            // loading...

            // change activity (MoodyActivity)
            var intent = Intent(this, MoodyActivity::class.java)
            intent.putExtra("uri", dataUri.toString())
            startActivity(intent)
        }
    }

    /* Image load function */
    private fun loadImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Load Picture"), 0)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                dataUri = data?.data!!
                try{
                    /* get the image to 'userPhoto' */
                    var userPhoto : Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dataUri) 
                    binding.imgUser.setImageBitmap(userPhoto)
                    binding.imgUser.visibility = View.VISIBLE
                }catch(e:Exception){
                    Toast.makeText(this,"$e",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this,"album access failed ",Toast.LENGTH_SHORT).show()
            }
        }
    }

    /* request album access permission */
    private fun permission(){
        var readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        /* when the permission was denied -> request permission*/
        if(readPermission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                0)
        }
    }
}