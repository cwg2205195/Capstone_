package com.e.capstone;

import android.support.v4.app.Fragment;
public class MainActivity extends SingleFragmentActivity{
    @Override
    public Fragment createFragment(){
        Fragment fragment = new LoginMain();
        ((LoginMain) fragment).setAppContext(this.getApplicationContext());
        return fragment;
    }
}
/*
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DBHelper dbHelper=new DBHelper(this.getApplicationContext());
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);   //根据ID查找容器

        if(fragment == null){
            fragment = new LoginMain();
            ((LoginMain) fragment).setAppContext(this.getApplicationContext());
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }

        //Doctor doctor=new Doctor("jack","hunter","123456");
        //Ctler ctler= new Ctler(doctor,this.getApplicationContext());
       // DBHelper dbHelper=new DBHelper(this.getApplicationContext());
    }
}*/
