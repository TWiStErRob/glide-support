package com.bumptech.glide.supportapp.issue;

import java.util.*;
import java.util.regex.*;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.*;
import android.util.Log;

import com.bumptech.glide.supportapp.*;

public class IssueListActivity extends GlideSupportActivity {
	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_list);
		RecyclerView rv = (RecyclerView)findViewById(android.R.id.list);
		rv.setLayoutManager(new LinearLayoutManager(this));

		try {
			List<String> classNames = Arrays.asList("TestActivity", "TestFragment");
			IssueEntryClassScanner scanner = new IssueEntryClassScanner(this, IssueInfo.getSources(), classNames);
			scanner.scan();
			List<IssueInfo> issues = buildTests(scanner.getClasses());
			Collections.sort(issues, new Comparator<IssueInfo>() {
				@Override public int compare(IssueInfo lhs, IssueInfo rhs) {
					return lhs.getEntryClass().getName().compareTo(rhs.getEntryClass().getName());
				}
			});
			rv.setAdapter(new IssueInfoAdapter(issues));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<IssueInfo> buildTests(List<Class<?>> classes) {
		List<IssueInfo> issues = new ArrayList<>(classes.size());
		Pattern splitter = Pattern.compile("^(.*)\\.(.*)\\.(.*)$");
		for (Class<?> clazz : classes) {
			Matcher matcher = splitter.matcher(clazz.getPackage().getName());
			if (!matcher.find()) {
				Log.w("Scanner", "Invalid test " + clazz.getName());
				continue;
			}
			//String basePackage = matcher.group(1);
			String source = matcher.group(2);
			String name = matcher.group(3);
			issues.add(new IssueInfo(clazz, source, name));
		}
		return issues;
	}
}

