package com.bumptech.glide.supportapp.github._1062_html_gif_spans;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.supportapp.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Collection;

public class GlideImageGetter implements Html.ImageGetter, Drawable.Callback {
    @SuppressWarnings("unused")
    private static final String TAG = GlideImageGetter.class.getSimpleName();
    private final RequestBuilder<Drawable> drawableRequestBuilder;
    private final RequestBuilder<GifDrawable> gifDrawableRequestBuilder;
    //    private final RequestManager glide;
    private final Collection<Target> imageTargets = new ArrayList<>();
    private final int size;
    private final TextView targetView;
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final Context context;
    private RequestManager requestManager;

    public GlideImageGetter(Context context, RequestManager requestManager, TextView targetView, int size) {
        this.context = context.getApplicationContext();
        this.requestManager = requestManager;
        this.drawableRequestBuilder = createDrawableGlideRequest(requestManager);
        this.gifDrawableRequestBuilder = createGifDrawableGlideRequest(requestManager);
        this.targetView = targetView;
        this.size = size;
        targetView.setTag(this);
    }

    public static void clear(TextView view) {
        view.setText(null);
        Object tag = view.getTag();
        if (tag instanceof GlideImageGetter) {
            ((GlideImageGetter) tag).clear();
            view.setTag(null);
        }
    }

    private RequestBuilder<Drawable> createDrawableGlideRequest(RequestManager requestManager) {
        RequestBuilder<Drawable> load = requestManager.asDrawable().fitCenter();
        return load.listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        });
    }

    private RequestBuilder<GifDrawable> createGifDrawableGlideRequest(RequestManager requestManager) {
        RequestBuilder<GifDrawable> load = requestManager.asGif().fitCenter();
        return load.listener(new RequestListener<GifDrawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                resource.start();
                return false;
            }
        });
    }

    public void clear() {
        for (Target target : imageTargets) {
            requestManager.clear(target);
        }
    }

    @Override
    public Drawable getDrawable(String url) {
        if (CommonUtils.isGif(url)) {
            GifWrapperTarget imageTarget = new GifWrapperTarget(size);
            Drawable asyncWrapper = imageTarget.getLazyDrawable();
            asyncWrapper.setCallback(this);
            gifDrawableRequestBuilder.load(url).into(imageTarget);
            imageTargets.add(imageTarget);
            return asyncWrapper;
        } else {
            WrapperTarget imageTarget = new WrapperTarget(size);
            Drawable asyncWrapper = imageTarget.getLazyDrawable();
            asyncWrapper.setCallback(this);
            drawableRequestBuilder.load(url).into(imageTarget);
            imageTargets.add(imageTarget);
            return asyncWrapper;
        }
    }

    @Override
    public void invalidateDrawable(@NonNull Drawable who) {
        targetView.invalidate();
    }

    @Override
    public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {
    }

    @Override
    public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {

    }
}
