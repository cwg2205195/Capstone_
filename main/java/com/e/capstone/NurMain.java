package com.e.capstone;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class NurMain extends AppCompatActivity {

    //public enum loginType{DOC,NUR,SIB};
    public static NurControler mNurControler;
    private SibControler mSibControler;
    private Button mButtonMyJobs;           //
    private Button mButtonMyInfos;          //
    private Button mButtonMyPatients;       //按钮
    private LinearLayout mLinearLayoutPatients;     //用来显示病人信息
    public static Nurse mNurse;   //self
    private Sib mSib;       //self
    public static LoginMain.loginType mLoginType;
    public static final String EXTRA_CTLER="doctor_ctler";
    public static final String EXTRA_HUMAN="human_extra";
    private static final String TAG_MSG="msg_patients";
    private List<jobInfo> infos;
   /* public static Intent newIntent(Context context, NurControler nurControler){
        Intent intent=new Intent(context,NurMain.class);
        intent.putExtra(EXTRA_CTLER,nurControler);
        return intent;
    }*/
    public static Intent newIntent(Context context, Human human, LoginMain.loginType loginType){
        mLoginType=loginType;
        Intent intent=new Intent(context,NurMain.class);
        intent.putExtra(EXTRA_HUMAN,human);
        return intent;
    }
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
            FragmentManager manager=getSupportFragmentManager();
            MsgWindow msgWindow=new MsgWindow();
            if(infos.size()>0){
                msgWindow.setMsg(" < " + infos.get(index).getWorkTime() +" > "+ infos.get(index).getDesc());
                /*TextView textView1=findViewById(R.id.msg_container);
                textView1.setText(infos.get(index).getDesc());*/
            }
            msgWindow.show(manager,TAG_MSG);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nur_main);
        Intent intent=getIntent();
        Human human=(Human)intent.getSerializableExtra(EXTRA_HUMAN);
        mButtonMyInfos=findViewById(R.id.btn_myInfos);
        mButtonMyJobs=findViewById(R.id.btn_myJobs);
        mButtonMyPatients=findViewById(R.id.btn_myPatients);
        mLinearLayoutPatients=findViewById(R.id.layout_patientInfos);
        final TextView textView=findViewById(R.id.nurse_prom);
        textView.setText(human.getName()+" 欢迎您登录系统");

        mSib=null;
        mNurse=null;
        //不知道是否会被剪切掉子类的数据，如果剪切掉则要进行数据库查询，否则直接使用
        /*if(human.getClass().equals(Nurse.class))
            mNurse=(Nurse)human;
        else if(human.getClass().equals(Sib.class))
            mSib=(Sib)human;*/
        if(mLoginType == LoginMain.loginType.SIB)
            mSib=(Sib)human;
        else
            mNurse=(Nurse)human;


        if(mSib!=null){
            mSibControler=new SibControler(mSib,getApplicationContext());
            String [] args={mSib.getSid().toString()};
            DBCursor dbCursor=new DBCursor(mSibControler.QueryFromTable(DB.JinfoTB.Name, DB.JinfoTB.Cols.WID+" =?",args));
            infos=dbCursor.GetJobInfos();
            Button button=new Button(this);
            Patient patient=mSibControler.GetPatientByPID(mSib.getPid());
            if(patient.getName()!=null) {
                button.setText(patient.getName());
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //以对话框的形式展示护理信息
                        FragmentManager manager=getSupportFragmentManager();
                        MsgWindow msgWindow=new MsgWindow();
                        msgWindow.show(manager,TAG_MSG);
                        if(infos.size()>=1){        //有护理信息则进行展示
                            msgWindow.setMsg(infos.get(0).getDesc());
                            /*TextView textView1=findViewById(R.id.msg_container);
                            textView1.setText(infos.get(0).getDesc());*/
                        }


                    }
                });

                mLinearLayoutPatients.addView(button);
            }
        }else if(mNurse!=null){
            mNurControler=new NurControler(mNurse,getApplicationContext());
            mButtonMyInfos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=ProfileEditor.newIntent(getApplicationContext(),false);
                    startActivity(intent);
                }
            });
            String []args={mNurse.getnID().toString()};
            DBCursor dbCursor=new DBCursor(mNurControler.QueryFromTable(DB.JinfoTB.Name,DB.JinfoTB.Cols.WID+" =?",args));
            infos=dbCursor.GetJobInfos();
            if(infos.size()<1){

            }else {
                for(int i=0;i<infos.size();++i){
                    Patient patient=mNurControler.GetPatientByPID(infos.get(i).getpID());   //获取第 i 个病人信息
                    myListener listener=new myListener();
                    listener.setIndex(i);
                    if(!patient.getName().isEmpty()){
                        Button button=new Button(this);
                        button.setText(patient.getName());
                        button.setOnClickListener(listener);
                       /* button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentManager manager=getSupportFragmentManager();
                                MsgWindow msgWindow=new MsgWindow();
                                msgWindow.show(manager,TAG_MSG);
                            }
                        });*/
                        mLinearLayoutPatients.addView(button);
                    }

                }

            }
        }


    }
}
