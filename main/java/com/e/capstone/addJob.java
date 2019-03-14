package com.e.capstone;


import android.app.job.JobInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class addJob extends AppCompatActivity {
    private static int IDBase=0xbadbeef;
    private EditText mEditTextJobDetail;    //工作内容
    private EditText mEditTextJobDate;      //工作日期
    private TextView mTextViewJobDateProm;  // 还未实现的，先设为隐藏
    private EditText mEditTextJobTime;      //工作时间
    private EditText mEditTextJobHour;      //时
    private EditText mEditTextJobMinute;    //分钟
    private Spinner  mSpinnerTarget;        //任务人
    private Button   mButtonAddJob;         //
    private TextView mTextViewPrompt;       //
    private Patient  mPatient;              //病人信息，添加工作内容时需要 PID
    private ArrayAdapter<String> mAdapter;  //存放护士信息
    private Sib mSib;                        //存放家属信息
    private ArrayList<String> mArrayList;
    private List<Nurse> mNurses;       //存放所有的 护士对象
    //private LinearLayout mLinearLayoutAvaWorkers;       //用来存放任务人的
    private RadioGroup mRadioGroup;     //存放所有护士按钮和家属按钮
    private List<CheckBox> mCheckBoxes;
    private List<RadioButton> mRadioButtons;    //存放所有按钮信息
    private UUID mWID;                  //保存工作者的ID

    private static final String EXTRA_PATIENT="EXTRA_PATIENT";      //activity 参数
    public  static final String EXTRA_JOBINFO="EXTRA_JOBINFO";      //activity 返回值

    public static Intent newIntent(Context context, Patient patient){
        Intent intent=new Intent(context,addJob.class);
        intent.putExtra(EXTRA_PATIENT,patient);
        return intent;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient_job);
        //初始化界面 并 获取 intent 的参数
        mTextViewPrompt=findViewById(R.id.add_job_info);
        mEditTextJobDetail=findViewById(R.id.edit_job_detail);
        mEditTextJobDate=findViewById(R.id.edit_job_date);

       // mEditTextJobDate.setVisibility(View.INVISIBLE); //未实现的功能 先隐藏
        //mTextViewPrompt=findViewById(R.id.tv_prom_date);
        //mTextViewPrompt.setVisibility(View.INVISIBLE);
        //mEditTextJobTime=findViewById(R.id.edit_job_time);
        mEditTextJobHour=findViewById(R.id.job_hour);
        mEditTextJobMinute=findViewById(R.id.job_minute);
        mSpinnerTarget  =findViewById(R.id.spinner_target);
        mButtonAddJob   =findViewById(R.id.btn_add_job);
        mPatient=(Patient)getIntent().getSerializableExtra(EXTRA_PATIENT);
        //mLinearLayoutAvaWorkers=findViewById(R.id.layout_avaWorkers);
        mRadioGroup=findViewById(R.id.rgp_workers);

        //获取护工列表，把护工和家属都添加到可用的任务人中
        mNurses=docMain.mDocControler.GetNurses();
        mCheckBoxes=new ArrayList<>();
        mRadioButtons=new ArrayList<>();
        for(int i=0;i<mNurses.size();++i){
           /* CheckBox checkBox=new CheckBox(this);
            mCheckBoxes.add(checkBox);                      //添加到列表中
            checkBox.setText(mNurses.get(i).getName());     //设置名字
            checkBox.setChecked(false);*/
           // mLinearLayoutAvaWorkers.addView(checkBox);

            RadioButton rb=new RadioButton(this);
            mRadioButtons.add(rb);
            mRadioGroup.addView(rb);
            rb.setText(mNurses.get(i).getName());
            rb.setChecked(false);
            rb.setId(IDBase+i);
        }
        mSib=docMain.mDocControler.GetPatientSib(mPatient.getpID());
        if(mSib.getName()!=null)
        {
            /*CheckBox checkBox=new CheckBox(this);
            mCheckBoxes.add(checkBox);
            checkBox.setText(mSib.getName());
            checkBox.setChecked(false);*/
            //mLinearLayoutAvaWorkers.addView(checkBox);

            RadioButton rb=new RadioButton(this);
            mRadioButtons.add(rb);
            mRadioGroup.addView(rb);
            rb.setId(IDBase-1);         //固定的ID值
            rb.setText(mSib.getName());
            rb.setChecked(false);

        }
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == IDBase-1)       //家属按钮被选中
                {
                    mWID=mSib.getSid();
                    return;
                }
                int offset=checkedId-IDBase;        //获取偏移量
                mWID=mNurses.get(offset).getnID();
            }
        });

        mArrayList=new ArrayList<String>();
        mAdapter=new ArrayAdapter<String>(this,R.layout.activity_new_patient_job,mArrayList);
        mSpinnerTarget.setAdapter(mAdapter);

        mTextViewPrompt.setText("正在添加 "+mPatient.getName()+" 的护理信息");

        mButtonAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobInfo jInfo=new jobInfo();
                /*int hour=0,minute=0;*/
                String h=mEditTextJobHour.getText().toString();
                String m=mEditTextJobMinute.getText().toString();
                /*try{

                }catch (
                )*/
                String workTime=h+" : " + m;
                jInfo.setWorkTime(workTime);

                jInfo.setDesc(mEditTextJobDetail.getText().toString());
                //jInfo.setWorkTime(""+mEditTextJobDate.getText().toString()+mEditTextJobTime.getText().toString());
                jInfo.setpID(mPatient.getpID());        //病人ID
                jInfo.setdID(docMain.mDoctor.getdID());//医生ID
                jInfo.setwID(mWID);             //工作者ID
                //jInfo.setpID();

                Intent intent=new Intent();
                intent.putExtra(EXTRA_JOBINFO,jInfo);
                setResult(RESULT_OK,intent);
                addJob.this.finish();
            }
        });

        //当下拉列表被单击时，进行数据库查询
       /* mSpinnerTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先添加与病人相关联的家属，如果没有，则继续尝试根据工作时间添加合适的护工
                Sib sib=DocControler.GetPatientSib(mPatient.getpID());
                if(sib.getName()!=null){
                    mSib=sib;
                    mAdapter.add(sib.getName());


                }

                //根据工作时间查询护士表，把可用的护士添加到列表中
                //jobTime 格式"yyyy-mm-dd hh:mm-hh:mm" , 下面根据格式需要进行字符串操作
                String jobTime=""+mEditTextJobDate.getText().toString()+mEditTextJobTime.getText().toString();
                List<Nurse> nurses=DocControler.QueryAvaNurse(jobTime);
                //mAdapter=new ArrayAdapter<String>(this,R.id.spinner_target,nurses.toArray());
                if(nurses.size()!=0)
                    for(Nurse nurse:nurses){
                        mAdapter.add(nurse.getName());
                    }

            }
        });*/
       /* mSpinnerTarget.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/
    }

    //点击添加的时候，要生成工作时间
    private void getJobTime(){
        String date=mEditTextJobDate.getText().toString();
        String time=mEditTextJobTime.getText().toString();

    }
}
