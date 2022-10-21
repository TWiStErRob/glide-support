package com.bumptech.glide.supportapp.github._1406_drawable_fading;

import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;

import androidx.annotation.NonNull;

public class GeneralizingTranscoder<ResourceType, Specific extends General, General>
		implements ResourceTranscoder<ResourceType, General> {
	private final ResourceTranscoder<ResourceType, Specific> transcoder;

	public GeneralizingTranscoder(@NonNull ResourceTranscoder<ResourceType, Specific> transcoder) {
		this.transcoder = transcoder;
	}

	@SuppressWarnings("unchecked")
	@Override public Resource<General> transcode(Resource<ResourceType> toTranscode) {
		// should be safe because we're upcasting the generic type and Resource only has outgoing generics
		return (Resource<General>)transcoder.transcode(toTranscode);
	}

	@Override public String getId() {
		return transcoder.getId();
	}
}
