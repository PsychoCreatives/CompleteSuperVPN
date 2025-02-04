package surf.express.nord.proton.vpn.data.remote.model

import com.google.gson.annotations.SerializedName



data class ServerObject(
    @SerializedName("caFile") val caFile: String,
    @SerializedName("caFileName") val caFileName: String,
    @SerializedName("country") val country: String,
    @SerializedName("countryCode") val countryCode: String,
    @SerializedName("p_nsm") val p_nsm: String,
    @SerializedName("premium") val premium: Boolean,
    @SerializedName("recommend") val recommend: Boolean,
    @SerializedName("state") val state: String,
    @SerializedName("status") val status: Boolean,
    @SerializedName("id") val id: String,
    @SerializedName("ipAddress") val ipAddress: String,

    @SerializedName("u_nsm") val u_nsm: String
)

data class ServersResponse(
    @SerializedName("data") val data: List<surf.express.nord.proton.vpn.data.remote.model.ServerObject>,
    @SerializedName("error") val error: Int,
    @SerializedName("message") val message: String
)