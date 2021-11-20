package sharma.pankaj.recyclerviewitemselection.ui


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.selection.*
import dagger.hilt.android.AndroidEntryPoint
import sharma.pankaj.recyclerviewitemselection.R
import sharma.pankaj.recyclerviewitemselection.data.model.Characters
import sharma.pankaj.recyclerviewitemselection.databinding.ActivityMainBinding
import sharma.pankaj.recyclerviewitemselection.ui.adapter.AnimeListAdapter
import sharma.pankaj.recyclerviewitemselection.ui.adapter.CustomItemDetailsLookup
import sharma.pankaj.recyclerviewitemselection.utils.ActivityBinding
import sharma.pankaj.recyclerviewitemselection.utils.Constants
import sharma.pankaj.recyclerviewitemselection.utils.Logger
import sharma.pankaj.recyclerviewitemselection.utils.PreferenceHandler
import sharma.pankaj.recyclerviewitemselection.viewmodel.MangaViewModel
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by ActivityBinding<ActivityMainBinding>(R.layout.activity_main)
    private val viewModel: MangaViewModel by viewModels()

    @Inject
    lateinit var adapter: AnimeListAdapter

    @Inject
    lateinit var log: Logger

    @Inject
    lateinit var local: PreferenceHandler
    private var list : List<Characters> = arrayListOf()
    private var selectList : HashMap<Long, Characters> = hashMapOf()

    private var tracker: SelectionTracker<Long>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
        viewModel.getMangaCharacter()
        binding.recyclerView.adapter = adapter
        tracker = SelectionTracker.Builder<Long>(
            "mySelection",
            binding.recyclerView,
            StableIdKeyProvider(binding.recyclerView),
            CustomItemDetailsLookup(binding.recyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        adapter.tracker = tracker

        tracker?.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onItemStateChanged(key: Long, selected: Boolean) {
                super.onItemStateChanged(key, selected)
                val index = getIndexOf(key)
                if (index!=-1){
                    if (selected){
                        selectList[key] = list[index]
                        list[key.toInt()].isSelected = true
                    }else{
                        list[index].isSelected = false
                        selectList.remove(key)
                    }
                }
            }
        })
    }

    private fun getIndexOf(key: Long) : Int{
        var dataIndex : Int = -1
        list.forEachIndexed { index, characters ->
            if (characters.mal_id == key){
                dataIndex =  index
            }
        }
        return dataIndex
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))

            this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isNotEmpty()){
                        filter(newText)
                    }
                    return false
                }
            })
        }
        val checkBoxTheme = menu.findItem(R.id.action_theme).actionView as CheckBox
        checkBoxTheme.isChecked = local.readBoolean(Constants.THEME_DAY_NIGHT, false)
        changeTheme(checkBoxTheme, local.readBoolean(Constants.THEME_DAY_NIGHT, false))
        checkBoxTheme.setOnCheckedChangeListener { buttonView, isChecked ->
            if (checkBoxTheme.isPressed){
                changeTheme(checkBoxTheme, isChecked)
            }
        }
        return true
    }

    fun filter(text: String) {
        val temp: MutableList<Characters> = ArrayList()
        for (data in list) {
            if (data.name.lowercase().contains(text.toString().lowercase())) {
                temp.add(data)
            }
        }
        adapter.setData(temp)
    }

    private fun initObserver() {
        viewModel.getMangaCharacterResponse().observe(this) {
            when {
                it.isSuccess -> {
                    binding.process.visibility = View.GONE
                    binding.placeholder.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    val response = it.getOrNull()
                    list = response?.characters!!
                    adapter.setData(list)
                }
                it.isFailure -> {
                    binding.process.visibility = View.GONE
                    binding.placeholder.visibility = View.VISIBLE
                    Log.e("TAG", "initObserver: ")
                }
                else -> {
                    binding.process.visibility = View.GONE
                    binding.placeholder.visibility = View.VISIBLE
                    Log.e("TAG", "initObserver: ")
                }
            }
        }
    }


    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun changeTheme(checkBox: CheckBox, isChecked: Boolean) {
        checkBox.isChecked = isChecked
        local.writeBoolean(Constants.THEME_DAY_NIGHT, isChecked)
        when (isChecked) {
            true -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            false -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}