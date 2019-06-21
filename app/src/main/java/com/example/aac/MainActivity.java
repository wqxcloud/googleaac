package com.example.aac;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bean.NewsDataVo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MainActivity mActivity;
    private NewsViewModel newsViewModel;
    private DataAdapter dataAdapter;
    private List<NewsDataVo> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        addObserver();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.do_btn_tv:
                newsViewModel.httpGetData();
                break;
            default:
                break;
        }
    }

    public void initView() {
        mActivity = this;
        mData = new ArrayList<>();
        dataAdapter = new DataAdapter(mData);
        findViewById(R.id.do_btn_tv).setOnClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.data_list_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(dataAdapter);
    }

    private void addObserver() {
        AccLifecycleObserver observer = new AccLifecycleObserver(mActivity);
        getLifecycle().addObserver(observer);
        ViewModelProvider.AndroidViewModelFactory instance =
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication());

        //newsViewModel = instance.create(NewsViewModel.class);

        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);


        newsViewModel.getSwitchDataMap().observe(mActivity, new Observer<NewsDataVo>() {
            @Override
            public void onChanged(@Nullable NewsDataVo dataVo) {
                assert dataVo != null;
                mData.add(dataVo);
                dataAdapter.notifyDataSetChanged();
            }
        });
    }


    class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewData> {

        private List<NewsDataVo> mData;

        DataAdapter(List<NewsDataVo> mData) {
            this.mData = mData;
        }

        @NonNull
        @Override
        public ViewData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewData(LayoutInflater.from(mActivity).inflate(R.layout.news_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewData viewData, int i) {
            NewsDataVo newsDataVo = mData.get(i);
            viewData.numTv.setText(newsDataVo.getReadNum() + "人阅读");
            viewData.titleTv.setText(newsDataVo.getNewsTitle());
            viewData.contentTv.setText(newsDataVo.getNewsContent());
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class ViewData extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private TextView titleTv;
            private TextView contentTv;
            private TextView numTv;

            public ViewData(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.image_iv);
                titleTv = itemView.findViewById(R.id.title_tv);
                contentTv = itemView.findViewById(R.id.content_tv);
                numTv = itemView.findViewById(R.id.read_num_tv);
            }
        }
    }
}
