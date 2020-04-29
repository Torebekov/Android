package com.example.arman

import android.os.Parcel
import android.os.Parcelable


class Posts : Parcelable {
    var logo: Int
    var author: String?
    var image: Int
    var time: String?
    var description: String?
    var like: Int
    var comment: String?
    var repost: String?
    var views: Int
    var isLiked = false

    constructor(
        logo: Int, author: String?, image: Int, time: String?,
        description: String?, like: Int, comment: String?,
        repost: String?, views: Int
    ) {
        this.logo = logo
        this.author = author
        this.image = image
        this.time = time
        this.description = description
        this.like = like
        this.comment = comment
        this.repost = repost
        this.views = views
        isLiked = false
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(logo)
        dest.writeString(author)
        dest.writeInt(image)
        dest.writeString(time)
        dest.writeString(description)
        dest.writeInt(like)
        dest.writeString(comment)
        dest.writeString(repost)
        dest.writeInt(views)
    }

    protected constructor(`in`: Parcel) {
        logo = `in`.readInt()
        author = `in`.readString()
        image = `in`.readInt()
        time = `in`.readString()
        description = `in`.readString()
        like = `in`.readInt()
        comment = `in`.readString()
        repost = `in`.readString()
        views = `in`.readInt()
    }

//    companion object {
//        var postsList: List<Posts>? = null
//        val CREATOR: Parcelable.Creator<Posts> = object : Parcelable.Creator<Posts?> {
//            @RequiresApi(api = Build.VERSION_CODES.Q)
//            override fun createFromParcel(`in`: Parcel): Posts? {
//                return Posts(`in`)
//            }
//
//            override fun newArray(size: Int): Array<Posts?> {
//                return arrayOfNulls(size)
//            }
//        }
//    }

    companion object CREATOR : Parcelable.Creator<Posts> {
                var postsList: List<Posts>? = null

        override fun createFromParcel(parcel: Parcel): Posts {
            return Posts(parcel)
        }

        override fun newArray(size: Int): Array<Posts?> {
            return arrayOfNulls(size)
        }
    }
}
