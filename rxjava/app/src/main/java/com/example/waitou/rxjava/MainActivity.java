package com.example.waitou.rxjava;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.example.waitou.rxjava.base.BaseActivity;
import com.example.waitou.rxjava.databinding.DataActivity;
import com.example.waitou.rxjava.demos.demo1.ExpandableListViewActivity;
import com.example.waitou.rxjava.demos.demo2.VerticalViewPagerActivity;
import com.example.waitou.rxjava.demos.demo5.AnimationsActivity;
import com.example.waitou.rxjava.demos.demo6.ExpandableActivity;
import com.example.waitou.rxjava.demos.expandablerecycler_demo7.ExpandableRecyclerActivity;
import com.example.waitou.rxjava.demos.expandableview_demo8.ExpandableViewActivity;
import com.example.waitou.rxjava.demos.transition.TransitionActivity;
import com.example.waitou.rxjava.design.DesignActivity;
import com.example.waitou.rxjava.dialog.MyDialog;
import com.example.waitou.rxjava.gesture_scroller.gesture.GestureDetectorActivity;
import com.example.waitou.rxjava.gesture_scroller.scroller.ScrollerActivity;
import com.example.waitou.rxjava.main.FontHelper;
import com.example.waitou.rxjava.roundlndicator.RoundIndicatorActivity;

import java.io.File;

import dawanju.waitou.wtlibrary.BaseDialog;
import dawanju.waitou.wtlibrary.Effectstype;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private TextView mTv4;
    private TextView mTv5;
    private TextView mTv6;
    private TextView mTv7;
    private TextView mTv8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FontHelper.injectFont(findViewById(android.R.id.content));
        mTv1 = (TextView) findViewById(R.id.tv1);
        mTv2 = (TextView) findViewById(R.id.tv2);
        mTv3 = (TextView) findViewById(R.id.tv3);
        mTv4 = (TextView) findViewById(R.id.tv4);
        mTv5 = (TextView) findViewById(R.id.tv5);
        mTv6 = (TextView) findViewById(R.id.tv6);
        mTv7 = (TextView) findViewById(R.id.tv7);
        mTv8 = (TextView) findViewById(R.id.tv8);
        mTv1.setOnClickListener(this);
        mTv2.setOnClickListener(this);
        mTv3.setOnClickListener(this);
        mTv4.setOnClickListener(this);
        mTv5.setOnClickListener(this);
        mTv6.setOnClickListener(this);
        mTv7.setOnClickListener(this);
        mTv8.setOnClickListener(this);

        LoadingView loadingView = (LoadingView) findViewById(R.id.loading);
        loadingView.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        // fromJson();
        // toJson();

    }

    private void initNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        //首先获取到NotifycationManager来对通知进行管理
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //通知栏的创建 support-v4库中提供了一个notificationCompat类，使用这个类的构造器来创建Notification对象，就可以
        //保证我们所有程序在所有android系统版本上都能正常工作。
        //构建一个通知
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("this is content title") //指定通知栏的标题
                .setContentText("this is content text") //指定通知栏的正文内容
                .setWhen(System.currentTimeMillis()) //指定通知被创建的时间
                .setSmallIcon(R.drawable.img1) //设置通知的小图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.img2))//设置通知的大图标
                .setSound(Uri.fromFile(new File("路径"))) //设置通知发出的时候 播放一段音频
                .setVibrate(new long[]{0, 1000, 1000, 1000}) //设置手机的震动 和静止的时长 震动1秒，静止1秒，在震动1秒
                .setLights(Color.GREEN, 1000, 1000)//设置LED等闪烁 颜色 LED亮起1秒 暗去1秒 答到一闪一闪的效果
                .setDefaults(NotificationCompat.DEFAULT_ALL) //默认的通知效果
                .setStyle(new NotificationCompat.BigTextStyle().bigText("显示所有内容文字")) //显示完整的通知文字
                .setStyle(new NotificationCompat.BigPictureStyle().bigLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.img2))) //显示一张图片
                .setPriority(NotificationCompat.PRIORITY_MAX) //设置通知的重要程度
                .setContentIntent(pendingIntent) //通知的跳转
                .setAutoCancel(true) //表示点击这个通知的时候，通知会自动消失
                .build();
        //通过NotificationManager 的notify()方法显示通知
        //nofify接收两个参数 第一个参数是id 要保证每个通知所指定的id都是不同的,第二个参数就是Notifycation对象了。
        manager.notify(1, notification);


        manager.cancel(1);
    }


    @Override
    protected boolean isOverridePendingTransition() {
        return false;
    }

    @Override
    protected int initContentViewID() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        if (view == mTv1) {
            new BaseDialog(this)
                    .setAnimation(Effectstype.Fliph)
                    .titleDes("我是标题栏")
                    .titleIcon(R.drawable.icon)
                    .contentDes("我是内容栏")
                    .leftButton("demo1", () -> gotoActivity(ExpandableListViewActivity.class))
                    .rightButton("demo2", () -> gotoActivity(VerticalViewPagerActivity.class))
                    .leftIcon(R.drawable.icon)
                    .show();
        } else if (view == mTv2) {
            new MyDialog(this)
                    .setAnimation(Effectstype.Sidefill)
                    .leftButton("demo3", () -> gotoActivity(ExpandableRecyclerActivity.class))
                    .rightButton("demo4", null)
                    .leftIcon(R.drawable.icon)
                    .show();
        } else if (view == mTv3) {
            new MyDialog(this)
                    .setImageIcon(R.drawable.img6)
                    .setAnimation(Effectstype.Flipv)
                    .leftButton("demo5", () -> gotoActivity(AnimationsActivity.class))
                    .rightButton("demo6", () -> gotoActivity(ExpandableActivity.class))
                    .leftIcon(R.drawable.icon)
                    .show();
        } else if (view == mTv4) {
            new MyDialog(this)
                    .setImageIcon(R.drawable.img7)
                    .setAnimation(Effectstype.Fadein)
                    .leftButton("RoundIndicator", () -> gotoActivity(RoundIndicatorActivity.class))
                    .rightButton("Expandable", () -> gotoActivity(ExpandableViewActivity.class))
                    .leftIcon(R.drawable.icon)
                    .show();
        } else if (view == mTv5) {
            new MyDialog(this)
                    .setImageIcon(R.drawable.img2)
                    .setAnimation(Effectstype.Slit)
                    .leftButton("scroller", () -> gotoActivity(ScrollerActivity.class))
                    .rightButton("Gesture", () -> gotoActivity(GestureDetectorActivity.class))
                    .leftIcon(R.drawable.icon)
                    .show();
        } else if (view == mTv6) {
            new MyDialog(this)
                    .setImageIcon(R.drawable.img5)
                    .setAnimation(Effectstype.Slit)
                    .leftButton("Transition", () -> gotoActivity(TransitionActivity.class))
                    .rightButton("Gesture", () -> gotoActivity(GestureDetectorActivity.class))
                    .leftIcon(R.drawable.icon)
                    .show();
        } else if (view == mTv7) {
            gotoActivity(DesignActivity.class);
        } else if(view == mTv8){
            gotoActivity(DataActivity.class);
        }
    }

    public void fromJson() {
//        Gson gson = new Gson();
//        int i = gson.fromJson("100", int.class);
//        double d = gson.fromJson("\"9.9\"", double.class);
//        boolean b = gson.fromJson("true", boolean.class);
//        String s = gson.fromJson("string",String.class);
//        Log.i("gson", "打印的结果是 i=" + i + " d=" + d + " b=" + b+" s=" + s);
//
//        String fromJson = "{\"name\":\"刘玄德\",\"age\":33}";
//        UserBean bean = gson.fromJson(fromJson, UserBean.class);
//        Log.i("gson", "打印的结果是 " + bean.toString());
//
//        UserBean bean2 = new UserBean();
//        String fromJson2 = "{\"name\":\"刘玄德2\",\"age\":33}";
//        JsonReader reader = new JsonReader(new StringReader(fromJson2));
//        try {
//            reader.beginObject();
//            while (reader.hasNext()){
//                String s1 = reader.nextName();
//                switch (s1) {
//                    case "name":
//                        bean2.name = reader.nextString();
//                        break;
//                    case "age":
//                        bean2.age = reader.nextInt();
//                        break;
//                }
//            }
//            reader.endObject();
//            Log.i("gson", "打印的结果是 bean2= " + bean2.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        Gson gson = new Gson();
//        String fromJson3 = "{\"name\":\"刘玄德3\",\"age\":33,\"emailAddress\":\"utmoi@163.com\",\"email\":\"utmoi@1634.com\"}";
//        UserBean bean = gson.fromJson(fromJson3, UserBean.class);
//        Log.i("gson" , bean.toString());

//        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create();
//        UserBean userBean = new UserBean("赵子龙",100,"utmoi@163.com");
//        String toJson = gson.toJson(userBean);
//        Log.i("gson", "打印的结果是 toJson= " + toJson);


//        Gson gson = new GsonBuilder().setFieldNamingStrategy(new FieldNamingStrategy() {
//            @Override
//            public String translateName(Field f) {
//                if("emailAddress".equals(f.getName())){
//                    return "email";
//                }
//                return f.getName();
//            }
//        }).create();
//        UserBean userBean = new UserBean("赵子龙",100,"utmoi@163.com");
//        String toJson = gson.toJson(userBean);
//        Log.i("gson", "打印的结果是 toJson= " + toJson);


//        Gson gson = new Gson();
//        String jsonArray = "[\"Swift\",\"Java\",\"PHP\"]";
//        List<String> strList = gson.fromJson(jsonArray, new TypeToken<List<String>>() {}.getType());
//        Log.i("gson", "打印的结果是 strList.get(0)= " + strList.get(0) + " strList.get(1)=" + strList.get(1) + " strList.get(2)=" + strList.get(2)) ;

    }

    public void toJson() {
//        Gson gson = new Gson();
//        String num = gson.toJson(100);
//        String bool = gson.toJson(false);
//        String str = gson.toJson("String");
//        Log.i("gson", "打印的结果是 num=" + num + " bool=" + bool + " str=" + str);
//
//        UserBean bean = new UserBean("赵子龙",100);
//        String toJson = gson.toJson(bean);
//        Log.i("gson", "打印的结果是 toJson=" + toJson);


//        JsonWriter jsonWriter = new JsonWriter(new LogWriter("gson"));
//        try {
//            jsonWriter.beginObject().name("name").value("赵子龙")
//                                    .name("age").value(100).endObject();
//            jsonWriter.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
