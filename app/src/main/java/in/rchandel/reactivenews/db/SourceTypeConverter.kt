package `in`.rchandel.reactivenews.db

import `in`.rchandel.reactivenews.data.Source
import androidx.room.TypeConverter


class SourceTypeConverter {

    @TypeConverter
    fun sourceToString(source : Source?) : String? {
        return source?.name
    }

    @TypeConverter
    fun stringToSource(value: String?) : Source? {
        return Source(value?.lowercase(), value)
    }
}