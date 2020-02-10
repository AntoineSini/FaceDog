package fr.isen.facedog

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties

data class Publication(
    var publication_id : String,
    var publication_title: String?,
    var publication_description: String?,
    var user_id: String?
)
