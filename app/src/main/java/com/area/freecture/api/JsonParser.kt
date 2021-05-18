package com.area.freecture.api

import com.area.freecture.model.ImageModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

object JsonParser {
    fun json2ImageList(jsonObject: String): List<ImageModel> {
        var jsonArray: JSONArray? = null
        try {
            jsonArray = JSONArray(jsonObject)
        } catch (e: JSONException) {

        }
        val imageModelsList = ArrayList<ImageModel>()
        return try {
            if (jsonArray!!.length() > 0) {
                for (i in 0 until jsonArray.length()) {
                    val imagesUrlModel = json2ImageUrl(jsonArray.getJSONObject(i))
                    imageModelsList.add(imagesUrlModel)
                }
            }
            imageModelsList
        } catch (e: JSONException) {
            ArrayList()
        }
    }

    private fun json2ImageUrl(ObjDetail: JSONObject): ImageModel {
        val imagesUrlModel = ImageModel()

        try {
            val objectUrl = ObjDetail.getJSONObject("urls")
            val objectUser = ObjDetail.getJSONObject("user")

            if (ObjDetail.has("id"))
                imagesUrlModel.photo_id = ObjDetail.getString("id")
            if (ObjDetail.has("description"))
                imagesUrlModel.description = ObjDetail.getString("description")

            if (objectUrl.has("thumb"))
                imagesUrlModel.thumb = objectUrl.getString("thumb")

            if (objectUrl.has("small"))
                imagesUrlModel.small = objectUrl.getString("small")

            if (objectUrl.has("regular"))
                imagesUrlModel.regular = objectUrl.getString("regular")

            if (objectUrl.has("full"))
                imagesUrlModel.full = objectUrl.getString("full")

            if (objectUser.has("id"))
                imagesUrlModel.user_id = objectUser.getString("id")

            if (objectUser.has("username"))
                imagesUrlModel.username = objectUser.getString("username")

            if (objectUser.has("location"))
                imagesUrlModel.location = objectUser.getString("location")

            if (objectUser.has("profile_image")) {
                val objectUserProfile = objectUser.getJSONObject("profile_image")
                when {
                    objectUserProfile.has("medium") -> {
                        imagesUrlModel.profileImage = objectUserProfile.getString("medium")
                    }
                    objectUserProfile.has("large") -> {
                        imagesUrlModel.profileImage = objectUserProfile.getString("large")
                    }
                    else -> {
                        imagesUrlModel.profileImage = objectUserProfile.getString("small")
                    }
                }
            }
        } catch (ex: Exception) {

        }
        return imagesUrlModel
    }
}
