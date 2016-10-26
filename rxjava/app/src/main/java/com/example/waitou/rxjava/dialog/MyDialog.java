package com.example.waitou.rxjava.dialog;

import android.content.Context;
import android.widget.ImageView;

import com.example.waitou.rxjava.R;

import dawanju.waitou.wtlibrary.BaseDialog;

/**
 * Created by waitou on 16/8/31.
 */
public class MyDialog extends BaseDialog {

    private ImageView mImageView;

    public MyDialog(Context context) {
        super(context);
        setDialogContentView(R.layout.my);
        initView();


    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.my_dialog_img);
    }


    public BaseDialog setImageIcon(int redId){
        mImageView.setImageResource(redId);
        return this;
    }

}
