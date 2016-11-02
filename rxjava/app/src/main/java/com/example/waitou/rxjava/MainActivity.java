package com.example.waitou.rxjava;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waitou.rxjava.base.BaseActivity;
import com.example.waitou.rxjava.demos.demo1.ExpandableListViewActivity;
import com.example.waitou.rxjava.demos.demo2.VerticalViewPagerActivity;
import com.example.waitou.rxjava.demos.demo4.RecycleViewActivity;
import com.example.waitou.rxjava.demos.demo5.AnimationsActivity;
import com.example.waitou.rxjava.demos.demo6.ExpandableActivity;
import com.example.waitou.rxjava.demos.expandablerecycler.ExpandableRecyclerActivity;
import com.example.waitou.rxjava.demos.expandableview_demo8.ExpandableViewActivity;
import com.example.waitou.rxjava.dialog.MyDialog;
import com.example.waitou.rxjava.main.FontHelper;

import dawanju.waitou.wtlibrary.BaseDialog;
import dawanju.waitou.wtlibrary.Effectstype;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private TextView mTv4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FontHelper.injectFont(findViewById(android.R.id.content));
        mTv1 = (TextView) findViewById(R.id.tv1);
        mTv2 = (TextView) findViewById(R.id.tv2);
        mTv3 = (TextView) findViewById(R.id.tv3);
        mTv4 = (TextView) findViewById(R.id.tv4);
        mTv1.setOnClickListener(this);
        mTv2.setOnClickListener(this);
        mTv3.setOnClickListener(this);
        mTv4.setOnClickListener(this);
        // fromJson();
        // toJson();
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
                    .rightButton("demo4", () -> gotoActivity(RecycleViewActivity.class))
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
        }else if(view == mTv4){
            new MyDialog(this)
                    .setImageIcon(R.drawable.img7)
                    .setAnimation(Effectstype.Fadein)
                    .leftButton("demo7", () -> Toast.makeText(MainActivity.this,"暂无",Toast.LENGTH_SHORT).show())
                    .rightButton("demo8", () -> gotoActivity(ExpandableViewActivity.class))
                    .leftIcon(R.drawable.icon)
                    .show();
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
