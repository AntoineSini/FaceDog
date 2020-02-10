package fr.isen.facedog

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class User {
    var userId: String? = null
    var username: String? = null
    var email: String? = null
}