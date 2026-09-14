package com.example.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.R

object AnimeNotificationManager {
    const val CHANNEL_ID = "anime_episode_drops"
    private const val CHANNEL_NAME = "New Anime Episodes"
    private const val CHANNEL_DESC = "Notifications for newly released anime episodes"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESC
                enableVibration(true)
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showEpisodeNotification(
        context: Context,
        animeId: String,
        animeTitle: String,
        episodeNumber: Int,
        source: String
    ) {
        createNotificationChannel(context)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("EXTRA_ANIME_ID", animeId)
            putExtra("EXTRA_EPISODE_NUM", episodeNumber)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            (animeId.hashCode() + episodeNumber),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("New Episode Dropped! 🎉")
            .setContentText("$animeTitle Episode $episodeNumber is now streaming on $source")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = (animeId.hashCode() + episodeNumber).coerceAtLeast(1)
        notificationManager.notify(notificationId, builder.build())
    }
}
