package com.example.data.local

import androidx.room.TypeConverter
import com.example.data.model.WatchStatus

class DatabaseConverters {
    @TypeConverter
    fun fromWatchStatus(value: WatchStatus): String {
        return value.name
    }

    @TypeConverter
    fun toWatchStatus(value: String): WatchStatus {
        return try {
            WatchStatus.valueOf(value)
        } catch (e: Exception) {
            WatchStatus.PLAN_TO_WATCH
        }
    }
}
