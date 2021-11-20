package sharma.pankaj.recyclerviewitemselection.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MangaModel(
    @SerialName("request_hash") val request_hash : String,
    @SerialName("request_cached") val request_cached : Boolean,
    @SerialName("request_cache_expiry") val request_cache_expiry : Int,
    @SerialName("characters") val characters : List<Characters>
)

@Serializable
data class Characters (

    @SerialName("mal_id") val mal_id : Long,
    @SerialName("url") val url : String,
    @SerialName("image_url") val image_url : String,
    @SerialName("name") val name : String,
    @SerialName("role") val role : String,
    var isSelected : Boolean = false
)