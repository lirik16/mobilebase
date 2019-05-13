package mdev.mobile.app.ui.binding

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.request.RequestOptions
import mdev.mobile.app.R
import mdev.mobile.app.libsconfig.glide.GlideApp

@BindingAdapter("avatarUrl")
fun avatarUrl(view: ImageView, avatarUrl: String?) {
    GlideApp.with(view)
        .load(avatarUrl)
        .centerCrop()
        .placeholder(R.drawable.shape_image_placeholder)
        .apply(RequestOptions.circleCropTransform())
        .into(view)
}

@BindingAdapter("iconUrl")
fun iconUrl(view: ImageView, iconUrl: String?) {
    GlideApp.with(view)
        .load(iconUrl)
        .centerCrop()
        .placeholder(R.drawable.shape_image_placeholder)
        .into(view)
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    val placeholder = CircularProgressDrawable(view.context).apply {
        setStyle(CircularProgressDrawable.LARGE)
        start()
    }

    GlideApp.with(view)
        .load(imageUrl)
        .centerCrop()
        .placeholder(placeholder)
        .error(ColorDrawable(ContextCompat.getColor(view.context, R.color.colorPrimaryDark)))
        .into(view)
}

@BindingAdapter("isGone")
fun visibilityGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) View.GONE else View.VISIBLE
}

@BindingAdapter("isInvisible")
fun visibilityInvisible(view: View, isInvisible: Boolean) {
    view.visibility = if (isInvisible) View.INVISIBLE else View.VISIBLE
}
