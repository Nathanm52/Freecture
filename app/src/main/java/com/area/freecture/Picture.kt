package com.area.freecture

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.area.freecture.model.ImageModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class Picture : AppCompatActivity() {
    private var mainPicture: ImageView? = null
    private var profilPicture: ImageView? = null
    private lateinit var description: TextView
    private lateinit var localisation: TextView
    private lateinit var username: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)
        val list: List<ImageModel> = intent.extras?.get("mylist") as List<ImageModel>
        val position: Int = intent.extras?.get("postion") as Int
        println("PRINT ${list[position].username}")
        println("PRINT ${list[position].location}")
        println("PRINT ${list[position].profileImage}")
        println("PRINT [${list[position].description}]")
        mainPicture = findViewById(R.id.main_picture)
        profilPicture = findViewById(R.id.picture_avatar)
        description = findViewById<TextView>(R.id.description_content)
        username = findViewById<TextView>(R.id.username)
        localisation = findViewById<TextView>(R.id.localisation_content)

        this.mainPicture?.let {
            Glide.with(this).load(list[position].small)
                .thumbnail(0.5f)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(it)
        }
        this.profilPicture?.let {
            Glide.with(this).load(list[position].profileImage)
                .thumbnail(0.5f)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(it)
        }
        username.text = list[position].username

        if (list[position].description != "null") {
            description.text = list[position].description
        }
        if (list[position].location != "null")
            localisation.text = list[position].location

    }
}