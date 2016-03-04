package com.bumptech.glide;

import com.bumptech.glide.request.animation.GlideAnimationFactory;

public class Github840_GlideRequestBuilderAccessor {
	public static <ModelType, DataType, ResourceType, TranscodeType> GenericRequestBuilder<ModelType, DataType, ResourceType, TranscodeType> animateAlt(
			GenericRequestBuilder<ModelType, DataType, ResourceType, TranscodeType> request,
			GlideAnimationFactory<TranscodeType> factory) {
		return request.animate(factory);
	}
	public static <TranscodeType> void animate(GenericRequestBuilder<?, ?, ?, TranscodeType> request,
			GlideAnimationFactory<TranscodeType> factory) {
		request.animate(factory);
	}
}
