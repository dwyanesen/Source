package com.example.michael.anew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int i;
    private void saveObject(String name){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = this.openFileOutput(name, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(newobject);
        } catch (Exception e) {
            e.printStackTrace();
            //这里是保存文件产生异常
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    //fos流关闭异常
                    e.printStackTrace();
                }
            }
            if (oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    //oos流关闭异常
                    e.printStackTrace();
                }
            }
        }
    }
    //以上为保存对象的代码

    private Object getObject(String name){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = this.openFileInput(name);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            //这里是读取文件产生异常
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    //fis流关闭异常
                    e.printStackTrace();
                }
            }
            if (ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    //ois流关闭异常
                    e.printStackTrace();
                }
            }
        }
        //读取产生异常，返回null
        return null;
    }
    //以上为取得对象的代码

    private ArrayList<Object> newobject=new ArrayList<Object>();

    private ArrayList<Object> xin = new ArrayList<Object>();



    private void initTime()
    {
        Time time1 = new Time("周六","1","我去超市来着...","七","2016");
        newobject.add(time1);
        Time time2 = new Time("周天","2","今天第一次和智恩吃意大利面。","七","2016");
        newobject.add(time2);
        Time time3 = new Time("周一","3","路两边的树吸走了汽车尾气,7:45pm准备下班","七","2016");
        newobject.add(time3);
        Time time4 = new Time("周二","4","现在每天都能吃到猪排饭了，好开心啊","七","2016");
        newobject.add(time4);
        Empty empty1=new Empty(R.drawable.empty,"周三","5","七","2016");
        newobject.add(empty1);
        Empty empty2=new Empty(R.drawable.empty,"周四","6","七","2016");
        newobject.add(empty2);
        Time time5 = new Time("周五","7","你，黑咖啡，芝士年糕，羽毛球，成功","七","2016");
        newobject.add(time5);
        Time time6 = new Time("周六","8","DGA 7 最爱Beatles的Hey Jude","七","2016");
        newobject.add(time6);
        Time time7 = new Time("周日","9","7:25AM起床，中午吃了披萨，那家披萨店的积分好像又变多了","七","2016");
        newobject.add(time7);
        Time time8 = new Time("周日","9","7:25AM起床，中午吃了披萨，那家披萨店的积分好像又变多了","七","2016");
        newobject.add(time8);
        Time time9 = new Time("周日","9","7:25AM起床，中午吃了披萨，那家披萨店的积分好像又变多了","七","2016");
        newobject.add(time9);
        Time time10 = new Time("周日","9","7:25AM起床，中午吃了披萨，那家披萨店的积分好像又变多了","七","2016");
        newobject.add(time10);
    }

    private void initTimeN(int y,int m){

        newobject = new ArrayList<Object>();
        xin = new ArrayList<Object>();

        String inity = Integer.toString(y);
        String initm = Integer.toString(m+1);
        //用当前月份作为首次使用时的初始月份

        Calendar time=Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR,Integer.parseInt(inity));
        time.set(Calendar.MONTH,Integer.parseInt(initm)-1);//注意,Calendar对象默认一月为0
        int initnum=time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数


        for(i=0;i<initnum;i++) {
            int num;
            num=i;

            Empty e;
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(Calendar.YEAR, Integer.parseInt(inity));
            cal.set(Calendar.MONTH, Integer.parseInt(initm)-1);
            cal.set(Calendar.DAY_OF_MONTH, num);
            int weekno = (int) cal.get(Calendar.DAY_OF_WEEK);
            if(6==weekno||7==weekno){
                e = new Empty(R.drawable.empty2, ""+weekno, String.valueOf(num+1), initm, inity);
            } else{
                e = new Empty(R.drawable.empty, ""+weekno, String.valueOf(num+1), initm, inity);
            }
            //Empty e = new Empty(R.drawable.empty, ""+weekno, String.valueOf(num+1), initm, inity);
            newobject.add(e);
            xin.add(e);
        }
        saveObject("object.dat");
    }
    private void initTimeClick(String y,String m){

        xin=new ArrayList<Object>();

        String initm ;
        if(m.equals("一月"))
            initm = "1";
        else if(m.equals("二月"))
            initm = "2";
        else if(m.equals("三月"))
            initm = "3";
        else if(m.equals("四月"))
            initm = "4";
        else if(m.equals("五月"))
            initm = "5";
        else if(m.equals("六月"))
            initm = "6";
        else if(m.equals("七月"))
            initm = "7";
        else if(m.equals("八月"))
            initm = "8";
        else if(m.equals("九月"))
            initm = "9";
        else if(m.equals("十月"))
            initm = "10";
        else if(m.equals("十一月"))
            initm = "11";
        else
            initm = "12";

        Calendar time=Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR,Integer.parseInt(y));
        time.set(Calendar.MONTH,Integer.parseInt(initm)-1);//注意,Calendar对象默认一月为0
        int initnum=time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数


        for(i=0;i<initnum;i++) {
            int num;
            num=i;

            Empty e;
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(Calendar.YEAR, Integer.parseInt(y));
            cal.set(Calendar.MONTH, Integer.parseInt(initm)-1);
            cal.set(Calendar.DAY_OF_MONTH, num);
            int weekno = (int) cal.get(Calendar.DAY_OF_WEEK);

            if(6==weekno||7==weekno){
                e = new Empty(R.drawable.empty2, ""+weekno, String.valueOf(num+1), initm, y);
            } else{
                e = new Empty(R.drawable.empty, ""+weekno, String.valueOf(num+1), initm, y);
            }
            newobject.add(e);
            xin.add(e);
        }
        saveObject("object.dat");
    }

    TextView plus;
    TextView month;
    TextView year;
    TextView scan;
    ImageView ig;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {

                    String returnedDay = data.getStringExtra("day_return");
                    String returnedWeek = data.getStringExtra("week_return");
                    String returnedData = data.getStringExtra("event_return");
                    String returnedMonth = data.getStringExtra("month_return");
                    String returnedYear = data.getStringExtra("year_return");


                    //if(returnedDay != null)
                        //Log.d("hhhh",returnedDay);
                    //以上注释为刚开始出错时用于调出出错位置的代码

                    int num = 0;
                    for(Object i:xin) {
                        if(i instanceof Time) {
                            if (((Time) i).getDay().equals(returnedDay) && ((Time) i).getYear().equals(returnedYear) && ((Time) i).getMonth().equals(returnedMonth)) {
                                if(returnedData.equals(""))
                                {
                                    Empty temp;
                                    xin.remove(i);
                                    if("6".equals(returnedWeek)||"7".equals(returnedWeek))
                                        temp = new Empty(R.drawable.empty2,returnedWeek, returnedDay,returnedMonth,returnedYear);
                                    else
                                        temp = new Empty(R.drawable.empty,returnedWeek, returnedDay,returnedMonth,returnedYear);
                                    xin.add(num, temp);

                                    TimeAdapter adapter = new TimeAdapter(this, xin);
                                    ListView lv = (ListView) findViewById(R.id.lv);//得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
                                    lv.setAdapter(adapter);
                                    break;
                                }else {
                                    ((Time) i).setEvent(returnedData);
                                    //Log.d("hhhh",returnedData);
                                    TimeAdapter adapter = new TimeAdapter(this, xin);
                                    ListView lv = (ListView) findViewById(R.id.lv);//得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
                                    lv.setAdapter(adapter);
                                    break;
                                }
                            }
                        }
                        else {
                            if (((Empty) i).getDay().equals(returnedDay) && ((Empty) i).getMonth().equals(returnedMonth) && ((Empty) i).getYear().equals(returnedYear)) {
                                if (returnedData.equals(""))
                                    break;
                                else {
                                    xin.remove(i);
                                    Time temp = new Time(returnedWeek, returnedDay, returnedData,returnedMonth,returnedYear);
                                    xin.add(num, temp);
                                    TimeAdapter adapter = new TimeAdapter(this, xin);
                                    ListView lv = (ListView) findViewById(R.id.lv);//得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
                                    lv.setAdapter(adapter);
                                    break;
                                }
                        }
                        }
                        num++;
                    }

                    num=0;
                    for(Object i:newobject) {
                        if(i instanceof Time) {
                            if (((Time) i).getDay().equals(returnedDay) && ((Time) i).getYear().equals(returnedYear) && ((Time) i).getMonth().equals(returnedMonth)) {
                                if(returnedData.equals(""))
                                {
                                    Empty temp;
                                    newobject.remove(i);
                                    if("6".equals(returnedWeek)||"7".equals(returnedWeek))
                                        temp = new Empty(R.drawable.empty2,returnedWeek, returnedDay,returnedMonth,returnedYear);
                                    else
                                        temp = new Empty(R.drawable.empty,returnedWeek, returnedDay,returnedMonth,returnedYear);
                                    newobject.add(num, temp);
                                    saveObject("object.dat");
                                    break;
                                }else {
                                    ((Time) i).setEvent(returnedData);
                                    //Log.d("hhhh",returnedData);
                                    saveObject("object.dat");
                                    //数据保存在dat文本中
                                    break;
                                }
                            }
                        }
                        else {
                            if (((Empty) i).getDay().equals(returnedDay) && ((Empty) i).getMonth().equals(returnedMonth) && ((Empty) i).getYear().equals(returnedYear)) {
                                if (returnedData.equals(""))
                                    break;
                                else {
                                    newobject.remove(i);
                                    Time temp = new Time(returnedWeek, returnedDay, returnedData,returnedMonth,returnedYear);
                                    newobject.add(num, temp);
                                    saveObject("object.dat");
                                    //数据保存在dat文本中
                                    break;
                                }
                            }
                        }
                        num++;
                    }

                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        plus = (TextView) findViewById(R.id.text_view6);
        month = (TextView) findViewById(R.id.text_view2);
        year = (TextView) findViewById(R.id.text_view4);
        scan = (TextView) findViewById(R.id.text_view8);
        ig = (ImageView) findViewById(R.id.image_view9);
        //主界面下的选项条中的几个选项

        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LastActivity.class);
                startActivity(intent);
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                String sy = year.getText().toString();
                String sm = month.getText().toString();
                intent.putExtra("show_month",sm);
                intent.putExtra("show_year",sy);
                startActivity(intent);
            }
        });


        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupmonth = new PopupMenu(MainActivity.this, month);
                popupmonth.getMenuInflater().inflate(R.menu.menu_month, popupmonth.getMenu());

                popupmonth.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        xin = new ArrayList<Object>();
                        newobject = (ArrayList<Object>) getObject("object.dat");
                        String y = year.getText().toString();
                        String m = (String) item.getTitle();
                        String mm;
                        if (m.equals("一月"))
                            mm = "1";
                        else if (m.equals("二月"))
                            mm = "2";
                        else if (m.equals("三月"))
                            mm = "3";
                        else if (m.equals("四月"))
                            mm = "4";
                        else if (m.equals("五月"))
                            mm = "5";
                        else if (m.equals("六月"))
                            mm = "6";
                        else if (m.equals("七月"))
                            mm = "7";
                        else if (m.equals("八月"))
                            mm = "8";
                        else if (m.equals("九月"))
                            mm = "9";
                        else if (m.equals("十月"))
                            mm = "10";
                        else if (m.equals("十一月"))
                            mm = "11";
                        else
                            mm = "12";
                        int k = 0;
                        int num = 0;
                        for (Object i : newobject) {
                            if (i instanceof Time) {
                                if (mm.equals(((Time) i).getMonth().toString()) && y.equals(((Time) i).getYear().toString())) {
                                    num++;
                                    xin.add(k, i);
                                    k++;
                                }
                            } else {
                                if (mm.equals(((Empty) i).getMonth().toString()) && y.equals(((Empty) i).getYear().toString())) {
                                    num++;
                                    xin.add(k, i);
                                    k++;
                                }
                            }
                        }
                        if (num == 0)
                            initTimeClick(y, m);
                        month.setText(m);

                        TimeAdapter adapter = new TimeAdapter(MainActivity.this, xin);
                        ListView lv = (ListView) findViewById(R.id.lv);//得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
                        lv.setAdapter(adapter);

                        return true;
                    }
                });
                popupmonth.show();

                saveObject("object.dat");
            }
        });

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupmonth = new PopupMenu(MainActivity.this, year);
                popupmonth.getMenuInflater().inflate(R.menu.menu_year, popupmonth.getMenu());
                popupmonth.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        xin = new ArrayList<Object>();
                        newobject = (ArrayList<Object>) getObject("object.dat");
                        String y = (String) item.getTitle();
                        year.setText(y);
                        String m = month.getText().toString();
                        String mm;
                        if (m.equals("一月"))
                            mm = "1";
                        else if (m.equals("二月"))
                            mm = "2";
                        else if (m.equals("三月"))
                            mm = "3";
                        else if (m.equals("四月"))
                            mm = "4";
                        else if (m.equals("五月"))
                            mm = "5";
                        else if (m.equals("六月"))
                            mm = "6";
                        else if (m.equals("七月"))
                            mm = "7";
                        else if (m.equals("八月"))
                            mm = "8";
                        else if (m.equals("九月"))
                            mm = "9";
                        else if (m.equals("十月"))
                            mm = "10";
                        else if (m.equals("十一月"))
                            mm = "11";
                        else
                            mm = "12";
                        int num = 0;
                        int k = 0;
                        for (Object i : newobject) {
                            if (i instanceof Time) {
                                if (mm.equals(((Time) i).getMonth().toString()) && y.equals(((Time) i).getYear().toString())) {
                                    num++;
                                    xin.add(k, i);
                                    k++;
                                }
                            } else {
                                if (mm.equals(((Empty) i).getMonth().toString()) && y.equals(((Empty) i).getYear().toString())) {
                                    num++;
                                    xin.add(k, i);
                                    k++;
                                }
                            }
                        }
                        if (num == 0)
                            initTimeClick(y, m);

                        TimeAdapter adapter = new TimeAdapter(MainActivity.this, xin);
                        ListView lv = (ListView) findViewById(R.id.lv);//得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
                        lv.setAdapter(adapter);

                        return true;
                    }
                });
                popupmonth.show();
                saveObject("object.dat");
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newobject = (ArrayList<Object>) getObject("object.dat");
                xin = new ArrayList<Object>();

                Calendar cal = Calendar.getInstance();
                int plusy, plusm, plusd, plusw;
                plusy = cal.get(GregorianCalendar.YEAR);
                plusm = cal.get(GregorianCalendar.MONTH);
                plusd = cal.get(GregorianCalendar.DAY_OF_MONTH);
                plusw = cal.get(GregorianCalendar.DAY_OF_WEEK);

                if (plusw == 7)
                    plusw = 1;
                else
                    plusw = plusw - 1;

                String m = Integer.toString(plusm + 1);
                String y = Integer.toString(plusy);
                for (Object i : newobject) {
                    if (i instanceof Time) {
                        if (m.equals(((Time) i).getMonth().toString()) && y.equals(((Time) i).getYear().toString())) {
                            xin.add(i);
                        }
                    } else {
                        if (m.equals(((Empty) i).getMonth().toString()) && y.equals(((Empty) i).getYear().toString())) {
                            xin.add(i);
                        }
                    }
                }

                String yy;
                if (m.equals("1"))
                    yy = "一月";
                else if (m.equals("2"))
                    yy = "二月";
                else if (m.equals("3"))
                    yy = "三月";
                else if (m.equals("4"))
                    yy = "四月";
                else if (m.equals("5"))
                    yy = "五月";
                else if (m.equals("6"))
                    yy = "六月";
                else if (m.equals("7"))
                    yy = "七月";
                else if (m.equals("8"))
                    yy = "八月";
                else if (m.equals("9"))
                    yy = "九月";
                else if (m.equals("10"))
                    yy = "十月";
                else if (m.equals("11"))
                    yy = "十一月";
                else
                    yy = "十二月";

                month.setText(yy);
                year.setText(y);

                ArrayList<Object> dangqian = new ArrayList<Object>();
                for (Object i : newobject) {
                    if (i instanceof Time) {
                        if (Integer.toString(plusm+1).equals(((Time) i).getMonth().toString()) && Integer.toString(plusy).equals(((Time) i).getYear().toString()) && Integer.toString(plusd).equals(((Time) i).getDay().toString())) {
                            dangqian.add(i);
                        }
                    } else {
                        if (Integer.toString(plusm+1).equals(((Empty) i).getMonth().toString()) && Integer.toString(plusy).equals(((Empty) i).getYear().toString()) && Integer.toString(plusd).equals(((Empty) i).getDay().toString())) {
                            dangqian.add(i);
                        }
                    }
                }
                for(Object i:dangqian) {
                    if (i instanceof Empty) {
                        Intent intent = new Intent(MainActivity.this, EditActivity.class);
                        intent.putExtra("empty_year", Integer.toString(plusy));
                        intent.putExtra("empty_month", Integer.toString(plusm + 1));
                        intent.putExtra("empty_week", Integer.toString(plusw));
                        intent.putExtra("empty_day", Integer.toString(plusd));
                        startActivityForResult(intent, 1);
                    } else {
                        Intent intent = new Intent(MainActivity.this, EditActivityForNew.class);
                        intent.putExtra("original_year", Integer.toString(plusy));
                        intent.putExtra("original_month", Integer.toString(plusm + 1));
                        intent.putExtra("original_week", Integer.toString(plusw));
                        intent.putExtra("original_day", Integer.toString(plusd));
                        intent.putExtra("original_data", ((Time)i).getEvent());
                        startActivityForResult(intent, 1);
                    }
                }

            }
        });//设置在主界面下选项条中的加号的响应


        if((ArrayList<Object>)getObject("object.dat") == null) {
            Calendar cal=Calendar.getInstance();
            int plusy,plusm;

            plusy=cal.get(GregorianCalendar.YEAR);
            plusm=cal.get(GregorianCalendar.MONTH);
            String yy;
            if(Integer.toString(plusm+1).equals("1"))
                yy="一月";
            else if(Integer.toString(plusm+1).equals("2"))
                yy="二月";
            else if(Integer.toString(plusm+1).equals("3"))
                yy="三月";
            else if(Integer.toString(plusm+1).equals("4"))
                yy="四月";
            else if(Integer.toString(plusm+1).equals("5"))
                yy="五月";
            else if(Integer.toString(plusm+1).equals("6"))
                yy="六月";
            else if(Integer.toString(plusm+1).equals("7"))
                yy="七月";
            else if(Integer.toString(plusm+1).equals("8"))
                yy="八月";
            else if(Integer.toString(plusm+1).equals("9"))
                yy="九月";
            else if(Integer.toString(plusm+1).equals("10"))
                yy="十月";
            else if(Integer.toString(plusm+1).equals("11"))
                yy="十一月";
            else
                yy="十二月";
            year.setText(Integer.toString(plusy));
            month.setText(yy);
            initTimeN(plusy,plusm);
        }else {
            newobject = (ArrayList<Object>) getObject("object.dat");
            Calendar cal=Calendar.getInstance();
            int plusy,plusm;

            plusy=cal.get(GregorianCalendar.YEAR);
            plusm=cal.get(GregorianCalendar.MONTH);

            String yy;
            if(Integer.toString(plusm+1).equals("1"))
                yy="一月";
            else if(Integer.toString(plusm+1).equals("2"))
                yy="二月";
            else if(Integer.toString(plusm+1).equals("3"))
                yy="三月";
            else if(Integer.toString(plusm+1).equals("4"))
                yy="四月";
            else if(Integer.toString(plusm+1).equals("5"))
                yy="五月";
            else if(Integer.toString(plusm+1).equals("6"))
                yy="六月";
            else if(Integer.toString(plusm+1).equals("7"))
                yy="七月";
            else if(Integer.toString(plusm+1).equals("8"))
                yy="八月";
            else if(Integer.toString(plusm+1).equals("9"))
                yy="九月";
            else if(Integer.toString(plusm+1).equals("10"))
                yy="十月";
            else if(Integer.toString(plusm+1).equals("11"))
                yy="十一月";
            else
                yy="十二月";
            year.setText(Integer.toString(plusy));
            month.setText(yy);
            String y = year.getText().toString();
            String m = month.getText().toString();
            String mm;
            if(m.equals("一月"))
                mm = "1";
            else if(m.equals("二月"))
                mm = "2";
            else if(m.equals("三月"))
                mm = "3";
            else if(m.equals("四月"))
                mm = "4";
            else if(m.equals("五月"))
                mm = "5";
            else if(m.equals("六月"))
                mm = "6";
            else if(m.equals("七月"))
                mm = "7";
            else if(m.equals("八月"))
                mm = "8";
            else if(m.equals("九月"))
                mm = "9";
            else if(m.equals("十月"))
                mm = "10";
            else if(m.equals("十一月"))
                mm = "11";
            else
                mm = "12";
            xin=new ArrayList<Object>();
            for(Object i:newobject)
            {
                if(i instanceof Time){
                    if(mm.equals( ((Time)i).getMonth().toString() ) && y.equals( ((Time)i).getYear().toString() )){
                        xin.add(i);
                    }
                }else{
                    if(mm.equals( ((Empty)i).getMonth().toString() ) && y.equals( ((Empty)i).getYear().toString() )) {
                        xin.add(i);
                    }
                }
            }
        }


        TimeAdapter adapter =new TimeAdapter(this, xin);
        ListView lv = (ListView) findViewById(R.id.lv);//得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
        lv.setAdapter(adapter);

        lv.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (xin.get(position) instanceof Empty) {

                    Empty empty_data = (Empty) xin.get(position);

                    String omonth=empty_data.getMonth();
                    String oyear=empty_data.getYear();

                    String day=empty_data.getDay();
                    String week=empty_data.getWeek();

                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                    intent.putExtra("empty_day",day);
                    intent.putExtra("empty_week",week);

                    intent.putExtra("empty_year",oyear);
                    intent.putExtra("empty_month",omonth);

                    startActivityForResult(intent,1);
                }
                if (xin.get(position) instanceof Time) {
                    Time times_data = (Time)xin.get(position);

                    String omonth=times_data.getMonth();
                    String oyear=times_data.getYear();

                    String event = times_data.getEvent();
                    String day = times_data.getDay();
                    String week = times_data.getWeek();//获得Time Item的相关数据

                    Intent intent = new Intent(MainActivity.this, EditActivityForNew.class);
                    intent.putExtra("original_data",event);
                    intent.putExtra("original_day",day);
                    intent.putExtra("original_week",week);
                    intent.putExtra("original_year",oyear);
                    intent.putExtra("original_month",omonth);

                    startActivityForResult(intent,1);


                }
            }
        }));

    }

}
