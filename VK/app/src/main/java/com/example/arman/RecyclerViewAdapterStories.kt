package com.example.arman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import de.hdodenhof.circleimageview.CircleImageView


class RecyclerViewAdapterStories(
    private val context: FragmentActivity?,
    mData: List<StoriesModel>
) :
    RecyclerView.Adapter<RecyclerViewAdapterStories.MyViewHolder>() {
    private val mData: List<StoriesModel>
    var glide: RequestManager
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View
        val inflater = LayoutInflater.from(context)
        view = inflater.inflate(R.layout.item_story, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.storiesname.setText(mData[position].storiesName)
        glide.load(mData[position].storiesPic).into(holder.stories)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(mView: View) :
        RecyclerView.ViewHolder(mView) {
        var storiesname: TextView
        var stories: CircleImageView

        init {
            storiesname = mView.findViewById<View>(R.id.stories_name) as TextView
            stories = mView.findViewById<View>(R.id.stories_pic) as CircleImageView
        }
    }

    init {
        this.mData = mData
        glide = this!!.context?.let { Glide.with(it) }!!
    }
}
