package com.area.freecture.model

import java.io.Serializable

class ImageModel : Serializable {
    var user_id: String? = null
    var photo_id: String? = null
    var username: String? = null
    var location: String? = null
    var profileImage: String? = null
    var description: String? = null

    var full: String? = null
    var regular: String? = null
    var small: String? = null
    var thumb: String? = null
}
