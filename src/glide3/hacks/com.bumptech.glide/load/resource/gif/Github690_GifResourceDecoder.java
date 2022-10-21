package com.bumptech.glide.load.resource.gif;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.gifdecoder.GifHeader;

/**
 * .asGif().decoder(new FixedGifResourceDecoder(context))
 */
public class Github690_GifResourceDecoder extends GifResourceDecoder {
	private static final GifHeaderParserPool PARSER_POOL = new GifHeaderParserPool(); // package private class
	private static final GifDecoderPool DECODER_POOL = new Issue690GifDecoderPool();
	public Github690_GifResourceDecoder(Context context) {
		super(context, Glide.get(context).getBitmapPool(), PARSER_POOL, DECODER_POOL); // package private ctor
	}

	static class Issue690GifDecoderPool extends GifDecoderPool { // package private class
		private static final byte[] NO_DATA = new byte[0];
		private static final GifHeader EMPTY_HEADER = new GifHeader();
		@Override public synchronized void release(GifDecoder decoder) {
			decoder.clear(); // clear
			decoder.setData(EMPTY_HEADER, NO_DATA); // set dummy data
			super.release(decoder); // clear again so only dummy data is leaked
		}
	}
}
