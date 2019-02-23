package com.bumptech.glide.supportapp.github._1062_html_gif_spans;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.supportapp.GlideListFragment;

import java.util.Arrays;

public class TestFragment extends GlideListFragment {
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final HtmlChatAdapter adapter = new HtmlChatAdapter(Arrays.asList(
                new ChatMessage("User1 name", "text message without any Html formatting"),
                new ChatMessage("User2 name", "text message with <b>some</b> "
                        + "<abbr title=\"HyperText Markup Language\">HTML</abbr> formatting."),
                new ChatMessage("User2 name", "text message with emoticon: :emo1:"),
                new ChatMessage("User2 name", "text message with emoticon: :emo2:"),
                new ChatMessage("User2 name", "text message with emoticon: :emo3:"),
                new ChatMessage("User1 name", "text message with emoticon: :emo4:"),
                new ChatMessage("User1 name", "text message with static emoticon: :emo_static1:"),
                new ChatMessage("User1 name", "text message with static and dynamic emoticon: :emo_static1: :emo1:"),
                new ChatMessage("User2 name", "These will animate: "
                        + ":emo2: :emo2: :emo2: :emo2: :emo2: :emo2: :emo2: :emo2:"),
                new ChatMessage("User2 name", "but these won't (more than 8): "
                        + ":emo3: :emo3: :emo3: :emo3: :emo3: :emo3: :emo3: :emo3: :emo3:"),
                new ChatMessage("User1 name", ":emo1: :emo1: :emo1: :emo1: :emo1: :emo1: :emo1: :emo1:"),
                new ChatMessage("User1 name", ":emo2: :emo2: :emo2: :emo2: :emo2: :emo2: :emo2: :emo2:"),
                new ChatMessage("User1 name", ":emo3: :emo3: :emo3: :emo3: :emo3: :emo3: :emo3: :emo3:"),
                new ChatMessage("User1 name", ":emo4: :emo4: :emo4: :emo4: :emo4: :emo4: :emo4: :emo4:"),
                new ChatMessage("User2 name", ":emo1: :emo1: :emo1: :emo1: :emo1: :emo1: :emo1: :emo1:"),
                new ChatMessage("User2 name", ":emo2: :emo2: :emo2: :emo2: :emo2: :emo2: :emo2: :emo2:"),
                new ChatMessage("User2 name", ":emo3: :emo3: :emo3: :emo3: :emo3: :emo3: :emo3: :emo3:"),
                new ChatMessage("User2 name", ":emo4: :emo4: :emo4: :emo4: :emo4: :emo4: :emo4: :emo4:"),
                new ChatMessage("User3 name", ":emo1: :emo1: :emo1: :emo1: :emo1: :emo1: :emo1: :emo1:"),
                new ChatMessage("User3 name", ":emo2: :emo2: :emo2: :emo2: :emo2: :emo2: :emo2: :emo2:"),
                new ChatMessage("User3 name", ":emo3: :emo3: :emo3: :emo3: :emo3: :emo3: :emo3: :emo3:"),
                new ChatMessage("User3 name", ":emo4: :emo4: :emo4: :emo4: :emo4: :emo4: :emo4: :emo4:"),
                new ChatMessage("User4 name", ":emo1: :emo1: :emo1: :emo1: :emo1: :emo1: :emo1: :emo1:"),
                new ChatMessage("User4 name", ":emo2: :emo2: :emo2: :emo2: :emo2: :emo2: :emo2: :emo2:"),
                new ChatMessage("User4 name", ":emo3: :emo3: :emo3: :emo3: :emo3: :emo3: :emo3: :emo3:"),
                new ChatMessage("User4 name", ":emo4: :emo4: :emo4: :emo4: :emo4: :emo4: :emo4: :emo4:"),
                new ChatMessage("User5 name", ":emo1: :emo1: :emo1: :emo1: :emo1: :emo1: :emo1: :emo1:"),
                new ChatMessage("User5 name", ":emo2: :emo2: :emo2: :emo2: :emo2: :emo2: :emo2: :emo2:"),
                new ChatMessage("User5 name", ":emo3: :emo3: :emo3: :emo3: :emo3: :emo3: :emo3: :emo3:"),
                new ChatMessage("User5 name", ":emo4: :emo4: :emo4: :emo4: :emo4: :emo4: :emo4: :emo4:"),
                new ChatMessage("User6 name", ":emo1: :emo1: :emo1: :emo1: :emo1: :emo1: :emo1: :emo1:"),
                new ChatMessage("User6 name", ":emo2: :emo2: :emo2: :emo2: :emo2: :emo2: :emo2: :emo2:"),
                new ChatMessage("User6 name", ":emo3: :emo3: :emo3: :emo3: :emo3: :emo3: :emo3: :emo3:"),
                new ChatMessage("User6 name", ":emo4: :emo4: :emo4: :emo4: :emo4: :emo4: :emo4: :emo4:")
        ), Glide.with(this));
        listView.setAdapter(adapter);
        listView.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                adapter.clear(view);
            }
        });
    }
}
 
