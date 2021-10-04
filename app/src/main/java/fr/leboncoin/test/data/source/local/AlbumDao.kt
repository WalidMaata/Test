package fr.leboncoin.test.data.source.local

import androidx.room.*
import fr.leboncoin.test.data.model.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    /**
     * Insert a album in database . If albums already exist replace it
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<Album>)

    /**
     * Select all albums from article table
     *
     *@return all albums
     */
    @Query("SELECT * FROM ARTICLE")
     fun getAlbums(): Flow<List<Album>>
}