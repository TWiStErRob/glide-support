package com.bumptech.glide.supportapp.utils;

import android.transition.Transition;
import android.transition.Transition.TransitionListener;
import android.util.Log;

import androidx.fragment.app.Fragment;

public class LoggingTransitionListener implements TransitionListener {
	private final String who;
	public LoggingTransitionListener(String who) {
		this.who = who;
	}
	private void log(String method, Transition transition) {
		Log.v("TRANS", who + "." + method + ": " + transition.hashCode());
	}
	@Override public void onTransitionStart(Transition transition) {
		log("onTransitionStart", transition);
	}
	@Override public void onTransitionEnd(Transition transition) {
		log("onTransitionEnd", transition);
	}
	@Override public void onTransitionCancel(Transition transition) {
		log("onTransitionCancel", transition);
	}
	@Override public void onTransitionPause(Transition transition) {
		log("onTransitionPause", transition);
	}
	@Override public void onTransitionResume(Transition transition) {
		log("onTransitionResume", transition);
	}

	public static void listenForAll(String where, Fragment fragment) {
		listen(where, "getEnterTransition", fragment.getEnterTransition());
		listen(where, "getExitTransition", fragment.getExitTransition());
		listen(where, "getReenterTransition", fragment.getReenterTransition());
		listen(where, "getReturnTransition", fragment.getReturnTransition());
		if (fragment.getSharedElementEnterTransition() == fragment.getSharedElementReturnTransition()) {
			listen(where, "getSharedElementEnter|ReturnTransition", fragment.getSharedElementEnterTransition());
		} else {
			listen(where, "getSharedElementEnterTransition", fragment.getSharedElementEnterTransition());
			listen(where, "getSharedElementReturnTransition", fragment.getSharedElementReturnTransition());
		}
	}

	private static void listen(String where, String name, Object transition) {
		listen(where + "." + name, transition);
	}

	public static void listen(String name, Object transition) {
		if (transition != null) {
			Transition t = (Transition)transition;
			t.addListener(new LoggingTransitionListener(name));
		}
	}
}
