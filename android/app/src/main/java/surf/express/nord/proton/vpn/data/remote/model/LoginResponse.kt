package surf.express.nord.proton.vpn.data.remote.model

import com.google.gson.annotations.SerializedName


data class UserObject(
    @SerializedName("createdAt")
    val createdAt: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val id: String? = null,
    val lastName: String? = null,
    @SerializedName("isAnonymous")
    val isAnonymous: Boolean = false
)

data class LoginResponse(
    val data: surf.express.nord.proton.vpn.data.remote.model.UserObject? = null,
    val success: Int? = null,
    val message: String? = null
)
