package com.example.twinky.Models

class Reel {
    var reelUtl: String = ""
    var caption: String = ""

    constructor()

    constructor(reelUtl: String, caption: String) {
        this.reelUtl = reelUtl
        this.caption = caption
    }
}