package fr.leboncoin.test.data.model

import androidx.room.*

@Entity(tableName = "article")
data class Album @JvmOverloads constructor(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "url") var url: String = "",
    @ColumnInfo(name = "thumbnailUrl") var thumbnailUrl: String = ""
)