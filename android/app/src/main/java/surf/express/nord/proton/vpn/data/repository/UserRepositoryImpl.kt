package surf.express.nord.proton.vpn.data.repository

import surf.express.nord.proton.vpn.data.remote.ApiService
import surf.express.nord.proton.vpn.domain.model.Ads
import surf.express.nord.proton.vpn.domain.model.User
import surf.express.nord.proton.vpn.domain.repository.UserRepository
import surf.express.nord.proton.vpn.domain.model.Package
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {
    @Inject
    lateinit var apiService: ApiService


    override suspend fun register(param: MutableMap<String, Any>): User {
        val response = apiService.signup(param)
        return User.fromObject(response.data)
    }

    override suspend fun login(param: MutableMap<String, Any>): User {
        val loginResponse = apiService.login(param)
        return User.fromObject(loginResponse.data)
    }

    override suspend fun otp(param: MutableMap<String, Any>): User {
        val response = apiService.otp(param)
        return User.fromObject(response.data)
    }

    override suspend fun profile(param: MutableMap<String, Any>): User {
        val response = apiService.profile(param)
        return User.fromObject(response.data)
    }

    override suspend fun createAnonymousUser(param: MutableMap<String, Any>): User {
        val response = apiService.createAnonymousUser(param)
        return User.fromObject(response.data)
    }

    override suspend fun updateTotalUploadDownload(param: MutableMap<String, Any>): User {
        val response = apiService.updateTotalUploadDownload(param)
        return User.fromObject(response.data)
    }

    override suspend fun packages(param: MutableMap<String, Any>): List<Package> {
        val response = apiService.packages(param)
        return response.data.map { packageObject -> Package.fromObject(packageObject) }
    }

    override suspend fun ads(param: MutableMap<String, Any>): List<Ads> {
        val response = apiService.ads(param)
        return response.data.map { adsObject -> Ads.fromObject(adsObject) }
    }

    override suspend fun subscription(param: MutableMap<String, Any>) {
        apiService.subscription(param)
    }
}