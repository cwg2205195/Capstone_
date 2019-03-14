package com.e.capstone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileEditor extends AppCompatActivity {
    private EditText mEditTextOldPasswd;
    private EditText mEditTextNewPasswd;
    private EditText mEditTextNewPasswdCK;
    private TextView mTextViewDepOrAddr;        //如果是医生就是科室，如果是护工则是住址
    private EditText mEditTextDepOrAddr;        //如果是医生就是科室，如果是护工则是住址
    private EditText mEditTextPhone;
    private TextView mTextViewTitle;            //若是护工则隐藏控件
    private EditText mEditTextTitle;            //若是护工则隐藏控件
    private Button mButtonSubmit;           //提交修改
   // private  Human mHuman;
    //private DocControler mDocControler;
   // private NurControler mNurControler;
    private static boolean misDoc;           //用来判断修改的是哪种类型的用户
    public static final String EXTRA_HUMAN="EXTRA_PROFILE_HUMAN";

    /*@Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

    }*/
    public static Intent newIntent(Context context,boolean isDoc){
       //mHuman=human;
       Intent intent=new Intent(context, ProfileEditor.class);
       //intent.putExtra(EXTRA_HUMAN,human);
       misDoc=isDoc;
       return intent;
    }
    private void show(String text){
        Toast toast= Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
   @Override
    public  void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setContentView(R.layout.activity_profile_edit);
        mEditTextOldPasswd=findViewById(R.id.ET_oldPasswd);
        mEditTextNewPasswd=findViewById(R.id.ET_newPasswd);
        mEditTextNewPasswdCK=findViewById(R.id.ET_newPasswdCK);
        mTextViewDepOrAddr=findViewById(R.id.TV_addr_dep);
        mEditTextDepOrAddr=findViewById(R.id.ET_department);
        mEditTextPhone=findViewById(R.id.ET_Phone);
        mTextViewTitle=findViewById(R.id.TV_title);
        mEditTextTitle=findViewById(R.id.ET_title);
        mButtonSubmit=findViewById(R.id.btn_changeProfile);
        //mHuman=(Human)getIntent().getSerializableExtra(EXTRA_HUMAN);

        if(misDoc){ //医生修改个人信息
           // mDocControler=new DocControler(mHuman,getApplicationContext());
            //mHuman = mDocControler.GetMyFullInfo(mHuman.getuName(),mHuman.getuPwd());
            mEditTextDepOrAddr.setText(docMain.mDoctor.getDep());
            mEditTextPhone.setText(docMain.mDoctor.getPhone());
            mEditTextTitle.setText(docMain.mDoctor.getTitle());
           // mTextViewTitle.setText(docMain.mDoctor.getTitle());

            mButtonSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean allPass=true;
                    String oldPasswd=mEditTextOldPasswd.getText().toString();
                    //旧密码不为空，进行验证旧密码
                    if(!oldPasswd.isEmpty()){
                        if(oldPasswd.equals(docMain.mDoctor.getuPwd())){ //  输入的旧密码正确，验证新密码是否相同
                            String newPasswd=mEditTextNewPasswd.getText().toString();
                            String newPasswdCK=mEditTextNewPasswdCK.getText().toString();
                            if(!newPasswd.isEmpty() && !newPasswdCK.isEmpty() && newPasswd.equals(newPasswdCK)){     //密码都不为空,且相同
                                docMain.mDoctor.setuPwd(newPasswd);
                                /*mHuman.setuPwd(newPasswd);
                                mDocControler.UpdateInfo((Doctor)mHuman);*/
                            }else{
                                show("新密码不同，请重新输入！");
                                allPass=false;
                            }
                        }else{
                            show("密码错误，请重新输入！");
                            allPass=false;
                        }
                    }

                    String department=mEditTextDepOrAddr.getText().toString();
                    if(!department.isEmpty()){
                        //((Doctor)mHuman).setDep(department);
                        docMain.mDoctor.setDep(department);
                        //docMain.mDocControler.UpdateInfo(docMain.mDoctor);
                    }
                    String phone=mEditTextPhone.getText().toString();
                    if(!phone.isEmpty()){
                        docMain.mDoctor.setPhone(phone);
                        //docMain.mDocControler.UpdateInfo(docMain.mDoctor);
                    }
                    String title = mEditTextTitle.getText().toString();
                    if(!title.isEmpty()){
                        docMain.mDoctor.setTitle(title);
                        //docMain.mDocControler.UpdateInfo(docMain.mDoctor);
                    }
                    docMain.mDocControler.UpdateInfo(docMain.mDoctor);
                    if(allPass){
                        show("个人资料更新成功！");
                    }
                }
            });
        }else{  //护工修改个人信息
            //mNurControler=new NurControler(mHuman,getApplicationContext());
            //隐藏不必要控件
            mTextViewTitle.setVisibility(View.INVISIBLE);
            mEditTextTitle.setVisibility(View.INVISIBLE);
            mTextViewDepOrAddr.setText("住址");
            mEditTextDepOrAddr.setText(NurMain.mNurse.getAddr());
            mEditTextPhone.setText(NurMain.mNurse.getPhone());
            mButtonSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean allPass=true;
                    String oldPasswd=mEditTextOldPasswd.getText().toString();
                    //旧密码不为空，进行验证旧密码
                    if(!oldPasswd.isEmpty()){
                        if(oldPasswd.equals(NurMain.mNurse.getuPwd())){ //  输入的旧密码正确，验证新密码是否相同
                            String newPasswd=mEditTextNewPasswd.getText().toString();
                            String newPasswdCK=mEditTextNewPasswdCK.getText().toString();
                            if(!newPasswd.isEmpty() && !newPasswdCK.isEmpty() && newPasswd.equals(newPasswdCK)){     //密码都不为空,且相同
                                NurMain.mNurse.setuPwd(newPasswd);
                            }else{
                                show("新密码不同，请重新输入！");
                                allPass=false;
                            }
                        }else{
                            show("密码错误，请重新输入！");
                            allPass=false;
                        }
                    }
                    String addr=mEditTextDepOrAddr.getText().toString();
                    if(!addr.isEmpty()){
                        NurMain.mNurse.setAddr(addr);
                    }
                    String phone=mEditTextPhone.getText().toString();
                    if(!phone.isEmpty()){
                        NurMain.mNurse.setPhone(phone);
                    }
                    NurMain.mNurControler.UpdateInfo(NurMain.mNurse);
                    if(allPass){
                        show("个人资料更新成功！");
                    }
                }
            });
        }


    }
}
