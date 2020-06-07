package com.deepanshu.dailydos.database

import androidx.room.TypeConverter
import java.util.*

class TaskConverters {

    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun fromDateToTimeStamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

}