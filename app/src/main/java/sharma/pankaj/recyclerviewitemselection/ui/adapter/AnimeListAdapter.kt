package sharma.pankaj.recyclerviewitemselection.ui.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import sharma.pankaj.recyclerviewitemselection.R
import sharma.pankaj.recyclerviewitemselection.data.model.Characters
import sharma.pankaj.recyclerviewitemselection.databinding.LayoutMangaCharacterBinding
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AnimeListAdapter @Inject constructor() : RecyclerView.Adapter<AnimeListAdapter.ViewHolder>() {

    public var list: List<Characters> = arrayListOf()

    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    interface ItemClickListener {
        fun onItemClick()
    }

    fun setData(list: List<Characters>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutBinding: LayoutMangaCharacterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_manga_character, parent, false
        )
        return ViewHolder(layoutBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        tracker?.let {
            holder.bind(holder.adapterPosition, it.isSelected(position.toLong()))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long = position.toLong()

    inner class ViewHolder(private val view: LayoutMangaCharacterBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(value: Int, isActivated: Boolean) = with(itemView)  {
            view.model = list[value]
            list[value].isSelected = isActivated
            view.selected.isChecked = list[value].isSelected
        }


        fun getItemDetails(): ItemDetails<Long> =
            object : ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long = itemId
            }
    }

    companion object {
        @JvmStatic
        @BindingAdapter(value = ["bind:imageUrl"])
        fun loadImage(img: ShapeableImageView, imageUrl: String?) {
            Glide.with(img.context)
                .load(imageUrl ?: "")
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(img)
        }
    }
}