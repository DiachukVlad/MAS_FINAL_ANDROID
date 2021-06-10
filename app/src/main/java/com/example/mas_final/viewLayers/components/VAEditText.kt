package com.example.mas_final.viewLayers.components

import android.content.Context
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.mas_final.R
import com.example.mas_final.databinding.VaEditTextBinding
import com.example.mas_final.helpers.dp


class VAEditText(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {
    var hintText: String = ""
        set(value) {
            binding.editText.hint = value
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

    init {
        setupAttributes(attrs)

        binding.editText.setOnFocusChangeListener { v, hasFocus ->
            binding.root.elevation = if (hasFocus) 6f.dp else 0f
        }
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        val arr = context.theme.obtainStyledAttributes(attrs, R.styleable.VAEditText, 0, 0)

        if (arr.hasValue(R.styleable.VAEditText_hintText)) {
            hintText = arr.getString(R.styleable.VAEditText_hintText) ?: ""
        }
        if (arr.hasValue(R.styleable.VAEditText_icon)) {
            icon = arr.getResourceId(R.styleable.VAEditText_icon, 0)
        }
        if (arr.hasValue(R.styleable.VAEditText_password)) {
            isPassword = arr.getBoolean(R.styleable.VAEditText_password, false)
        }
    }
}