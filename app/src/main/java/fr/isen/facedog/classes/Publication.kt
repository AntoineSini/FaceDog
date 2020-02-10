package fr.isen.facedog.classes

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties

data class Publication(
    var id : String,
    var title: String?,
    var description: String?,
    var user_id: String?
)
