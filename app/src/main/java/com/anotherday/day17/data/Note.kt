package com.anotherday.day17.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = Note.dbName)
data class Note(
    @ColumnInfo(name = Columns.noteId)
    @PrimaryKey(autoGenerate = true)
    val noteId: Int,

    @ColumnInfo(name = Columns.content)
    val content: String?,

    @ColumnInfo(name = Columns.editDate)
    val editDate: Date?,

    @ColumnInfo(name = Columns.createDate)
    val createDate: Date?,

    ) : Parcelable {
    companion object {
        const val dbName = "note" //🤪
    }

    class Columns {
        companion object {
            const val noteId = "note_id"
            const val content = "content"
            const val editDate = "edit_date"
            const val createDate = "create_date"
        }
    }

}

