package surf.express.nord.proton.vpn.data.remote.model


import com.google.gson.annotations.SerializedName



data class AdsObject(
    @SerializedName("adsId")
    val adsId: String,
    @SerializedName("adsPlatform")
    val adsPlatform: String,
    @SerializedName("adsStatus")
    val adsStatus: Boolean,
    @SerializedName("adsType")
    val adsType: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("__v")
    val v: Int
)

data class AdsResponse(
    @SerializedName("data")
    val data: List<surf.express.nord.proton.vpn.data.remote.model.AdsObject>,
    @SerializedName("success")
    val success: Int
)