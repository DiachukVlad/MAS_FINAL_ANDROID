package com.example.mas_final.viewLayers.components

import android.content.Context
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.example.mas_final.R
import com.example.mas_final.databinding.VaEditTextBinding
import com.example.mas_final.helpers.dp


class VAField(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {
    var text: String = ""
        set(value) {
            binding.editText.hint = value
            binding.textView.text = value
            field = value
        }

    var icon: Int = 0
        set(value) {
            binding.image.setImageResource(value)
            field = value
        }

    var binding: VaEditTextBinding =
        VaEditTextBinding.inflate(LayoutInflater.from(context), this, true)

    var isPassword: Boolean = false
        set(value) {
            field = value
            binding.editText.transformationMethod = PasswordTransformationMethod()
        }

    var isButton: Boolean = false
        set(value) {
            field = value
            binding.editText.isVisible = !value
            binding.textView.isVisible = value
        }
    init {
        setupAttributes(attrs)

        binding.editText.setOnFocusChangeListener { v, hasFocus ->
            binding.root.elevation = if (hasFocus) 6f.dp else 0f
        }
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        val arr = context.theme.obtainStyledAttributes(attrs, R.styleable.VAField, 0, 0)

        if (arr.hasValue(R.styleable.VAField_hintText)) {
            text = arr.getString(R.styleable.VAField_hintText) ?: ""
        }
        if (arr.hasValue(R.styleable.VAField_icon)) {
            icon = arr.getResourceId(R.styleable.VAField_icon, 0)
        }
        if (arr.hasValue(R.styleable.VAField_password)) {
            isPassword = arr.getBoolean(R.styleable.VAField_password, false)
        }
        if (arr.hasValue(R.styleable.VAField_isButton)) {
            isButton = arr.getBoolean(R.styleable.VAField_isButton, false)
        }
    }
}