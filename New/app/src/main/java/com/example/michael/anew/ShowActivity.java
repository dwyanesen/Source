package com.example.michael.anew;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by Michael on 2016/9/26.
 */
public class ShowActivity extends Activity {

    private Object getObject(String name) {
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
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    //fis流关闭异常
                    e.printStackTrace();
                }
            }
            if (ois != null) {
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

    TextView tvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Intent intent = getIntent();

        ArrayList<Object> newobject = (ArrayList<Object>) getObject("object.dat");
        final ArrayList<Object> newone = new ArrayList<Object>();

        final String m = intent.getStringExtra("show_month");
        final String s = intent.getStringExtra("show_year");

        tvm = (TextView) findViewById(R.id.tvs);
        tvm.setText(m);

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
                if (mm.equals(((Time) i).getMonth().toString()) && s.equals(((Time) i).getYear().toString())) {
                    num++;
                    newone.add(k, i);
                    k++;
                }
            }
        }
        if(num==0){
            Empty e =new Empty(R.drawable.empty,"空","空",m,s);
            newone.add(e);
        }


        ShowAdapter adapter = new ShowAdapter(this, newone);
        ListView lvs = (ListView) findViewById(R.id.lvs);//得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
        lvs.setAdapter(adapter);

        Button Btn1 = (Button) findViewById(R.id.back);//获取按钮资源
        Btn1.setOnClickListener(new Button.OnClickListener() {//创建监听
            public void onClick(View v) {
                finish();
            }
        });

        tvm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupmonth = new PopupMenu(ShowActivity.this, tvm);
                popupmonth.getMenuInflater().inflate(R.menu.menu_month, popupmonth.getMenu());

                popupmonth.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        ArrayList<Object> newobject = (ArrayList<Object>) getObject("object.dat");
                        ArrayList<Object> newone = new ArrayList<Object>();
                        String m = (String) item.getTitle();

                        tvm.setText(m);

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
                                if (mm.equals(((Time) i).getMonth().toString()) && s.equals(((Time) i).getYear().toString())) {
                                    num++;
                                    newone.add(k, i);
                                    k++;
                                }
                            }
                        }
                        if(num==0){
                            Empty e =new Empty(R.drawable.empty,"空","空",m,s);
                            newone.add(e);
                        }
                        ShowAdapter adapter = new ShowAdapter(ShowActivity.this, newone);
                        ListView lv = (ListView) findViewById(R.id.lvs);//得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
                        lv.setAdapter(adapter);

                        return true;
                    }

                });

                popupmonth.show();
            }
        });
    }
}