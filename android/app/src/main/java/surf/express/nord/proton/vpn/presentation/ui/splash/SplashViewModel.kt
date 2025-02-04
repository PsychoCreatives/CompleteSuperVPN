package surf.express.nord.proton.vpn.presentation.ui.splash

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import surf.express.nord.proton.vpn.domain.model.Resource
import surf.express.nord.proton.vpn.domain.model.User
import surf.express.nord.proton.vpn.domain.repository.UserRepository
import surf.express.nord.proton.vpn.presentation.base.BaseViewModel
import surf.express.nord.proton.vpn.presentation.utils.SharePrefs.KEY_USER_ID
import surf.express.nord.proton.vpn.presentation.utils.getDeviceId
import surf.express.nord.proton.vpn.presentation.utils.getStringPref
import surf.express.nord.proton.vpn.presentation.utils.putStringPref
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val userRepository: UserRepository
) : BaseViewModel() {
    private val _userMutableLiveData = MutableLiveData<Resource<User>>()
    val userLiveData: LiveData<Resource<User>> = _userMutableLiveData
    val user: User? by lazy {
        return@lazy userLiveData.value?.data
    }

    fun execute() {
        _userMutableLiveData.postValue(Resource.loading(null))

        viewModelScope.launch(exceptionHandler) {
            loadUser()
        }
    }

    private suspend fun loadUser() {
        //region#load profile
        val deviceId = appContext.getDeviceId()
        var userId = getStringPref(KEY_USER_ID)
        val param = mutableMapOf<String, Any>()

        if (userId == null) {
            param["deviceId"] = deviceId
            userId =
                userRepository.createAnonymousUser(param).id ?: throw NullPointerException("Ops...")
        }
        putStringPref(KEY_USER_ID, userId)
        param.clear()
        param["userId"] = userId as Any
        val user = userRepository.profile(param)
        //endregion

        //region#load packages
        param["os"] = "android"
        val packages = userRepository.packages(param)
        user.packages = packages

        val ads = userRepository.ads(param)
        user.ads = ads
        //endregion

        _userMutableLiveData.postValue(Resource.success(user))
    }


    override fun handleError(msg: String?) {
        _userMutableLiveData.postValue(Resource.error(msg, null))
    }
}