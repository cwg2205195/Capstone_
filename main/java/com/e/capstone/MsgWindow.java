package com.e.capstone;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class MsgWindow extends DialogFragment {
    private TextView mTextView;
    private  static String msg;

    public  String getMsg() {
        return msg;
    }
    //设置要展示的内容， 添加任务时间最好让调用者实现，这样类的作用就很清楚
    public  void setMsg(String msg) {
        this.msg=msg;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        View v= LayoutInflater.from(getActivity())
                .inflate(R.layout.msg_view,null);
        mTextView=v.findViewById(R.id.msg_container);
        mTextView.setTextSize(18);
        mTextView.setText(msg);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.details)
                .setPositiveButton(android.R.string.ok,null)
                .create();
    }
}
