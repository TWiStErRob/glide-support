package com.bumptech.glide.supportapp.stackoverflow._31867322_generate_bitmap;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelLoader;

/** Boilerplate because of the degeneration in ModelType == DataType, but important for caching
 * @see GeneratingAdapter#generator */
class GeneratePassthroughModelLoader implements ModelLoader<GenerateParams, GenerateParams> {
	@Override public DataFetcher<GenerateParams> getResourceFetcher(final GenerateParams model, int width, int height) {
		return new DataFetcher<GenerateParams>() {
			@Override public GenerateParams loadData(Priority priority) throws Exception {
				return model;
			}
			@Override public void cleanup() {
			}
			@Override public String getId() {
				return model.getId();
			}
			@Override public void cancel() {
			}
		};
	}
}
