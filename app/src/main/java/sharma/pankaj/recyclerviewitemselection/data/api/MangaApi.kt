package sharma.pankaj.recyclerviewitemselection.data.api

import io.ktor.client.*
import io.ktor.client.request.*
import sharma.pankaj.recyclerviewitemselection.data.model.MangaModel

interface MangaApi {
    suspend fun getMangaCharacter(): MangaModel
}