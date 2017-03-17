package com.example.waitou.rxjava.music;

import com.example.waitou.rxjava.R;
import com.example.waitou.rxjava.base.BaseActivity;

/**
 * Created by waitou on 16/11/29.
 *
 */

public class MusicActivity extends BaseActivity{

    MusicPlayerView mpv;

    @Override
    protected boolean isOverridePendingTransition() {
        return false;
    }

    @Override
    protected int initContentViewID() {
        return R.layout.activity_music;
    }

    @Override
    protected void initData() {
        mpv = (MusicPlayerView) findViewById(R.id.mpv);
        mpv.setCoverURL("https://upload.wikimedia.org/wikipedia/en/b/b3/MichaelsNumberOnes.JPG");
        mpv.setOnClickListener(v -> {
            if (mpv.isRotating()) {
                mpv.stop();
            } else {
                mpv.start();
            }
        });
    }
}
