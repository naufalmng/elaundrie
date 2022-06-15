package com.xnfl16.elaundrie.core.data.source.network

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.media.RingtoneManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BADGE_ICON_SMALL
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.res.ResourcesCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.ui.main.MainActivity
import com.xnfl16.elaundrie.utils.Constant.CHANNEL_ID

class ElaundrieWorker(context: Context, workerParameters: WorkerParameters): Worker(context,workerParameters) {
    private val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

    override fun doWork(): Result {
        val title = applicationContext.getString(R.string.title)
        val text = applicationContext.getString(R.string.jangan_lupa_cek)
        val priority = NotificationCompat.PRIORITY_HIGH

        val intent = Intent(applicationContext,MainActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(applicationContext,0,intent,0)

        val builder = NotificationCompat.Builder(applicationContext,CHANNEL_ID)
            .setVibrate(longArrayOf(1000,1000))
            .setSound(notificationSound)
            .setLights((ResourcesCompat.getColor(applicationContext.resources,R.color.yellow_primary,null)), NotificationCompat.FLAG_SHOW_LIGHTS,NotificationCompat.FLAG_SHOW_LIGHTS)
            .setLargeIcon((ResourcesCompat.getDrawable(applicationContext.resources,R.drawable.housewife_01,null) as BitmapDrawable).bitmap)
            .setPriority(priority)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentText(text)
            .setBadgeIconType(BADGE_ICON_SMALL)
            .setSmallIcon(R.drawable.ic_laundry)

        NotificationManagerCompat.from(applicationContext).also {
            it.notify(NOTIFICATION_ID,builder.build())
        }
        Log.d("ElaundrieWorker: ","do work success")
        return Result.success()
    }

//    private fun getPendingIntent(): PendingIntent {
//        val intent = Intent(applicationContext,MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        return PendingIntent.getActivity(applicationContext,0,intent,0)
//    }

    companion object {
        const val NOTIFICATION_ID =44
    }
}