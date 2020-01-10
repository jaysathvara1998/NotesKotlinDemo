package com.example.noteskotlindemo.Model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Task {
    var title: String? = null
    var note: String? = null
    var id: String? = null

    constructor()
    constructor(title: String?, note: String?, id: String?) {
        this.title = title
        this.note = note
        this.id = id
    }
}