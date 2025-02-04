package surf.express.nord.proton.vpn.presentation.ui.profile

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import surf.express.nord.proton.vpn.R
import surf.express.nord.proton.vpn.databinding.FragmentProfileBinding
import surf.express.nord.proton.vpn.domain.model.Package
import surf.express.nord.proton.vpn.domain.model.Status
import surf.express.nord.proton.vpn.presentation.MyApp
import surf.express.nord.proton.vpn.presentation.base.BaseFragment
import surf.express.nord.proton.vpn.presentation.ui.MainTabFragment
import surf.express.nord.proton.vpn.presentation.ui.MainTabUIDelegate
import surf.express.nord.proton.vpn.presentation.ui.ShareViewModel
import surf.express.nord.proton.vpn.presentation.utils.*
import surf.express.nord.proton.vpn.presentation.widget.bottomnav.BottomNavBar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile),
    MainTabFragment.OnTabChanged, View.OnClickListener {
    private val shareViewModel: ShareViewModel by activityViewModels()

    private val delegate: MainTabUIDelegate? by lazy {
        return@lazy FragmentUtils.getParent(this, MainTabUIDelegate::class.java)
    }

    override fun initBinding(view: View): FragmentProfileBinding {
        return FragmentProfileBinding.bind(view)
    }

    override fun initView() {
        binding.tvEdit.setOnClickListener(this)
        binding.tvUpgrade.setOnClickListener(this)
        binding.tvLogout.setOnClickListener(this)
    }

    override fun initObserve() {
        shareViewModel.userLiveData.observe(viewLifecycleOwner) {
            binding.tvEmail.text = it.email
        }

        shareViewModel.purchaseLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.tvUpgrade.visibility = View.GONE
                    if (Feature.FEATURE_CANCEL_SUBSCRIPTION) {
                        binding.viewPackage.visibility = View.VISIBLE
                    }
                    val calendar = Calendar.getInstance()
                        .apply {
                            timeInMillis = it.data?.purchaseTime ?: 0L
                        }
                    val packages = shareViewModel.user?.packages ?: listOf()
                    val packageIdMonth =
                        packages.firstOrNull { p: Package -> p.packageDuration == "monthly" }?.packageId

                    val isMonthly = it.data?.sku == packageIdMonth

                    binding.tvAccountType.text = if (isMonthly) {
                        calendar.add(Calendar.MONTH, 1)
                        getString(R.string.gold_monthly_package, calendar.toStringWithPattern())
                    } else {
                        calendar.add(Calendar.YEAR, 1)
                        getString(R.string.gold_yearly_package, calendar.toStringWithPattern())
                    }
                }
                else -> {
                    binding.tvAccountType.text = getString(R.string.free)
                    binding.tvUpgrade.visibility = View.VISIBLE
                    binding.viewPackage.visibility = View.GONE
                }
            }
        }
    }

    override fun onChange(tabIndex: Int) {
        if (tabIndex != BottomNavBar.TAB_PROFILE) {
            return
        }
        activity?.updateColorStatusBar(R.color.colorPrimary)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.tvUpgrade -> {
                delegate?.setCurrentTab(BottomNavBar.TAB_PREMIUM)
            }
            binding.tvEdit -> {
//                findNavController().navigate(R.id.action_mainFragment_to_newPasswordFragment)
            }
            binding.tvLogout -> {
                removePref(SharePrefs.KEY_USER_ID)
                findNavController().navigate(R.id.action_mainFragment_to_splashFragment)
            }
        }
    }
}