package com.example.waitou.rxjava.databinding.persenter;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.example.waitou.rxjava.databinding.info.Employee;

/**
 * Created by waitou on 16/12/29.
 */

public class SimplePresenter {

    private Activity mActivity;
    private Employee mEmployee;


    public SimplePresenter(Activity activity, Employee employee) {
        mActivity = activity;
        mEmployee = employee;
    }


    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mEmployee.setFirsName(s.toString());
        mEmployee.setFired(!mEmployee.isFired.get()); //这里取反赋值
    }

    public void onClick(View v) {
        Toast.makeText(mActivity, "点到了", Toast.LENGTH_SHORT).show();

    }

    public void onClickListenerBinding(Employee employee) {
        Toast.makeText(mActivity, employee.getLastName(),
                Toast.LENGTH_SHORT).show();
    }
}
