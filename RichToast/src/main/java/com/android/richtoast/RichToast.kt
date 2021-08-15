package com.android.richtoast

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class RichToast private constructor(
    context: Context,
    message: String,
    icon: Int,
    background: Int,
    duration: Duration,
    gravity: Int
) : Toast(context) {
    init {
        val layout = LayoutInflater.from(context).inflate(R.layout.rich_toast_layout, null, false)
        val constraint = layout.findViewById<ConstraintLayout>(R.id.constraint_layout)
        val textView = layout.findViewById<TextView>(R.id.text_view)
        val imageView = layout.findViewById<ImageView>(R.id.image_view)
        textView.text = message
        imageView.setImageResource(icon)
        imageView.animation = AnimationUtils.loadAnimation(context, R.anim.anim_zoom)
        constraint.setBackgroundResource(background)
        this.duration = duration.ordinal
        this.setGravity(gravity, 0, 0)
        this.view = layout
    }

    data class Builder(
        private val context: Context,
        private var message: String = context.getString(R.string.default_message),
        private var icon: Int = R.drawable.ic_success,
        private var duration: Duration = Duration.SHORT,
        private var background: Int = R.drawable.success_bg,
        private var type: Type = Type.SUCCESS,
        private var gravity: Int = Gravity.BOTTOM
    ) {
        fun setMessage(message: String) = apply { this.message = message }
        fun setDuration(duration: Duration) = apply { this.duration = duration }
        fun setType(type: Type) = apply {
            this.icon = when (type) {
                Type.SUCCESS -> R.drawable.ic_success
                Type.ERROR -> R.drawable.ic_error
                Type.INFO -> R.drawable.ic_info
                Type.WARNING -> R.drawable.ic_warning
            }
            this.background = when (type) {
                Type.SUCCESS -> R.drawable.success_bg
                Type.ERROR -> R.drawable.error_bg
                Type.INFO -> R.drawable.info_bg
                Type.WARNING -> R.drawable.warning_bg
            }
        }

        fun setGravity(gravity: Int) = apply { this.gravity = gravity }
        fun build() = RichToast(context, message, icon, background, duration, gravity).show()
    }
}

enum class Duration {
    SHORT, LONG
}

enum class Type {
    SUCCESS, ERROR, WARNING, INFO
}