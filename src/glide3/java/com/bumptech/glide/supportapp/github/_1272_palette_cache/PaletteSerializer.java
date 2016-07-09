package com.bumptech.glide.supportapp.github._1272_palette_cache;

import java.io.*;
import java.util.*;

import android.support.v7.graphics.*;
import android.support.v7.graphics.Palette.*;
import android.util.Log;

class PaletteSerializer implements Serializable {
	private /*final*/ Palette palette;
	public PaletteSerializer(Palette palette) {
		this.palette = palette;
	}
	public Palette getPalette() {
		return palette;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		List<Swatch> swatches = palette.getSwatches();
		out.writeInt(swatches.size());
		for (Swatch swatch : swatches) {
			out.writeInt(swatch.getRgb());
			out.writeInt(swatch.getPopulation());
		}

		List<Target> targets = palette.getTargets();
		out.writeInt(targets.size());
		for (Target target : targets) {
			out.writeBoolean(target.isExclusive());
			out.writeFloat(target.getPopulationWeight());
			out.writeFloat(target.getLightnessWeight());
			out.writeFloat(target.getMinimumLightness());
			out.writeFloat(target.getTargetLightness());
			out.writeFloat(target.getMaximumLightness());
			out.writeFloat(target.getSaturationWeight());
			out.writeFloat(target.getMinimumSaturation());
			out.writeFloat(target.getTargetSaturation());
			out.writeFloat(target.getMaximumSaturation());
		}
	}

	private void readObject(ObjectInputStream in) throws IOException {
		int swatchCount = in.readInt();
		List<Swatch> swatches = new ArrayList<>(swatchCount);
		for (int i = 0; i < swatchCount; i++) {
			int color = in.readInt();
			int population = in.readInt();
			swatches.add(new Swatch(color, population));
		}
		Builder builder = new Builder(swatches);
		int targets = in.readInt();
		for (int i = 0; i < targets; i++) {
			Target target = new Target.Builder()
					.setExclusive(in.readBoolean())
					.setPopulationWeight(in.readFloat())
					.setLightnessWeight(in.readFloat())
					.setMinimumLightness(in.readFloat())
					.setTargetLightness(in.readFloat())
					.setMaximumLightness(in.readFloat())
					.setSaturationWeight(in.readFloat())
					.setMinimumSaturation(in.readFloat())
					.setTargetSaturation(in.readFloat())
					.setMaximumSaturation(in.readFloat())
					.build();
			builder.addTarget(resolveSpecialTarget(target));
		}
		palette = builder.generate();
	}

	private static final Map<String, Target> SPECIAL_TARGETS = new HashMap<>();

	static {
		SPECIAL_TARGETS.put(key(Target.VIBRANT), Target.VIBRANT);
		SPECIAL_TARGETS.put(key(Target.LIGHT_VIBRANT), Target.LIGHT_VIBRANT);
		SPECIAL_TARGETS.put(key(Target.DARK_VIBRANT), Target.DARK_VIBRANT);
		SPECIAL_TARGETS.put(key(Target.MUTED), Target.MUTED);
		SPECIAL_TARGETS.put(key(Target.DARK_MUTED), Target.DARK_MUTED);
		SPECIAL_TARGETS.put(key(Target.LIGHT_MUTED), Target.LIGHT_MUTED);
	}

	/**
	 * Need to resolve the serialized version to the named constant,
	 * otherwise the {@link Palette Palette.get*Swatch()} methods won't work.
	 * This is due to {@link Target} not having a hashCode method.
	 */
	private Target resolveSpecialTarget(Target target) {
		Target special = SPECIAL_TARGETS.get(key(target));
		return special != null? special : target;
	}

	/**
	 * Simply concat everything together to create a key.
	 * Paranoidly separate numbers to prevent collisions ("1" + "23" == "12" + "3").
	 */
	@SuppressWarnings("StringBufferReplaceableByString")
	private static String key(Target target) {
		StringBuilder sb = new StringBuilder();
		sb.append(target.isExclusive());
		sb.append("_").append(target.getPopulationWeight());
		sb.append("_").append(target.getLightnessWeight());
		sb.append("_").append(target.getMinimumLightness());
		sb.append("_").append(target.getTargetLightness());
		sb.append("_").append(target.getMaximumLightness());
		sb.append("_").append(target.getSaturationWeight());
		sb.append("_").append(target.getMinimumSaturation());
		sb.append("_").append(target.getTargetSaturation());
		sb.append("_").append(target.getMaximumSaturation());
		return sb.toString();
	}

	public static void dump(Palette palette) {
		Log.i("PALETTE", "Vibrant: " + palette.getVibrantSwatch());
		Log.i("PALETTE", "LightVibrant: " + palette.getLightVibrantSwatch());
		Log.i("PALETTE", "DarkVibrant: " + palette.getDarkVibrantSwatch());
		Log.i("PALETTE", "Muted: " + palette.getMutedSwatch());
		Log.i("PALETTE", "LightMuted: " + palette.getLightMutedSwatch());
		Log.i("PALETTE", "DarkMuted: " + palette.getDarkMutedSwatch());

		List<Swatch> swatches = palette.getSwatches();
		for (int i = 0; i < swatches.size(); i++) {
			Log.i("PALETTE", i + " Swatch: " + swatches.get(i));
		}
		List<Target> targets = palette.getTargets();
		for (int i = 0; i < targets.size(); i++) {
			Target target = targets.get(i);
			Log.i("PALETTE", i + " Target: " + target + " -> " + palette.getSwatchForTarget(target));
		}
	}
}
