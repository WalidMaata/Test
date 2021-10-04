package fr.leboncoin.test.data.source.remote

import fr.leboncoin.test.data.model.Album
import retrofit2.http.GET

interface ApiService {

    @GET("img/shared/technical-test.json")
    suspend fun getAlbums() : List<Album>
}