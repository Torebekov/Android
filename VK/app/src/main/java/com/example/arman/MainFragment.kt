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


class MainFragment : Fragment() {
    var recyclerView: RecyclerView? = null
    var recyclerView2: RecyclerView? = null
    var storiesModelArrayList: ArrayList<StoriesModel> =
        ArrayList<StoriesModel>()
    var adapterStories: RecyclerViewAdapterStories? = null
    private var adapter: PostsListAdapter? = null
    private var listener: PostsListAdapter.ItemClickListener? = null
    private var fragmentButtonListener: PostsListAdapter.FragmentButtonListener? = null
    private var fragmentLikeListener: PostsListAdapter.FragmentLikeListener? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater
            .inflate(R.layout.fragment_main, container, false) as ViewGroup
        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView?.setLayoutManager(LinearLayoutManager(activity))
        recyclerView2 = rootView.findViewById<View>(R.id.recy_stories) as RecyclerView
        val layoutManager1: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        (layoutManager1 as LinearLayoutManager).orientation = LinearLayoutManager.HORIZONTAL
        recyclerView2!!.layoutManager = layoutManager1
        adapterStories = RecyclerViewAdapterStories(activity, storiesModelArrayList)
        recyclerView2!!.adapter = adapterStories
        populaterecyclerviewstories()
        listener = object : LikedListAdapter.ItemClickListener, PostsListAdapter.ItemClickListener {
            override fun itemClick(position: Int, item: Posts?) {
                val intent = Intent(activity, PostDetailActivity::class.java)
                intent.putExtra("posts", item)
                startActivity(intent)
            }
        }
        fragmentButtonListener = object : PostsListAdapter.FragmentButtonListener {
            override fun myClick(posts: Posts?, option: Int) {
                (activity as MainActivity?)!!.myClick(posts, option)
            }
        }
        fragmentLikeListener = object : PostsListAdapter.FragmentLikeListener {
            override fun removeItemLike(posts: Posts?) {
                (activity as MainActivity?)!!.removeItemLike(posts)
            }
        }
        adapter = PostsListAdapter(
            postsGenerator() as MutableList<Posts>,
            listener,
            fragmentButtonListener,
            fragmentLikeListener
        )
        recyclerView?.setAdapter(adapter)
        return rootView
    }

    fun removeLike(posts: Posts?) {
        if (posts != null) {
            adapter?.removeLike(posts)
        }
    }

    private fun populaterecyclerviewstories() {
        var storiesModel = StoriesModel("Albert", R.drawable.a1)
        storiesModelArrayList.add(storiesModel)
        storiesModel = StoriesModel("Marie", R.drawable.a2)
        storiesModelArrayList.add(storiesModel)
        storiesModel = StoriesModel("Ernest", R.drawable.a3)
        storiesModelArrayList.add(storiesModel)
        storiesModel = StoriesModel("Niels", R.drawable.propic4)
        storiesModelArrayList.add(storiesModel)
        storiesModel = StoriesModel("Isaac", R.drawable.av5)
        storiesModelArrayList.add(storiesModel)
        storiesModel = StoriesModel("Werner", R.drawable.av8)
        storiesModelArrayList.add(storiesModel)
    }

    fun postsGenerator(): List<Posts> {
        val items: MutableList<Posts> = ArrayList()
        val logo = ArrayList<Int>()
        val author = ArrayList<String>()
        val image = ArrayList<Int>()
        val time = ArrayList<String>()
        val description = ArrayList<String>()
        val like = ArrayList<Int>()
        val comment = ArrayList<String>()
        val repost = ArrayList<String>()
        val views = ArrayList<Int>()
        //#1
        logo.add(R.drawable.logo_wg)
        author.add("Wiki Geography - География мира")
        image.add(R.drawable.img1)
        time.add("Сегодня в 18:00")
        description.add(getString(R.string.post1))
        comment.add("45")
        like.add(158)
        repost.add("23")
        views.add(201)
        //#2
        logo.add(R.drawable.logo_wg)
        author.add("Wiki Geography - География мира")
        image.add(R.drawable.img2)
        time.add("Сегодня в 17:00")
        description.add(getString(R.string.post2))
        comment.add("45")
        like.add(100)
        repost.add("23")
        views.add(201)
        //#3
        logo.add(R.drawable.logo_ww)
        author.add("Wiki Weapons - все об оружии")
        image.add(R.drawable.img10)
        time.add("Сегодня в 16:00")
        description.add(getString(R.string.post10))
        comment.add("45")
        like.add(200)
        repost.add("18")
        views.add(201)
        //#4
        logo.add(R.drawable.logo_lang)
        author.add("I♥Languages|Английский язык и не только|English")
        image.add(R.drawable.img4)
        time.add("Сегодня в 13:00")
        description.add(getString(R.string.post4))
        comment.add("45")
        like.add(201)
        repost.add("11")
        views.add(210)
        //#5
        logo.add(R.drawable.logo_wg)
        author.add("Wiki Geography - География мира")
        image.add(R.drawable.img3)
        description.add(getString(R.string.post3))
        time.add("Сегодня в 12:00")
        comment.add("45")
        like.add(201)
        repost.add("25")
        views.add(250)
        //#6
        logo.add(R.drawable.logo_lang)
        author.add("I♥Languages|Английский язык и не только|English")
        image.add(R.drawable.img5)
        time.add("Сегодня в 11:00")
        description.add(getString(R.string.post5))
        comment.add("45")
        like.add(201)
        repost.add("23")
        views.add(255)
        //#7
        logo.add(R.drawable.logo_iq)
        author.add("Почему? Повышаем IQ")
        image.add(R.drawable.img7)
        time.add("Сегодня в 10:00")
        description.add(getString(R.string.post7))
        comment.add("45")
        like.add(201)
        repost.add("23")
        views.add(300)
        //#8
        logo.add(R.drawable.logo_ww)
        author.add("Wiki Weapons - все об оружии")
        image.add(R.drawable.img9)
        time.add("Вчера в 20:00")
        description.add(getString(R.string.post9))
        comment.add("45")
        like.add(201)
        repost.add("55")
        views.add(201)
        //#9
        logo.add(R.drawable.logo_lang)
        author.add("I♥Languages|Английский язык и не только|English")
        image.add(R.drawable.img6)
        time.add("Вчера в 22:00")
        description.add(getString(R.string.post6))
        comment.add("45")
        like.add(201)
        repost.add("40")
        views.add(201)
        //#10
        logo.add(R.drawable.logo_5)
        author.add("5 интересных фактов")
        image.add(R.drawable.img8)
        time.add("Вчера в 23:00")
        description.add(getString(R.string.post8))
        comment.add("45")
        like.add(201)
        repost.add("33")
        views.add(400)
        for (i in logo.indices) {
            val posts = Posts(
                logo[i],
                author[i],
                image[i],
                time[i],
                description[i],
                like[i],
                comment[i],
                repost[i],
                views[i]
            )
            items.add(posts)
        }
        return items
    }
}







