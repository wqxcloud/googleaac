package com.example.aac;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.bean.Data;

public class Main2Activity extends AppCompatActivity implements LifecycleOwner {

    private ShareViewModel mModel;

    private SeekBar mSeekBar;
    private TextView mCurrStatusTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //添加LifeCycleObserver，可以非UI业务逻辑放到Observer
        getLifecycle().addObserver(new MyLifeCycleObserver());
        initViews();
    }


    private void initViews() {
        mModel = ViewModelProviders.of(this).get(ShareViewModel.class);
        mSeekBar = findViewById(R.id.value_bar);
        mCurrStatusTv = findViewById(R.id.curr_status);
        mModel.getData().observe(this, new Observer<Data>() {
            @Override
            public void onChanged(@Nullable Data data) {
                mSeekBar.setProgress(data.getNum());
            }
        });

        //可以采用lambda表达式
//        mModel.getData().observe(this, data -> {
//            mSeekBar.setProgress(data.getNum());
//        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setData(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.change_value).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = mModel.getData().getValue().getNum();
                if(value > 95){
                    value = 0;
                }
                setData(value + 5);
            }
        });

        mCurrStatusTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示当前Activity State
                mCurrStatusTv.setText("当前Activity状态是：" + getLifecycle().getCurrentState().name());
            }
        });
    }

    /**
     * 更新ViewModel的值，如果不想重新创建新的对象，可以直接取出原来的数据对象，重新set即可
     * @param value
     */
    private void setData(int value) {
        Data data = mModel.getData().getValue();
        data.setValue(value);
        mModel.setData(data);
    }
}
