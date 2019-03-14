package com.e.capstone;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class docMain extends AppCompatActivity {
    public static DocControler mDocControler;
    private UUID mUUID;
    public static Doctor mDoctor;
    //public static final String EXTRA_CTLER="doctor_ctler";
    //public static final String EXTRA_UUID="doctor_ctler";
    //public static final String EXTRA_DOC="doctor";
    public static final String EXTRA_NAME="doctor_name";    //姓名
    public static final String EXTRA_PWD = "doctor_pwd";    //密码
    public static final String EXTRA_UNAME="doctor_uname";  //用户名
    static Patient tmp=null;
    private TextView mPromp;        //欢迎标题
    //private ScrollView mScrollView; //滚动窗口，动态添加病人信息
    private LinearLayout mLinearLayout;//放置病人信息按钮
    private ImageButton mBtnAddp;        //添加病人按钮
    private Button mBtnPmgr;        //病人信息管理按钮
    private Button mBtnSelfEdit;    //个人信息设置按钮
    private List<Patient> mPatientList;//所有病人信息
    private static final int REQUEST_CODE_PATIENT=0;
    private static final int REQUEST_CODE_ADDJOBINFO=1;
    static int i;
   /*public static Intent newIntent(Context context,DocControler docControler){
        Intent intent=new Intent(context,docMain.class);
        intent.putExtra(EXTRA_CTLER,docControler);
        return intent;
    }*/
    /*public static Intent newIntent(Context context, UUID uuid){
        Intent intent=new Intent(context,docMain.class);
        //intent.putExtra(EXTRA_CTLER,docControler);
        intent.putExtra(EXTRA_UUID,uuid);
        return intent;
    }*/
    public static Intent newIntent(Context context, Doctor doctor){
        Intent intent=new Intent(context,docMain.class);
        //intent.putExtra(EXTRA_CTLER,docControler);
       // intent.putExtra(EXTRA_DOC,doctor);
        //不要传递对象，传递用户名、密码和名字
        intent.putExtra(EXTRA_NAME,doctor.getName());
        intent.putExtra(EXTRA_PWD,doctor.getuPwd());
        intent.putExtra(EXTRA_UNAME,doctor.getuName());
        return intent;
    }
    //根据传递的 EXTRA 数据，创建 doctor对象和控制器对象
    private void Init(String name,String pwd,String uname){
       //mDoctor=new Doctor(name,uname,pwd);
       mDocControler=new DocControler(mDoctor,getApplicationContext());
       mDoctor=mDocControler.GetMyFullInfo(uname,pwd);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_main);
        Intent intent = getIntent();
        //mDocControler=(DocControler)intent.getSerializableExtra(EXTRA_CTLER);  //获取控制器对象
       // mUUID=(UUID)intent.getSerializableExtra(EXTRA_UUID);        //获取登录用户的 UUID
       // mDoctor=(Doctor) intent.getSerializableExtra(EXTRA_DOC);        //获取登录用户
       // mDocControler=new DocControler(mDoctor,getApplicationContext());//创建控制器对象
        Init(intent.getStringExtra(EXTRA_NAME),intent.getStringExtra(EXTRA_PWD),intent.getStringExtra(EXTRA_UNAME));

        mPromp=(TextView)findViewById(R.id.text_prom);
        //根据 UUID 获取用户信息，创建对应的控制器

        mPromp.setText("欢迎"+mDoctor.getName()+" 医生登录");

        //测试代码，添加一些病人信息
        /*for(int i=0;i<3;i++){
            Patient patient=new Patient("jack"+i,null,null);
            patient.setpID(UUID.randomUUID());
            mDocControler.AddPatient(patient);
        }*/
        //读取已经存在的病人信息，并显示
        mLinearLayout=(LinearLayout)findViewById(R.id.patientTB);
        mPatientList=mDocControler.GetPatients();
        InitPatientsInfo();

        //添加病人按钮
        mBtnAddp=(ImageButton)findViewById(R.id.btn_addpatient);
        mBtnAddp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建新的 activity，设置病人基本信息，然后返回一个病人对象，并调用 控制器的 添加病人函数
                startActivityForResult(new Intent(getApplicationContext(),addPatient.class),REQUEST_CODE_PATIENT);

            }
        });

        //病人信息管理按钮

        //个人信息设置按钮
        mBtnSelfEdit=(Button)findViewById(R.id.btn_selfedit);
        mBtnSelfEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=ProfileEditor.newIntent(getApplicationContext(),true);
                startActivity(intent);
            }
        });



    }
    @Override
    protected void onActivityResult(int reqCode,int retCode,Intent intent){
            if(retCode != RESULT_OK){
                return ;
            }
            if(intent != null && reqCode == REQUEST_CODE_PATIENT){      //添加病人返回，调用控制器添加病人数据
                Patient patient=(Patient) intent.getSerializableExtra(addPatient.EXTRA_PATIENTINFO);
                patient.setdID(mDoctor.getdID().toString());
                mDocControler.AddPatient(patient);  //新增的病人写入数据库
                mPatientList.add(patient);
                Button button=new Button(this);
                button.setText("病人: "+patient.getName());
                tmp=patient;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(addJob.newIntent(getApplicationContext(),tmp),REQUEST_CODE_ADDJOBINFO);
                    }
                });
                mLinearLayout.addView(button);
            }
            if(intent !=null && reqCode == REQUEST_CODE_ADDJOBINFO){    //添加病人护理信息返回，调用控制器写入护理信息
                jobInfo jInfo=(jobInfo)intent.getSerializableExtra(addJob.EXTRA_JOBINFO);
                mDocControler.AddJobInfo(jInfo);
            }
    }
    //刷新当前数据库中的病人信息
    private void UpdatePatients(){
        mPatientList=mDocControler.GetPatients();
        InitPatientsInfo();
    }
/*
class myButton extends android.support.v7.widget.AppCompatButton{

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        private int offset;      //用来记录病人列表的偏移量
        public myButton(Context context) {
            super(context);
        }
        @Override
        public void onClick(View view){

        }

    }*/
class myListener implements View.OnClickListener {

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    private int index;
    @Override
    public void onClick(View v) {
        startActivityForResult(addJob.newIntent(getApplicationContext(),mPatientList.get(index)),REQUEST_CODE_ADDJOBINFO);
    }
}
    // 根据获取的病人信息列表，添加病人详情按钮
    private void InitPatientsInfo(){
        int count=mPatientList.size();
        for(i=0;i<count;i++){
            Button button=new Button(this);
            button.setText("病人：" + mPatientList.get(i).getName());
            //button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);      //API level 不够
            //tmp=mPatientList.get(i);

            /*button.setOffset(i);    //  记录当前病人的偏移量即可
            int off=button.getOffset();*/

            myListener listener=new myListener();
            listener.setIndex(i);
            button.setOnClickListener(listener);
            /*button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //创建添加病人护理信息界面
                    //static int x=i;
                    startActivityForResult(addJob.newIntent(getApplicationContext(),mPatientList.get(gett)),REQUEST_CODE_ADDJOBINFO);
                    //startActivityForResult(addJob.newIntent(getApplicationContext(),mPatientList.get(i-1)),REQUEST_CODE_ADDJOBINFO);
                }
            });*/
            //button.setWidth();
            mLinearLayout.addView(button);
        }

    }
}
