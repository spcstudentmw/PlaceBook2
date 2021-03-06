package com.raywenderlich.placebook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.placebook.R
import com.raywenderlich.placebook.ui.MapsActivity
import com.raywenderlich.placebook.viewmodel.MapsViewModel
import kotlinx.android.synthetic.main.bookmark_item.view.*

// 1
class BookmarkListAdapter(
        private var bookmarkData: List<MapsViewModel.BookmarkView>?,
        private val mapsActivity: MapsActivity) :
        RecyclerView.Adapter<BookmarkListAdapter.ViewHolder>() {
    // 2
    class ViewHolder(v: View,
                     private val mapsActivity: MapsActivity) :
            RecyclerView.ViewHolder(v) {
        val nameTextView: TextView = v.bookmarkNameTextView
        val categoryImageView: ImageView =
                v.findViewById(R.id.bookmarkIcon) as ImageView
        init {
            v.setOnClickListener {
                val bookmarkView = itemView.tag as MapsViewModel.BookmarkView
                mapsActivity.moveToBookmark(bookmarkView)
            }
        }
    }

    // 3
    fun setBookmarkData(bookmarks: List<MapsViewModel.BookmarkView>) {
        this.bookmarkData = bookmarks
        notifyDataSetChanged()
    }

    // 4
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int): BookmarkListAdapter.ViewHolder {
        val vh = ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.bookmark_item, parent, false), mapsActivity)
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int) {
        // 5
        val bookmarkData = bookmarkData ?: return
        // 6
        val bookmarkViewData = bookmarkData[position]
        // 7
        holder.itemView.tag = bookmarkViewData
        holder.nameTextView.text = bookmarkViewData.name
        bookmarkViewData.categoryResourceId?.let {
            holder.categoryImageView.setImageResource(it)
        }
    }

    // 8
    override fun getItemCount(): Int {
        return bookmarkData?.size ?: 0
    }
}