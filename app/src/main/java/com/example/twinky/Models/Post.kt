package com.example.twinky.Models

class Post {

    var postUtl: String = ""
    var caption: String = ""

    constructor()

    constructor(postUtl: String, caption: String) {
        this.postUtl = postUtl
        this.caption = caption
    }

}