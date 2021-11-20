package sharma.pankaj.recyclerviewitemselection.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.util.*

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import sharma.pankaj.recyclerviewitemselection.data.model.MangaModel
import sharma.pankaj.recyclerviewitemselection.data.repository.MangaRepository
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(private val repository: MangaRepository) : ViewModel() {

    private val scope : CoroutineScope = CoroutineScope(Dispatchers.IO)

    private val mangaModelLiveData = MutableLiveData<Result<MangaModel>>()

    fun getMangaCharacterResponse() : MutableLiveData<Result<MangaModel>> {
        return mangaModelLiveData
    }


    fun getMangaCharacter(){
        scope.launch {
            repository.getMangaCharacter(liveData = mangaModelLiveData)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.coroutineContext.cancelChildren()
    }
}