package com.bumptech.glide.supportapp.github._1062_html_gif_spans;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.supportapp.R;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.applyDimension;

class HtmlChatAdapter extends BaseAdapter {
    private static final Map<String, String> EMOTICONS = new HashMap<>();

    static {
        EMOTICONS.put(":emo_static1:", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e6/Noto_Emoji_KitKat_263a.svg/1200px-Noto_Emoji_KitKat_263a.svg.png");
        EMOTICONS.put(":emo1:", "https://d1j8pt39hxlh3d.cloudfront.net/uploads/party_face_256_1.gif");
        EMOTICONS.put(":emo2:", "https://d1j8pt39hxlh3d.cloudfront.net/uploads/exploding_head_256_1.gif");
        EMOTICONS.put(":emo3:", "https://d1j8pt39hxlh3d.cloudfront.net/uploads/zany_face_256_1.gif");
        EMOTICONS.put(":emo4:", "https://dumielauxepices.net/sites/default/files/mood-clipart-thumb-down-687978-9086403.gif");
    }

    private final List<ChatMessage> messages;
    private final RequestManager glide;

    public HtmlChatAdapter(List<ChatMessage> messages, RequestManager glide) {
        this.messages = messages;
        this.glide = glide;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public ChatMessage getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return messages.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.github_1062_item, parent, false);
            convertView.setTag(new MessageViewHolder(convertView));
        }
        MessageViewHolder holder = (MessageViewHolder) convertView.getTag();
        holder.bind(getItem(position));
        return convertView;
    }

    public void clear(View convertView) {
        Object tag = convertView.getTag();
        if (tag instanceof MessageViewHolder) {
            MessageViewHolder holder = (MessageViewHolder) tag;
            holder.clear();
        }
    }

    @SuppressWarnings("deprecation")
    private void bindEmoticonMessage(TextView target, String html) {
        if (TextUtils.isEmpty(html)) {
            return;
        }
        Matcher matcher = Pattern.compile(":(\\w+):").matcher(html);
        StringBuffer sb = new StringBuffer();
        int emotionCount = 0;
        while (matcher.find()) {
            emotionCount++;
            String image = String.format(Locale.ROOT, "<img src=\"%s\" />", EMOTICONS.get(matcher.group()));
            matcher.appendReplacement(sb, image);
        }
        matcher.appendTail(sb);

        Spanned spanned;
        if (0 < emotionCount) {
            Context context = target.getContext();
            float size = applyDimension(COMPLEX_UNIT_DIP, 32, context.getResources().getDisplayMetrics());
            GlideImageGetter imageGetter = new GlideImageGetter(context, glide, target, (int) size);
            spanned = Html.fromHtml(sb.toString(), imageGetter, null);
        } else {
            spanned = Html.fromHtml(html, null, null);
        }
        target.setText(spanned);
    }

    private class MessageViewHolder {
        final View itemView;
        final TextView nameView;
        final ImageView avatarView;
        final TextView messageView;

        MessageViewHolder(View itemView) {
            this.itemView = itemView;
            this.nameView = (TextView) itemView.findViewById(R.id.github_1062_name);
            this.avatarView = (ImageView) itemView.findViewById(R.id.github_1062_avatar);
            this.messageView = (TextView) itemView.findViewById(R.id.github_1062_message);
        }

        void bind(ChatMessage message) {
            nameView.setText(message.getUserName());
            Context context = avatarView.getContext();
            glide.load(message.getAvatarUrl())
                    .into(avatarView);
            bindEmoticonMessage(messageView, message.getMessage());
        }

        void clear() {
            glide.clear(avatarView);
            nameView.setText(null);
            GlideImageGetter.clear(messageView);
        }
    }
}
