package dawanju.waitou.wtlibrary;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dawanju.waitou.wtlibrary.effects.BaseEffects;

/**
 * Created by waitou on 16/8/30.
 */
public class BaseDialog extends Dialog {

    protected Context mContext;
    protected boolean isCancelable = true;
    protected int mDuration = -1;

    private Effectstype mEffectstype = null;
    private LinearLayout           mBaseLayout; //根布局
    private LinearLayout           mTitleLayout; //标题根布局
    private RelativeLayout         mButtomLayout; //底部按钮根布局
    private FrameLayout            mCententLayout; //内容根布局
    private DrawableCenterTextView mRightButton; //右边按钮
    private DrawableCenterTextView mLeftButton; //左边按钮
    private TextView               mTitleDes; //标题
    private ImageView              mTitleIcon; //标题图标
    private TextView               mCententDes; //文本内容

    public BaseDialog(Context context) {
        super(context, R.style.Dialog_Tip);
        this.mContext = context;
        setContentView(R.layout.base_dialog);
        initViews();


        this.setCancelable(isCancelable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT ;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
    }

    /**
     * 初始化views
     */
    private void initViews() {
        mBaseLayout = (LinearLayout) findViewById(R.id.base_Layout);
        mTitleLayout = (LinearLayout) findViewById(R.id.base_dialog_title_layout);
        mButtomLayout = (RelativeLayout) findViewById(R.id.base_dialog_buttom_layout);
        mCententLayout = (FrameLayout) findViewById(R.id.base_dialog_centent_framelayout);
        mRightButton = (DrawableCenterTextView) findViewById(R.id.base_dialog_button_right_click);
        mLeftButton = (DrawableCenterTextView) findViewById(R.id.base_dialog_buttom_left_click);
        mCententDes = (TextView) findViewById(R.id.base_dialog_centent_des);
        mTitleDes = (TextView) findViewById(R.id.base_dialog_title_des);
        mTitleIcon = (ImageView) findViewById(R.id.base_dialog_title_icon);

        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                if(mEffectstype != null){
                    start(mEffectstype);
                }
            }
        });
    }

    /**
     * 填充新布局到内容区域
     */
    protected void setDialogContentView(int resId) {
        View v = LayoutInflater.from(mContext).inflate(resId, null);
        if (mCententLayout.getChildCount() > 0) {
            mCententLayout.removeAllViews();
        }
        mCententLayout.addView(v);
    }

    /**
     * 设置设置内容区域根布局的背景
     */
    public BaseDialog baseBackground(String colorString) {
        if (!TextUtils.isEmpty(colorString)) {
            mBaseLayout.setBackgroundColor(Color.parseColor(colorString));
        }
        return this;
    }

    /**
     * 设置内容区域根布局的背景
     */
    public BaseDialog baseBackground(int resId) {
        mBaseLayout.setBackgroundResource(resId);
        return this;
    }

    /**
     * 设置标题图标
     */
    public BaseDialog titleIcon(int resId) {
        if (toggleView(mTitleLayout, resId)) {
            mTitleIcon.setVisibility(View.VISIBLE);
            mTitleIcon.setImageResource(resId);
        }
        return this;
    }

    /**
     * 设置标题内容
     */
    public BaseDialog titleDes(CharSequence titleDes) {
        if (toggleView(mTitleLayout, titleDes)) {
            mTitleDes.setText(titleDes);
        }
        return this;
    }

    /**
     * 设置标题颜色
     */
    public BaseDialog titleColor(String colorString) {
        if (!TextUtils.isEmpty(colorString)) {
            mTitleDes.setTextColor(Color.parseColor(colorString));
        }
        return this;
    }

    /**
     * 设置标题颜色
     */
    public BaseDialog titleColor(int resId) {
        mTitleDes.setTextColor(resId);
        return this;
    }

    /**
     * 设置title根布局的背景
     */
    public BaseDialog titleLayoutBackground(String colorString) {
        if (!TextUtils.isEmpty(colorString)) {
            mTitleLayout.setBackgroundColor(Color.parseColor(colorString));
        }
        return this;
    }

    /**
     * 设置title根布局的背景
     */
    public BaseDialog titleLayoutBackground(int resId) {
        mTitleLayout.setBackgroundResource(resId);
        return this;
    }

    /**
     * 设置描述内容
     */
    public BaseDialog contentDes(CharSequence msg) {
        if (toggleView(mCententLayout, msg)) {
            mCententDes.setText(msg);
        }
        return this;
    }

    /**
     * 设置文本颜色
     */
    public BaseDialog contentColor(String colorString) {
        if (!TextUtils.isEmpty(colorString)) {
            mCententDes.setTextColor(Color.parseColor(colorString));
        }
        return this;
    }

    /**
     * 设置文本颜色
     */
    public BaseDialog contentColor(int resId) {
        mCententDes.setTextColor(resId);
        return this;
    }

    /**
     * 设置设置内容区域根布局的背景
     */
    public BaseDialog contentBackground(String colorString) {
        if (!TextUtils.isEmpty(colorString)) {
            mCententLayout.setBackgroundColor(Color.parseColor(colorString));
        }
        return this;
    }

    /**
     * 设置内容区域根布局的背景
     */
    public BaseDialog contentBackground(int resId) {
        mCententLayout.setBackgroundResource(resId);
        return this;
    }

    /**
     * 设置底部左边按钮的文字,事件
     */
    public BaseDialog leftButton(CharSequence des, final OnDialogClickListener listener) {
        if (toggleView(mButtomLayout, des)) {
            mLeftButton.setVisibility(View.VISIBLE);
            mLeftButton.setText(des);
        }
        if(listener != null){
            mLeftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    listener.dialogClick();
                }
            });
        }
        return this;
    }

    /**
     * 设置底部右边按钮文字,事件
     */
    public BaseDialog rightButton(CharSequence des, final OnDialogClickListener listener) {
        if (toggleView(mButtomLayout, des)) {
            mRightButton.setVisibility(View.VISIBLE);
            mRightButton.setText(des);
        }
        if(listener != null){
            mRightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    listener.dialogClick();
                }
            });
        }
        return this;
    }

    /**
     * 设置底部按钮的左边图标
     */
    public BaseDialog leftIcon(int resId) {
        mLeftButton.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
        return this;
    }

    /**
     * 设置底部按钮的右边图标
     */
    public BaseDialog rightIcon(int resId) {
        mRightButton.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
        return this;
    }

    /**
     * 设置动画
     */
    public BaseDialog setAnimation(Effectstype effectstype){
        this.mEffectstype = effectstype;
        return this;
    }

    /**
     * 设置动画的时间
     */
    public BaseDialog duration(int duration) {
        this.mDuration = duration;
        return this;
    }

    @Override
    public void show() {
        if(mRightButton.getCompoundDrawables()[0] == null){
            mRightButton.setGravity(Gravity.CENTER);
        }
        if(mLeftButton.getCompoundDrawables()[0] == null){
            mLeftButton.setGravity(Gravity.CENTER);
        }
        super.show();
    }

    /**
     * 是否点击对话框以外的地方消失 默认可以
     */
    public BaseDialog isCancelableOnTouchOutside(boolean cancelable) {
        this.isCancelable = cancelable;
        this.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    public BaseDialog isCancelable(boolean cancelable) {
        this.isCancelable = cancelable;
        this.setCancelable(cancelable);
        return this;
    }

    private void start(Effectstype type) {
        BaseEffects animator = type.getEffectsClazz();
        if (mDuration != -1) {
            animator.setDuration(Math.abs(mDuration));
        }
        animator.start(mBaseLayout);
    }


    private boolean toggleView(View view, Object obj) {
        if (obj == null) {
            view.setVisibility(View.GONE);
            return false;
        } else {
            view.setVisibility(View.VISIBLE);
            return true;
        }
    }


    public interface OnDialogClickListener{
        void dialogClick();
    }
}
