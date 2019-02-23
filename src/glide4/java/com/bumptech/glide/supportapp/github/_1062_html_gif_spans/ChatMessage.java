package com.bumptech.glide.supportapp.github._1062_html_gif_spans;

import java.util.Locale;

public class ChatMessage {
    private final String name;
    private final String avatarUrl;
    private final String message;

    public ChatMessage(String name, String message) {
        this(name, avatar(name), message);
    }

    public ChatMessage(String name, String avatarUrl, String message) {
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.message = message;
    }

    /**
     * Fake url generation
     */
    private static String avatar(String name) {
        int bgColor = name.hashCode() & 0xffffff;
        int fgColor = (~bgColor) & 0xffffff;
        return String.format(Locale.ROOT, "http://placehold.it/256x256/%06x/%06x?text=%s", bgColor, fgColor, name);
    }

    public String getUserName() {
        return name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "ChatMessage{name=\"%s\", avatarUrl=\"%s\", message=\"%s\"",
                name, avatarUrl, message);
    }
}
