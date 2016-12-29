package com.example.waitou.rxjava.roundlndicator;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.waitou.rxjava.R;
import com.example.waitou.rxjava.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by waitou on 16/12/6.
 *
 */
public class RoundIndicatorActivity extends BaseActivity {

    private RoundIndicatorView roundIndicatorView;
    private EditText editText;
    private Button button;

    @Override
    protected boolean isOverridePendingTransition() {
        return false;
    }

    @Override
    protected int initContentViewID() {
        return R.layout.activity_round_indicator;
    }

    @Override
    protected void initData() {
        roundIndicatorView = (RoundIndicatorView) findViewById(R.id.my_view);

        roundIndicatorView.setDrawCanArrival(1);
        mSubscription = Observable.timer(100, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    roundIndicatorView.setCurrentNumAnim(1000,null);
                });
        mPendingSubscriptions.add(mSubscription);

        editText = (EditText) findViewById(R.id.edit);
        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a =Integer.valueOf(editText.getText().toString());
                roundIndicatorView.setCurrentNumAnim(a,null);
            }
        });
    }

}
