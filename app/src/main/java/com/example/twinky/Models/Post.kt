package com.example.twinky.Models

class Post {

    var postUtl: String = ""
    var caption: String = ""
    var uid: String = ""
    var time: String = ""
    var nameGroup: String = ""


    constructor()

    constructor(postUtl: String, caption: String) {
        this.postUtl = postUtl
        this.caption = caption
    }

    constructor(postUtl: String, caption: String, uid: String, time: String) {
        this.postUtl = postUtl
        this.caption = caption
        this.uid = uid
        this.time = time
    }

    constructor(postUtl: String, caption: String, uid: String, time: String, nameGroup: String) {
        this.postUtl = postUtl
        this.caption = caption
        this.uid = uid
        this.time = time
        this.nameGroup = nameGroup
    }


}