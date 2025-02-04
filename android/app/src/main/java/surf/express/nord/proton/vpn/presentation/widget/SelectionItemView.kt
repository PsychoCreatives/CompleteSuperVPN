package surf.express.nord.proton.vpn.presentation.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import surf.express.nord.proton.vpn.R
import surf.express.nord.proton.vpn.databinding.ItemSelectionBinding

class SelectionItemView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {
    companion object {
        const val ACTION_CHECK_BOX = 0
        const val ACTION_PREMIUM = 1
        const val ACTION_ARROW = 2
    }

    private var binding: ItemSelectionBinding =
        ItemSelectionBinding.inflate(LayoutInflater.from(context), this)

    init {
        initAttrs(context, attrs)
    }

    private fun initAttrs(context: Context?, attrs: AttributeSet?) {
        context?.obtainStyledAttributes(attrs, R.styleable.SelectionItemView)
            ?.apply {
                //Flag
                val icon = this.getDrawable(R.styleable.SelectionItemView_siv_icon)
                setFlag(icon)
                //Title
                val title = this.getString(R.styleable.SelectionItemView_siv_title)
                setTitle(title)

                //Descripton
                val description = this.getString(R.styleable.SelectionItemView_siv_description)
                setDescription(description)

                //Price
                val price = this.getString(R.styleable.SelectionItemView_siv_price)
                setPrice(price)

                //Action
                val ordinal = this.getInt(R.styleable.SelectionItemView_siv_action, 0)
                setEndAction(ordinal)
            }
            ?.recycle()
    }

    private fun setFlag(icon: Drawable?) {
        if (icon != null) {
            binding.imgFlag.setImageDrawable(icon)
            binding.imgFlag.visibility = View.VISIBLE
        } else {
            binding.imgFlag.visibility = View.GONE
        }
    }

    fun setFlag(resId: Int) {
        if (resId != -1) {
            binding.imgFlag.setImageResource(resId)
            binding.imgFlag.visibility = View.VISIBLE
        } else {
            binding.imgFlag.visibility = View.GONE
        }
    }

    fun setTitle(title: String?) {
        binding.tvTitle.text = title
    }

    fun setDescription(description: String?) {
        if (description != null) {
            binding.tvDescription.text = description
            binding.tvDescription.visibility = View.VISIBLE
        } else {
            binding.tvDescription.visibility = View.GONE
        }
    }

    fun setEndAction(ordinal: Int) {
        val iconRes = when (ordinal) {
            ACTION_PREMIUM -> R.drawable.ic_crowd
            ACTION_ARROW -> R.drawable.ic_arrowright
            else -> R.drawable.ic_check_state
        }

        binding.imgCheck.setBackgroundResource(iconRes)
    }

    fun setPrice(price: String?) {
        if (price != null) {
            binding.tvPrice.text = price
            binding.tvPrice.visibility = View.VISIBLE
        } else {
            binding.tvPrice.visibility = View.GONE
        }
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        binding.imgCheck.isChecked = selected
    }

    fun purchase() {
        this.isEnabled = false
        binding.imgCheck.isEnabled = false
        binding.tvTitle.append(context?.getString(R.string.current))
        binding.tvTitle.alpha = 0.5f
        binding.tvPrice.alpha = 0.5f
    }

    fun initWith(title: String) {
        this.isEnabled = true
        binding.imgCheck.isEnabled = true
        binding.tvTitle.text = title
        binding.tvTitle.alpha = 1f
        binding.tvPrice.alpha = 1f
    }
}