package fr.leboncoin.test.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.leboncoin.test.data.model.Album

@Database(entities = [Album::class], version = 1, exportSchema = true)
abstract class TestDataBase : RoomDatabase(){

    abstract fun albumDao() : AlbumDao
}