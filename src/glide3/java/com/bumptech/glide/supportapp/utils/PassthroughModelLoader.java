package com.bumptech.glide.supportapp.utils;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelLoader;

public class PassthroughModelLoader<Result, Source extends Result> implements ModelLoader<Source, Result> {
	@Override public DataFetcher<Result> getResourceFetcher(final Source model, int width, int height) {
		return new CastingDataFetcher<Source, Result>(model); // upcasting is ok
	}
	/** Extremely unsafe, use with care. */
	private static class CastingDataFetcher<Source, Result> implements DataFetcher<Result> {
		private final Source model;

		public CastingDataFetcher(Source model) {
			this.model = model;
		}
		@SuppressWarnings("unchecked")
		@Override public Result loadData(Priority priority) throws Exception {
			return (Result)model;
		}
		@Override public void cleanup() {
		}
		@Override public String getId() {
			return model.toString();
		}
		@Override public void cancel() {
		}
	}
}
