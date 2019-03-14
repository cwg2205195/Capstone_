package com.e.capstone;

import java.util.Calendar;

public class StringNDate {
    //1.护工添加自己的工作时间
    //2.医生设置护理时间
    //3.护理时间和护工工作时间对比

    //1.需要两个日历对象，一个表示工作开始时间，一个结束时间
    //workTime 格式"hh:mm-hh:mm;"
    public static String getCalString(Calendar begin,Calendar end){
        String workTime=String.valueOf(begin.get(Calendar.HOUR_OF_DAY))+":"+
                begin.get(Calendar.MINUTE)+"-"+
                end.get(Calendar.HOUR_OF_DAY)+":"+end.get(Calendar.MINUTE)+";";
        return workTime;
    }

    //2.同样两个日期对象，一个开始一个结束
    //jobTime 格式"yyyy-mm-dd hh:mm-hh:mm"
    public static String getJobTimeString(Calendar begin,Calendar end){
        String jobTime=""+begin.get(Calendar.YEAR)+"-"+
                begin.get(Calendar.MONTH)+"-"+
                begin.get(Calendar.DAY_OF_MONTH)+" "+
                begin.get(Calendar.HOUR_OF_DAY)+":"+
                begin.get(Calendar.MINUTE)+"-"+
                end.get(Calendar.HOUR_OF_DAY)+":"+end.get(Calendar.MINUTE);
        return jobTime;
    }

    //3.护理时间 和 工作时间 作为字符串，进行对比
    //jobTime 是工作的日期安排， workTime 是护工的工作时间（注意是多个时段列表，用";"分隔）
    public static boolean CanWork(String jobTime,String workTime){
        int jBeginHour,jBeginMinute,jEndHour,jEndMinute;    //工作开始和结束时间
        int wBeginHour,wBeginMinute,wEndHour,wEndMinute;    //护工开工和停工时间
        String range=jobTime.split(" ")[1];
        String start=range.split("-")[0];
        String end  =range.split("-")[1];
        jBeginHour  =Integer.parseInt(start.split(":")[0]);
        jBeginMinute=Integer.parseInt(start.split(":")[1]);
        jEndHour    =Integer.parseInt(end.split(":")[0]);
        jEndMinute    =Integer.parseInt(end.split(":")[1]);

        //护工的时间是多个段 "11:00-12:00;14:30-15:30;"
        String [] rets=workTime.split(";");
        for(int i=0;i<rets.length;i++){
            wBeginHour  =Integer.parseInt( rets[i].split("-")[0].split(":")[0]);
            wBeginMinute  =Integer.parseInt( rets[i].split("-")[0].split(":")[1]);
            wEndHour  =Integer.parseInt( rets[i].split("-")[1].split(":")[0]);
            wEndMinute  =Integer.parseInt( rets[i].split("-")[1].split(":")[1]);

            int jb,je,wb,we;
            jb = jBeginHour*60+jBeginMinute;
            je = jEndHour*60+jEndMinute;
            wb = wBeginHour*60+wBeginMinute;
            we = wEndHour*60+wEndMinute;

            if(wb <= jb && we >= je){
                return true;
            }
        }




        return false;
    }



}
