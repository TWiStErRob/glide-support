package com.bumptech.glide.supportapp.github._1062_html_gif_spans;

import java.util.*;
import java.util.regex.*;

import android.content.Context;
import android.text.*;
import android.view.*;
import android.widget.*;

import static android.util.TypedValue.*;

import com.bumptech.glide.*;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.utils.LoggingListener;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

class HtmlChatAdapter extends BaseAdapter {
	private final List<ChatMessage> messages;
	private final RequestManager glide;

	public HtmlChatAdapter(List<ChatMessage> messages, RequestManager glide) {
		this.messages = messages;
		this.glide = glide;
	}
	@Override public int getCount() {
		return messages.size();
	}
	@Override public ChatMessage getItem(int position) {
		return messages.get(position);
	}
	@Override public long getItemId(int position) {
		return messages.get(position).hashCode();
	}

	@Override public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.github_1062_item, parent, false);
			convertView.setTag(new MessageViewHolder(convertView));
		}
		MessageViewHolder holder = (MessageViewHolder)convertView.getTag();
		holder.bind(getItem(position));
		return convertView;
	}

	public void clear(View convertView) {
		Object tag = convertView.getTag();
		if (tag instanceof MessageViewHolder) {
			MessageViewHolder holder = (MessageViewHolder)tag;
			holder.clear();
		}
	}

	private static final Map<String, String> EMOTICONS = new HashMap<>();

	static {
		EMOTICONS.put(":emo1:", "http://www.lifeshore.com/smiley/data/media/2/3D_emoticon_113.gif");
		EMOTICONS.put(":emo2:", "http://www.lifeshore.com/smiley/data/media/2/3D_emoticon_124.gif");
		EMOTICONS.put(":emo3:", "http://www.lifeshore.com/smiley/data/media/2/3D_emoticon_223.gif");
		EMOTICONS.put(":emo4:", "http://www.lifeshore.com/smiley/data/media/2/3D_emoticon_226.gif");
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
			GlideImageGetter imageGetter = new GlideImageGetter(context, glide, target, emotionCount <= 8, (int)size);
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
			this.nameView = (TextView)itemView.findViewById(R.id.github_1062_name);
			this.avatarView = (ImageView)itemView.findViewById(R.id.github_1062_avatar);
			this.messageView = (TextView)itemView.findViewById(R.id.github_1062_message);
		}

		@SuppressWarnings("unchecked")
		void bind(ChatMessage message) {
			nameView.setText(message.getUserName());
			Context context = avatarView.getContext();
			glide.load(message.getAvatarUrl())
			     .bitmapTransform(new FitCenter(context), new CropCircleTransformation(context))
			     .listener(new LoggingListener<String, GlideDrawable>())
			     .into(avatarView);
			bindEmoticonMessage(messageView, message.getMessage());
		}

		void clear() {
			Glide.clear(avatarView);
			nameView.setText(null);
			GlideImageGetter.clear(messageView);
		}
	}
}
