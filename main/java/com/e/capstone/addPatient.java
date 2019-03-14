package com.e.capstone;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.UUID;

public class addPatient extends AppCompatActivity {
    private EditText mEditTextPname;    //名字
    private EditText mEditTextPage; //年龄
    private EditText mEditTextPsex; //性别
    private EditText mEditTextPaddr; //住址
    private Spinner  mSpinnerPbid;  //病床号的选择
    private EditText mEditTextPdesc;//病人患病详情
    private Button  mButtonAddP;//添加病人按钮

    public static final String EXTRA_PATIENTINFO="EXTRA_PATIENTINFO";


    Patient mPatient;
    //也可以在这里返回静态 patient 对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        mEditTextPname=findViewById(R.id.edit_namePatient);
        mEditTextPage=findViewById(R.id.edit_agePatient);
        mEditTextPage=findViewById(R.id.edit_agePatient);
        mEditTextPaddr=findViewById(R.id.edit_addrPatient);
        mSpinnerPbid=findViewById(R.id.spinner);
        mEditTextPdesc=findViewById(R.id.edit_descPatient);
        mButtonAddP=findViewById(R.id.btn_addPatientConfirm);
        mEditTextPsex=findViewById(R.id.edit_sexPatient);


        //没有控制器怎么写数据库？？？
        //返回 patient 对象，给控制器写？
        mButtonAddP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPatient=new Patient(mEditTextPname.getText().toString(),null,null);    //name
                mPatient.setAge(Integer.parseInt(mEditTextPage.getText().toString()));              //age
                mPatient.setSex(mEditTextPsex.getText().toString());                //sex
                mPatient.setAddr(mEditTextPaddr.getText().toString());              //addr
               // mPatient.setBedID(""+mSpinnerPbid.getId());                         //bed id
                mPatient.setDesc(mEditTextPdesc.getText().toString());              //description of illness
                mPatient.setpID(UUID.randomUUID());                                 //uuid of patient
                Intent intent=new Intent();
                intent.putExtra(EXTRA_PATIENTINFO,mPatient);
                setResult(RESULT_OK,intent);
                addPatient.this.finish();
            }
        });





    }
}
