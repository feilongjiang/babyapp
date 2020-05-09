package com.example.apps.happybaby.data

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    @TypeConverter
    fun dateToDatestamp(date: Date?): Long? = date?.time
    @TypeConverter
    fun datestampToDate(value: Long?): Date? = value?.let { Date(it) }
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar?): Long? = calendar?.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long?): Calendar =
            Calendar.getInstance().apply {
                if (value != null) {
                    timeInMillis = value
                }
            }
}