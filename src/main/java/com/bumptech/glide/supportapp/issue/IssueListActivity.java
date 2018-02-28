package com.bumptech.glide.supportapp.issue;

import java.util.*;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;

import com.bumptech.glide.supportapp.IssueEntryClassScanner;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.utils.Utils;

public class IssueListActivity extends AppCompatActivity {

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_list);
		RecyclerView rv = (RecyclerView)findViewById(android.R.id.list);
		rv.setLayoutManager(new LinearLayoutManager(this));

		try {
			BaseIssueEntryClassScanner scanner = new IssueEntryClassScanner(this, IssueInfo.getSources());
//			scanner.setDebug(true);
			scanner.scan();
			List<IssueInfo> issues = new IssueInfosBuilder(scanner.getClasses())
					.activities(true)
					.fragments(true)
					.favor(Utils.getMetadataValue(this, getComponentName(), "favoredClass"))
					.sortBy(new Comparator<IssueInfo>() {
						@Override public int compare(IssueInfo lhs, IssueInfo rhs) {
							return lhs.getEntryClass().getName().compareTo(rhs.getEntryClass().getName());
						}
					})
					.build();
			rv.setAdapter(new IssueInfoAdapter(issues));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
