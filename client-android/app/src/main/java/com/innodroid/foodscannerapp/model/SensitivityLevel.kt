package com.innodroid.foodscannerapp.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.innodroid.foodscannerapp.R

enum class SensitivityLevel(@ColorRes val color: Int, @DrawableRes val icon: Int) {
    Unknown(R.color.gray, R.drawable.ic_baseline_thumb_up_24),
    None(R.color.green, R.drawable.ic_baseline_thumb_up_24),
    Mild(R.color.yellow, R.drawable.ic_baseline_warning_24),
    Moderate(R.color.orange, R.drawable.ic_baseline_warning_24),
    Severe(R.color.red, R.drawable.ic_baseline_warning_24)
}
