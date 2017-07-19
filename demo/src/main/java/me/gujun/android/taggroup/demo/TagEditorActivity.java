package me.gujun.android.taggroup.demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import me.gujun.android.taggroup.TagGroup;
import me.gujun.android.taggroup.demo.db.TagsManager;


public class TagEditorActivity extends ActionBarActivity {

    private static final String TAG = TagEditorActivity.class.getSimpleName();

    private TagGroup mTagGroup;
    private TagGroup mTagGroupChoice;

    private TagsManager mTagsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_editor);

        mTagsManager = TagsManager.getInstance(getApplicationContext());
        String[] tags = mTagsManager.getTags();

        mTagGroup = (TagGroup) findViewById(R.id.tag_group);
        mTagGroup.setOnTagChangeListener(mOnTagChangeListener);
        mTagGroup.setTags(tags);

        mTagGroupChoice = (TagGroup) findViewById(R.id.tag_group_choice);
        String[] tagsChoice = {"备用1", "备用2", "备用3", "备用4", "备用5"};
        mTagGroupChoice.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                mTagGroup.submitTag(tag);
            }
        });
        mTagGroupChoice.setTags(tagsChoice);
    }

    private TagGroup.OnTagChangeListener mOnTagChangeListener = new TagGroup.OnTagChangeListener() {
        @Override
        public void onInputTextChanged(CharSequence s) {
            Log.e(TAG, "当前输入内容：" + s.toString() + " -> 开始检索相关诊断");
        }

        @Override
        public void onAppend(TagGroup tagGroup, String tag) {

        }

        @Override
        public void onDelete(TagGroup tagGroup, String tag) {

        }

        @Override
        public void onTagsCountReachLimit(TagGroup tagGroup, int tagsCountLimit) {
            Toast.makeText(TagEditorActivity.this, "最多可以选择" + tagsCountLimit + "个诊断", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tag_editor_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mTagsManager.updateTags(mTagGroup.getTags());
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_submit) {
            mTagGroup.submitTag("");
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        mTagsManager.updateTags(mTagGroup.getTags());
        super.onBackPressed();
    }
}