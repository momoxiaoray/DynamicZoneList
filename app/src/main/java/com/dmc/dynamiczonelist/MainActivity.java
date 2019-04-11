package com.dmc.dynamiczonelist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MAdapter mMAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Gson gson = new Gson();
        List<CommentModel> commentModels = gson.fromJson(DataUtil.getTestData(), new TypeToken<List<CommentModel>>() {
        }.getType());
        mMAdapter = new MAdapter(this, commentModels);
        mRecyclerView.setAdapter(mMAdapter);

        mMAdapter.setOnActionListener(new MAdapter.OnActionListener() {
            @Override
            public void onComment(CommentModel item) {

            }

            @Override
            public void onHit(CommentModel item) {

            }

            @Override
            public void onDetailClick(CommentModel item) {

            }

            @Override
            public void onAllReplyOpen(CommentModel item) {

            }

            @Override
            public void onUserIconClick(String userName) {

            }

            @Override
            public void onLongClick(CommentModel item) {

            }
        });
    }
}
