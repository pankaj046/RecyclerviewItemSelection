package sharma.pankaj.recyclerviewitemselection.data.repository

import androidx.lifecycle.MutableLiveData
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import sharma.pankaj.recyclerviewitemselection.data.model.MangaModel
import sharma.pankaj.recyclerviewitemselection.data.network.KtorClient
import javax.inject.Inject

class MangaRepository @Inject constructor() {

    @KtorExperimentalAPI
    suspend fun getMangaCharacter(liveData : MutableLiveData<Result<MangaModel>>){
        try {
            val response = KtorClient.request<MangaModel>("https://api.jikan.moe/v3/manga/1/characters"){
                method = HttpMethod.Get
            }
           liveData.postValue(Result.success(response))
        }catch (e:Exception){
//            liveData.postValue(Result.success(emptyList()))
        }
    }
}