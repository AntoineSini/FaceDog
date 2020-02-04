package fr.isen.facedog

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class User {
    var username: String? = null
    var email: String? = null
}