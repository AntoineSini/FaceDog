package fr.isen.facedog

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties

data class User (
    var user_id: String?,
    var username: String?,
    var email: String?
)