package com.example.arman

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class LikeFragment : Fragment() {
    var recyclerView: RecyclerView? = null
    private var adapter: LikedListAdapter? = null
    private var postsList: MutableList<Posts>? = null
    private var listener: LikedListAdapter.ItemClickListener? = null
    private var fragmentLikeListener: LikedListAdapter.FragmentLikeListener? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView =
            inflater!!.inflate(R.layout.fragment_like, container, false) as ViewGroup
        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView?.setLayoutManager(LinearLayoutManager(activity))
        listener = object : LikedListAdapter.ItemClickListener {
            override fun itemClick(position: Int, item: Posts?) {
                val intent = Intent(activity, PostDetailActivity::class.java)
                intent.putExtra("posts", item)
                startActivity(intent)
            }
        }
        fragmentLikeListener = object : LikedListAdapter.FragmentLikeListener {
            override fun removeItemLike(posts: Posts?) {
                (activity as MainActivity?)!!.removeItemLike(posts)
            }
        }
        postsList = ArrayList()
        adapter = LikedListAdapter(postsList as ArrayList<Posts>, listener, fragmentLikeListener)
        recyclerView?.adapter = adapter
        return rootView
    }

    fun savePosts(posts: Posts) {
        postsList!!.add(posts)
        recyclerView!!.adapter!!.notifyItemInserted(postsList!!.size - 1)
    }

    fun removePosts(posts: Posts?) {
        if (postsList!!.indexOf(posts) == 0) {
            postsList!!.remove(posts)
        } else {
            val position = postsList!!.indexOf(posts)
            postsList!!.remove(posts)
            recyclerView!!.adapter!!.notifyItemRemoved(position)
        }
    }

    fun removeLike(posts: Posts?) {
        val p = postsList!!.indexOf(posts)
        removePosts(posts)
        adapter!!.notifyItemRemoved(p)
    }
}
