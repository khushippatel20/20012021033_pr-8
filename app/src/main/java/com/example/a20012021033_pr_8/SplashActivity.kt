package com.example.a20012021033_pr_8

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.widget.ImageView
import com.google.android.material.animation.AnimationUtils

class SplashActivity : AppCompatActivity(),Animation.AnimationListener {
    lateinit var guni_imgg:ImageView
    lateinit var logo_framebyframeanimation:AnimationDrawable
    lateinit var twinanimation:Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        guni_imgg=findViewById(R.id.img)
        guni_imgg.setBackgroundResource(R.drawable.uvpce_logo_list)
        logo_framebyframeanimation=guni_imgg.background as AnimationDrawable
        twinanimation=android.view.animation.AnimationUtils.loadAnimation(this,R.anim.twin_animation)
        twinanimation.setAnimationListener(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus){
            logo_framebyframeanimation.start()
            guni_imgg.startAnimation(twinanimation)
        }
        else
        {
            logo_framebyframeanimation.stop()
        }
    }

    override fun onAnimationStart(p0: Animation?) {

    }

    override fun onAnimationEnd(p0: Animation?) {
      Intent(this,MainActivity::class.java).also { startActivity(it)}
        overridePendingTransition(R.anim.scale_center_in,R.anim.scale_center_out)
        finish()
    }

    override fun onAnimationRepeat(p0: Animation?) {

    }
}