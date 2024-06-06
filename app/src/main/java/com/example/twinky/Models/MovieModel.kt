package com.example.twinky.Models


class MovieModel(title: String?, imageResId: Int) {
    private var title: String
    private var imageResId: Int

    init {
        this.title = title!!
        this.imageResId = imageResId
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(name: String?) {
        title = name!!
    }


    fun getImageResId(): Int {
        return imageResId
    }

    fun setImageResId(imageResId: Int) {
        this.imageResId = imageResId
    }
}