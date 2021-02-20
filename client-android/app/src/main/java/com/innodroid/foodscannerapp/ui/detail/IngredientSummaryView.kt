package com.innodroid.foodscannerapp.ui.detail

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import com.innodroid.foodscannerapp.R
import com.innodroid.foodscannerapp.databinding.ViewIngredientSummaryBinding
import com.innodroid.foodscannerapp.model.Ingredient
import com.innodroid.foodscannerapp.model.SensitivityLevel

class IngredientSummaryView: FrameLayout {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)

    private var binding: ViewIngredientSummaryBinding? = null

    override fun onFinishInflate() {
        super.onFinishInflate()

        binding = ViewIngredientSummaryBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun load(ingredients: List<Ingredient>) {
        val severe = ingredients.any { it.level == SensitivityLevel.Severe }
        val moderate = ingredients.any { it.level == SensitivityLevel.Moderate }
        val mild = ingredients.any { it.level == SensitivityLevel.Mild }

        if (ingredients.isEmpty()) {
            binding?.containsWarning?.setText(R.string.no_ingredients)
            setWarningColor(R.drawable.ic_baseline_block_24, SensitivityLevel.Unknown)
        } else if (severe && moderate && mild) {
            binding?.containsWarning?.setText(R.string.contains_severe_moderate_mild)
            setWarningColor(R.drawable.ic_baseline_warning_24, SensitivityLevel.Severe)
        } else if (severe && moderate) {
            binding?.containsWarning?.setText(R.string.contains_severe_moderate)
            setWarningColor(R.drawable.ic_baseline_warning_24, SensitivityLevel.Severe)
        } else if (severe && mild) {
            binding?.containsWarning?.setText(R.string.contains_severe_mild)
            setWarningColor(R.drawable.ic_baseline_warning_24, SensitivityLevel.Severe)
        } else if (severe) {
            binding?.containsWarning?.setText(R.string.contains_severe)
            setWarningColor(R.drawable.ic_baseline_warning_24, SensitivityLevel.Severe)
        } else if (moderate && mild) {
            binding?.containsWarning?.setText(R.string.contains_moderate_mild)
            setWarningColor(R.drawable.ic_baseline_warning_24, SensitivityLevel.Moderate)
        } else if (moderate) {
            binding?.containsWarning?.setText(R.string.contains_moderate)
            setWarningColor(R.drawable.ic_baseline_warning_24, SensitivityLevel.Moderate)
        } else if (mild) {
            binding?.containsWarning?.setText(R.string.contains_mild)
            setWarningColor(R.drawable.ic_baseline_warning_24, SensitivityLevel.Mild)
        } else {
            binding?.containsWarning?.setText(R.string.contains_none)
            setWarningColor(R.drawable.ic_baseline_thumb_up_24, SensitivityLevel.None)
        }
    }

    fun showLoading() {
        binding?.containsWarning?.setText(R.string.searching)
        setWarningColor(R.drawable.ic_baseline_search_24, SensitivityLevel.Unknown)
    }

    fun showError() {
        binding?.containsWarning?.setText(R.string.error_loading)
        setWarningColor(R.drawable.ic_baseline_warning_24, SensitivityLevel.Moderate)
    }

    private fun setWarningColor(@DrawableRes drawable: Int, severity: SensitivityLevel) {
        val color = context.getColor(severity.color)
        val drawable = context.getDrawable(drawable)!!.mutate()
        drawable.setTint(color)
        binding?.containsWarning?.setTextColor(color)
        binding?.containsWarning?.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
    }
}
