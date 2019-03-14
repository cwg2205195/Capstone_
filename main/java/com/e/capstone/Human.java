package com.e.capstone;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalTime;
import java.util.UUID;

public class Human implements Serializable {
    String name;
    String uName;
    String uPwd;

    public Human(String name, String uName, String uPwd) {
        this.name = name;
        this.uName = uName;
        this.uPwd = uPwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPwd() {
        return uPwd;
    }

    public void setuPwd(String uPwd) {
        this.uPwd = uPwd;
    }
}

class Doctor extends Human implements Serializable{
    String dep;     //科室
    String phone;   //联系方式
    String title;   //职称
    UUID dID;     //doc id
    Doctor(String name,String uName,String uPwd){
        super(name,uName,uPwd);

    }
    public UUID getdID() {
        return dID;
    }

    public void setdID(UUID dID) {
        this.dID = dID;
    }


    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

class Patient extends Human implements Serializable{
    int age;        //
    String sex;     //
    String addr;    //住址
    UUID pID;     //病人号
   // String bedID;   //床号

    public String getdID() {
        return dID;
    }

    public void setdID(String dID) {
        this.dID = dID;
    }

    String dID;        //医生ID

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    String desc;    //患病详情
    Patient(String name,String uName,String uPwd){
        super(name,uName,uPwd);
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public UUID getpID() {
        return pID;
    }

    public void setpID(UUID pID) {
        this.pID = pID;
    }

   /* public String getBedID() {
        return bedID;
    }

    public void setBedID(String bedID) {
        this.bedID = bedID;
    }*/
}

class Nurse extends Human implements Serializable{
    String addr;
    String phone;
    String workTime;  //工作时段
    UUID nID;           //nurse id

    Nurse(String name,String uName,String uPwd){
        super(name,uName,uPwd);
    }
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public UUID getnID() {
        return nID;
    }

    public void setnID(UUID nID) {
        this.nID = nID;
    }
}

class Sib extends Human{
    public UUID getSid() {
        return Sid;
    }

    public void setSid(UUID sid) {
        Sid = sid;
    }

    UUID Sid;   //

    public UUID getPid() {
        return Pid;
    }

    public void setPid(UUID pid) {
        Pid = pid;
    }

    UUID Pid;
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    String addr;
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    String phone;
    Sib(String name,String uName,String uPwd){
        super(name,uName,uPwd);
    }

}

class jobInfo implements Serializable{
    UUID pID;   //病人ID

    public UUID getwID() {
        return wID;
    }

    public void setwID(UUID wID) {
        this.wID = wID;
    }

    UUID wID;   //工作者的ID
   // UUID nID;   //护士ID
    UUID dID;   //医生ID
   // UUID sID;   //家属ID
    String workTime; //工作的时间
    String desc;        //工作内容
    /*public UUID getsID() {
        return sID;
   }*/

    /*public void setsID(UUID sID) {
        this.sID = sID;
    }*/



    public UUID getpID() {
        return pID;
    }

    public void setpID(UUID pID) {
        this.pID = pID;
    }

    /*public UUID getnID() {
        return nID;
    }

    public void setnID(UUID nID) {
        this.nID = nID;
    }*/

    public UUID getdID() {
        return dID;
    }

    public void setdID(UUID dID) {
        this.dID = dID;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}