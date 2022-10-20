package com.bumptech.glide.supportapp.issue;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.supportapp.GlideReset;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.issue.IssueInfoAdapter.TestInfoViewHolder;
import com.bumptech.glide.supportapp.utils.Utils;

import androidx.recyclerview.widget.RecyclerView;

public class IssueInfoAdapter extends RecyclerView.Adapter<TestInfoViewHolder> {
	private final List<IssueInfo> issues;
	public IssueInfoAdapter(List<IssueInfo> issues) {
		this.issues = issues;
	}
	@Override public int getItemCount() {
		return issues.size();
	}

	@Override public TestInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.test_list_item, parent, false);
		return new TestInfoViewHolder(view);
	}

	@Override public void onBindViewHolder(TestInfoViewHolder holder, int position) {
		IssueInfo info = issues.get(position);
		holder.bind(info);
	}

	static class TestInfoViewHolder extends RecyclerView.ViewHolder {
		public final ImageView icon;
		public final TextView title;
		public final TextView clazz;
		public final TextView link;
		private IssueInfo boundInfo;

		public TestInfoViewHolder(View itemView) {
			super(itemView);
			icon = (ImageView)itemView.findViewById(android.R.id.icon);
			title = (TextView)itemView.findViewById(android.R.id.title);
			clazz = (TextView)itemView.findViewById(android.R.id.text1);
			link = (TextView)itemView.findViewById(android.R.id.text2);
			link.setLinksClickable(true);
			link.setAutoLinkMask(Linkify.WEB_URLS);
			link.setMovementMethod(LinkMovementMethod.getInstance());

			itemView.setOnClickListener(new OnClickListener() {
				@Override public void onClick(View v) {
					Context context = v.getContext();
					Intent intent = null;
					if (boundInfo.isActivity()) {
						intent = new Intent(context.getApplicationContext(), boundInfo.getEntryClass());
					} else if (boundInfo.isFragment()) {
						intent = new Intent(context.getApplicationContext(), IssueFragmentActivity.class);
						intent.putExtra(IssueFragmentActivity.CONTENT_FRAGMENT, boundInfo.getEntryClass().getName());
					}
					if (intent == null) {
						Toast.makeText(context, "Cannot start " + boundInfo.getEntryClass(), Toast.LENGTH_LONG).show();
						return;
					}
					new GlideReset(context).replace(boundInfo.getModules());
					Log.d("SYS", "Starting " + intent);
					context.startActivity(intent);
				}
			});
		}

		private void bind(IssueInfo info) {
			boundInfo = info;
			Context context = itemView.getContext();
			icon.setImageResource(info.getIcon(context));
			String name = info.getName();
			if (!info.getModules().isEmpty()) {
				name += " (modules: " + info.getModules().size() + ")";
			}
			title.setText(name);
			clazz.setText(info.getEntryClass().getName().replace(Utils.getAppPackage(context).getName(), "app"));
			link.setText(info.getLink());
		}
	}
}
