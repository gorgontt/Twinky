package com.example.twinky.Models

class Reel {
    var reelUtl: String = ""
    var caption: String = ""
    var nameChat: String = ""
    var profileLink: String? = null

    constructor()

    constructor(reelUtl: String, caption: String) {
        this.reelUtl = reelUtl
        this.caption = caption
    }

    constructor(reelUtl: String, caption: String, profileLink: String) {
        this.reelUtl = reelUtl
        this.caption = caption
        this.profileLink = profileLink
    }

    constructor(reelUtl: String, caption: String, nameChat: String, profileLink: String?) {
        this.reelUtl = reelUtl
        this.caption = caption
        this.nameChat = nameChat
        this.profileLink = profileLink
    }
}