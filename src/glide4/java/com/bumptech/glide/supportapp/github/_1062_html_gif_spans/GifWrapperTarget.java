package com.bumptech.glide.supportapp.github._1062_html_gif_spans;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.view.Gravity;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * Glide target for fixed sized Drawables that require sync return.
 * It bridges the gap between sync and async by providing a drawable that can be used immediately,
 * but filling in the contents later.
 * To make it work with animation the user will have subscribe to the Drawable's invalidate events:
 * <pre><code>
 * Drawable asyncWrapper = imageTarget.getLazyDrawable();
 * asyncWrapper.setCallback((Drawable.Callback)this);
 *
 * public void invalidateDrawable(Drawable who) {
 *     targetView.invalidate();
 * }
 * </code></pre>
 */
@SuppressWarnings("deprecation")
@SuppressLint("RestrictedApi")
        // DrawableWrapper is a handy class, wouldn't want to copy-paste it
class GifWrapperTarget extends SimpleTarget<GifDrawable> {
    /**
     * Workaround because the AppCompat DrawableWrapper doesn't support null drawable as the API23 version does
     */
    private final ColorDrawable nullObject = new ColorDrawable(Color.TRANSPARENT);
    private final DrawableWrapper wrapper = new DrawableWrapper(null/* temporarily null until a setDrawable call*/);

    @SuppressWarnings("WeakerAccess")
    public GifWrapperTarget(int size) {
        super(size, size);
        setDrawable(null);
        // set wrapper bounds to fix the size of the view, TextViews don't like ImageSpans changing dimensions
        wrapper.setBounds(0, 0, size, size);
    }

    @SuppressWarnings("WeakerAccess")
    public Drawable getLazyDrawable() {
        return wrapper;
    }

    @Override
    public void onLoadStarted(Drawable placeholder) {
        setDrawable(placeholder);
    }

    @Override
    public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
        // start GlideDrawable, even if it's not animated (these methods are No-op in that case)
//        resource.setRepeatCount(GlideDrawable.LOOP_FOREVER);
//        glideDrawable.start();
        setDrawable(resource);
    }


//    public void onLoadFailed(Exception e, Drawable errorDrawable) {
//        setDrawable(errorDrawable);
//    }

//    @Override
//    public void onResourceReady(AnimatedImageDrawable glideDrawable, GlideAnimation glideAnimation) {
//
//    }

    private void setDrawable(Drawable drawable) {
        if (drawable == null) {
            drawable = nullObject;
        }
        drawable.setBounds(calcBounds(drawable, Gravity.CENTER));
        wrapper.setWrappedDrawable(drawable);
        // invalidate wrapper drawable so it re-draws itself and displays the new wrapped drawable
        wrapper.invalidateSelf();
    }

    /**
     * Align drawable in wrapper in case the image is smaller than the target size.
     */
    private Rect calcBounds(Drawable drawable, @SuppressWarnings("SameParameterValue") int gravity) {
        Rect bounds = new Rect();
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Rect container = wrapper.getBounds();
        if (w == -1 && h == -1) {
            w = container.width();
            h = container.height();
        }
        Gravity.apply(gravity, w, h, container, bounds);
        return bounds;
    }
}
