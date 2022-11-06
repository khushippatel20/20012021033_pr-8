package com.example.a20012021033_pr_8

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextClock
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.util.*

class MainActivity : AppCompatActivity() , Animation.AnimationListener{
    lateinit var alarmimages : ImageView
    lateinit var alarmimages_frameByFrameAnimation : AnimationDrawable
    lateinit var alarmimagesAnimation : Animation
    lateinit var heartimages : ImageView
    lateinit var heart_frameByFrameAnimation : AnimationDrawable
    lateinit var heartAnimation : Animation
    var mili:Long=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        alarmimages = findViewById(R.id.alarmImages)
        alarmimages.setBackgroundResource(R.drawable.alarm_image_list)

        heartimages = findViewById(R.id.heartImages)
        heartimages.setBackgroundResource(R.drawable.heart_image_list)

        alarmimages_frameByFrameAnimation = alarmimages.background as AnimationDrawable

        heart_frameByFrameAnimation = heartimages.background as AnimationDrawable

        alarmimagesAnimation = AnimationUtils.loadAnimation(this, R.anim.alarm_animation)
        alarmimagesAnimation.setAnimationListener(this)

        heartAnimation = AnimationUtils.loadAnimation(this, R.anim.heart_animation)
        alarmimagesAnimation.setAnimationListener(this)


        val CancleAlarmCardView = findViewById<MaterialCardView>(R.id.cancel_alarm_card_view)
        val BtnCreateAlarm = findViewById<MaterialButton>(R.id.btn_set_alarm)
        val SetAlarmTime = findViewById<TextView>(R.id.set_alarm_time)
        val BtnCancelAlarm = findViewById<MaterialButton>(R.id.btn_cancel_alarm)
        val clockTC = findViewById<TextClock>(R.id.show_time_tv)

        clockTC.format12Hour = "hh:mm:ss a"

        CancleAlarmCardView.visibility= View.GONE

        BtnCreateAlarm.setOnClickListener {
            var cal: Calendar = Calendar.getInstance()
            var hour = cal.get(Calendar.HOUR_OF_DAY)
            var min = cal.get(Calendar.MINUTE)
            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener(function = {
                    view, h, m ->
                mili=getMillis(h,m)
                setAlarm(getMillis(h,m),"Start")
                CancleAlarmCardView.visibility= View.VISIBLE
                SetAlarmTime.text=h.toString()+":"+m.toString()
            }),hour,min,false)
            tpd.show()
        }

        BtnCancelAlarm.setOnClickListener{
            setAlarm(mili,"Stop")
            CancleAlarmCardView.visibility= View.GONE
        }
    }
    fun setAlarm(millisTime: Long, str: String)
    {
        val intent = Intent(this,AlarmBroadcastReceiver::class.java)
        intent.putExtra("Service1", str)
        val pendingIntent =
            PendingIntent.getBroadcast(applicationContext, 234324243, intent, 0)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        if(str == "Start") {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                millisTime,
                pendingIntent
            )
        }else if(str == "Stop")
        {
            alarmManager.cancel(pendingIntent)
            sendBroadcast(intent)
        }
    }

    fun getMillis(hour:Int,min:Int):Long
    {
        val setcalendar = Calendar.getInstance()
        setcalendar[Calendar.HOUR_OF_DAY] = hour
        setcalendar[Calendar.MINUTE] = min
        setcalendar[Calendar.SECOND] = 0
        return setcalendar.timeInMillis
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if(hasFocus){
            alarmimages_frameByFrameAnimation.start()
            alarmimages.startAnimation(alarmimagesAnimation)

            heart_frameByFrameAnimation.start()
            heartimages.startAnimation(heartAnimation)
        }
    }
    override fun onAnimationStart(p0: Animation?) {
    }

    override fun onAnimationEnd(p0: Animation?) {
    }

    override fun onAnimationRepeat(p0: Animation?) {
    }
}