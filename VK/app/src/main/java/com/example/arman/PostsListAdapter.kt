package com.example.arman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.lang.String


class PostsListAdapter(
    postsList: MutableList<Posts>,
    listener: ItemClickListener?,
    fragmentButtonListener: FragmentButtonListener?,
    fragmentLikeListener: FragmentLikeListener?
) :
    RecyclerView.Adapter<PostsListAdapter.PostsViewHolder>() {
    private val postsList: MutableList<Posts>
    private val listener: ItemClickListener?
    private val fragmentButtonListener: FragmentButtonListener?
    private val fragmentLikeListener: FragmentLikeListener?
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_posts, null, false)
        val params = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = params
        return PostsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val posts: Posts? = Posts.postsList?.get(getItemViewType(position))

        Glide.with(holder.image.context).load(posts?.image).into(holder.image)
        Glide.with(holder.logo.context).load(posts?.logo).into(holder.logo)
        //       holder.image.setImageResource(posts.getImage());
//       holder.logo.setImageResource(posts.getLogo());
        holder.author.setText(posts?.author)
        holder.time.setText(posts?.time)
        holder.description.setText(String.valueOf(posts?.description))
        holder.btnLike.setText(posts?.like.toString() + "")
        holder.views.setText(String.valueOf(posts?.views))
        holder.btnComment.setText(String.valueOf(posts?.comment))
        holder.btnRepost.setText(String.valueOf(posts?.repost))
        if (posts?.isLiked === true) {
            holder.btnLike.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_favorite_black_24dp,
                0,
                0,
                0
            )
        } else {
            holder.btnLike.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_favorite_border_black_24dp,
                0,
                0,
                0
            )
        }
        holder.btnLike.setOnClickListener {
            //                if(fragmentButtonListener!=null){
            if (posts?.isLiked!!) {
                holder.btnLike.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_favorite_border_black_24dp,
                    0,
                    0,
                    0
                )
                fragmentButtonListener!!.myClick(posts, 2)
                fragmentLikeListener!!.removeItemLike(posts)
                posts?.isLiked=false
                val num = holder.btnLike.text.toString().toInt()
                holder.btnLike.text = (num - 1).toString()
            } else {
                holder.btnLike.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_favorite_black_24dp,
                    0,
                    0,
                    0
                )
                fragmentButtonListener!!.myClick(posts, 1)
                posts.isLiked=true
                val num = holder.btnLike.text.toString().toInt()
                holder.btnLike.text = (num + 1).toString()
            }
        }
        holder.itemView.setOnClickListener { listener?.itemClick(position, posts) }
    }

    override fun getItemCount(): Int {
        return Posts.postsList?.size!!
    }

    inner class PostsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var logo: ImageView
        var author: TextView
        var image: ImageView
        var time: TextView
        var description: TextView
        var btnLike: Button
        var btnComment: Button
        var btnRepost: Button
        var views: TextView

        init {
            logo = itemView.findViewById(R.id.logo)
            author = itemView.findViewById(R.id.author)
            image = itemView.findViewById(R.id.image)
            time = itemView.findViewById(R.id.tvPostTime)
            description = itemView.findViewById(R.id.tvDescription)
            btnLike = itemView.findViewById(R.id.btnLike)
            btnComment = itemView.findViewById(R.id.btnComment)
            btnRepost = itemView.findViewById(R.id.btnRepost)
            views = itemView.findViewById(R.id.views)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    interface ItemClickListener {
        fun itemClick(position: Int, item: Posts?)
    }

    interface FragmentButtonListener {
        fun myClick(posts: Posts?, option: Int)
    }

    interface FragmentLikeListener {
        fun removeItemLike(posts: Posts?)
    }

    fun removeLike(posts: Posts) {
        val p: Int = Posts.postsList?.indexOf(posts)!!
        posts.isLiked=false
        //        posts.setLike(R.drawable.ic_favorite_border_black_24dp);
        Posts.postsList?.set(p, posts)
        postsList[p] = posts
        this.notifyItemChanged(p)
    }

    init {
        Posts.postsList = postsList
        this.postsList = postsList
        this.listener = listener
        this.fragmentButtonListener = fragmentButtonListener
        this.fragmentLikeListener = fragmentLikeListener
    }
}

private fun <E> List<E>.set(p: Int, posts: E) {

}
