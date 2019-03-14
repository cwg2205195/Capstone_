package com.e.capstone;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.Serializable;

public class LoginMain extends Fragment {

    public enum loginType{DOC,NUR,SIB};            //登录类别
    private EditText mUname;     //user name field
    private EditText mUpwd;     //password field
    private boolean mIsDoc;     //true if it's a doctor
    private loginType mLoginType;     //登录用户的类别，
    private RadioGroup mRadioGroup; //
    private RadioButton mrbDoc;
    private RadioButton mrbNur;
    private Button mBtnSignin;  //sign in button
    private Button mBtnSignup;  //sign up button
    private Context appContext;
    private Ctler handler;      //处理登录后的用户请求

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

    }

    public void setAppContext(Context appContext) {
        this.appContext = appContext;
    }

    @Override   //创建 Fragment 必须覆盖这个函数 ，返回一个 view对象给 activity
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstance){
        View v = inflater.inflate(R.layout.activity_login_main,container,false);
        mUname = (EditText)v.findViewById(R.id.editUname);
        mUpwd = (EditText)v.findViewById(R.id.editUpwd);
        mRadioGroup = (RadioGroup)v.findViewById(R.id.rgRole);
        mrbDoc = (RadioButton)v.findViewById(R.id.rbDoc);
        mrbNur = (RadioButton)v.findViewById(R.id.rbNurse);
        mBtnSignin = (Button)v.findViewById(R.id.btnSignin);
        mBtnSignup = (Button)v.findViewById(R.id.btnSignupMain);

        //默认 doctor 角色登录
        mrbDoc.setChecked(true);
        mLoginType=loginType.DOC;
        //角色选择按钮判断
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                switch(id){
                    case(R.id.rbDoc):
                        mLoginType=loginType.DOC;
                        break;
                    case(R.id.rbNurse):
                        mLoginType=loginType.NUR;
                        break;
                    case(R.id.rbSib):
                        mLoginType=loginType.SIB;
                        break;
                }
            }
        });

        //登录按钮事件处理,获取用户名，密码，根据角色创建一个 human 对象，传送给 登录控制器，登录
        mBtnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname,upwd;
                uname = mUname.getText().toString();
                upwd  = mUpwd.getText().toString();
                if(uname.length()!=0 && upwd.length()!=0){
                    Human human=null;
                    switch (mLoginType){
                        case DOC:
                            human=new Doctor(null,uname,upwd);
                            break;
                        case NUR:
                            human=new Nurse(null,uname,upwd);
                            break;
                        case SIB:
                            human=new Sib(null,uname,upwd);
                            break;
                    }
                    SignInUpControler signInUpControler=new SignInUpControler(human,appContext);
                    handler= signInUpControler.SignIn();        //验证用户，返回对应的控制器对象
                    signInUpControler.CloseDB();
                    if(handler == null){
                        show("登录失败！请检查用户名或密码！");
                    }else{
                        Intent intent ;
                        show("登录成功！");
                        human.setName(handler.mHuman.getName());
                        if(mLoginType==loginType.DOC){
                            intent=docMain.newIntent(getActivity(),(Doctor) human);
                        }
                        else
                            intent=NurMain.newIntent(getActivity(), handler.mHuman,mLoginType);
                        startActivity(intent);
                        // 创建新的 fragment ， 处理用户登录后的事件
                    }
                }
                else{
                    show("用户名或密码为空，请检查！");
                }
            }
        });

        //注册按钮事件处理
        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SignUp.class);;
                SignUp.mIsDoc=mLoginType;
                startActivity(intent);

            }
        });

        return v;
    }

    private void show(String text){
        Toast toast= Toast.makeText(appContext,text,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    //获取登录角色
    private void GetRole(RadioGroup radioGroup){
        int count = radioGroup.getChildCount();
        for(int i=0;i<count;i++){
            RadioButton rb = (RadioButton)radioGroup.getChildAt(i);
            if(rb.getId() == R.id.rbDoc && rb.isSelected()){
                mIsDoc=true;
            }else if(rb.getId() == R.id.rbNurse && rb.isSelected()){
                mIsDoc=false;
            }
        }
    }

}
