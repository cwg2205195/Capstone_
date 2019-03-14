package com.e.capstone;

import android.app.Activity;
import android.content.pm.SigningInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

public class SignUp extends AppCompatActivity {
    public static LoginMain.loginType mIsDoc;
    private EditText mEdit_uname;
    private EditText mEdit_pwd;
    private EditText mEdit_name;
    private EditText mEdit_dep;
    private EditText mEdit_phone;
    private EditText mEdit_title;
    private Button   mSignup;
    private EditText mEditTextPatientName;
    private EditText mEditAddr;


    private void show(String text){
        Toast toast= Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mIsDoc== LoginMain.loginType.DOC){
            setContentView(R.layout.activity_sign_up);
            mEdit_uname=(EditText)findViewById(R.id.edit_uname);
            mEdit_pwd  =(EditText)findViewById(R.id.edit_pwd);
            mEdit_name  =(EditText)findViewById(R.id.edit_name);
            mEdit_dep  =(EditText)findViewById(R.id.edit_dep);
            mEdit_phone  =(EditText)findViewById(R.id.edit_phone);
            mEdit_title  =(EditText)findViewById(R.id.edit_title);
            mSignup     =(Button)findViewById(R.id.btn_signup);

            //编写监听事件，处理注册按钮事件
            mSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uname,pwd,name,dep,phone,title;
                    uname=mEdit_uname.getText().toString();
                    pwd  =mEdit_pwd.getText().toString();
                    name =mEdit_name.getText().toString();
                    dep  =mEdit_dep.getText().toString();
                    phone=mEdit_phone.getText().toString();
                    title=mEdit_title.getText().toString();
                    if(uname.length()==0 || pwd.length()==0 || name.length()==0 || dep.length()==0 || phone.length()==0 || title.length()==0){
                        show("请填写完整个人信息！");
                        return;
                    }
                    Doctor doctor=new Doctor(name,uname,pwd);
                    doctor.setPhone(phone);
                    doctor.setTitle(title);
                    doctor.setDep(dep);
                    doctor.setdID( UUID.randomUUID());
                    SignInUpControler signInUpControler=new SignInUpControler(doctor,getApplicationContext());
                    if(signInUpControler.SignUp(doctor)){
                        show("注册成功！");

                        //Activity.finish();
                    }else{

                        show("注册失败！");
                    }
                    signInUpControler.CloseDB();
                }
            });
        }else{
            setContentView(R.layout.activity_sign_up_nurse);
            mEdit_uname=(EditText)findViewById(R.id.edit_unameN);
            mEdit_pwd  =(EditText)findViewById(R.id.edit_pwdN);
            mEdit_name  =(EditText)findViewById(R.id.edit_nameN);
            mEditAddr  =(EditText)findViewById(R.id.edit_addrN);
            mEdit_phone  =(EditText)findViewById(R.id.edit_phoneN);
            mSignup     =(Button)findViewById(R.id.btn_signupN);
            mEditTextPatientName=(EditText)findViewById(R.id.editPatineName);

            mSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uname,pwd,name,addr,phone;
                    uname=mEdit_uname.getText().toString();
                    pwd  =mEdit_pwd.getText().toString();
                    name =mEdit_name.getText().toString();
                    addr =mEditAddr.getText().toString();
                    phone=mEdit_phone.getText().toString();
                    String PatientName = mEditTextPatientName.getText().toString();
                    if(uname.length()==0 || pwd.length()==0 || name.length()==0 || addr.length()==0 || phone.length()==0 ){
                        show("请填写完整个人信息！");
                        return;
                    }
                    if(PatientName.isEmpty()) { //病人名为空，则表示是护士注册
                        Nurse nurse = new Nurse(name, uname, pwd);
                        nurse.setnID(UUID.randomUUID());
                        nurse.setAddr(addr);
                        nurse.setPhone(phone);
                        SignInUpControler signInUpControler = new SignInUpControler(nurse, getApplicationContext());
                        if (signInUpControler.SignUp(nurse)) {
                            show("注册成功！");
                        } else {
                            show("注册失败！");
                        }
                        signInUpControler.CloseDB();
                    }else{      //否则为家属注册，查询是否存在对应的病人，存在则插入家属表，不存在提示错误
                        Sib sib = new Sib(name,uname,pwd);
                        sib.setSid(UUID.randomUUID());
                        sib.setAddr(addr);
                        sib.setPhone(phone);
                        SignInUpControler signInUpControler = new SignInUpControler(sib, getApplicationContext());
                        String [] args={PatientName};
                        DBCursor dbCursor=new DBCursor(signInUpControler.QueryFromTable(DB.PatientTB.Name,"name=?",args));
                        Patient patient=dbCursor.GetPatient();
                        if(patient.getName().isEmpty()){
                            show("无法找到此病人，请核对病人信息！");
                            return;
                        }
                        sib.setPid(patient.getpID());
                        if(signInUpControler.SignUp(sib)){
                            show("注册成功！");
                        }else{
                            show("注册失败！");
                        }
                    }
                }
            });
        }


    }
}
