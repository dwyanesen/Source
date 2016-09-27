package com.example.michael.anew;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;

/**
 * Created by Michael on 2016/9/19.
 */
public class EditActivityForNew extends Activity {

    private EditText newlyinput;
    private TextView tvd;
    private TextView tvday;
    private TextView tvyear;
    private TextView tvmonth;
    private TextView tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_edit2);

        Intent intent = getIntent();
        String d = intent.getStringExtra("original_data");
        final String w = intent.getStringExtra("original_week");
        final String day = intent.getStringExtra("original_day");

        final String oyear=intent.getStringExtra("original_year");
        final String omonth=intent.getStringExtra("original_month");

        EditText tv=(EditText) findViewById(R.id.edit_main2);

        tvd=(TextView)findViewById(R.id.edit_title_week2);
        tvday=(TextView)findViewById(R.id.edit_title_day2);

        tvyear=(TextView)findViewById(R.id.edit_title_year2);
        tvmonth=(TextView)findViewById(R.id.edit_title_month2);

        tt=(TextView)findViewById(R.id.t2);

        String sss;
        if(w.equals("1"))
            sss="周一";
        else if(w.equals("2"))
            sss="周二";
        else if(w.equals("3"))
            sss="周三";
        else if(w.equals("4"))
            sss="周四";
        else if(w.equals("5"))
            sss="周五";
        else if(w.equals("6"))
            sss="周六";
        else
            sss="周日";

        tv.setText(d);

        if(w.equals("7")||w.equals("6"))
            tvd.setTextColor(Color.RED);
        else
            tvd.setTextColor(Color.BLACK);

        tvday.setTextColor(Color.BLACK);
        tvmonth.setTextColor(Color.BLACK);
        tvyear.setTextColor(Color.BLACK);



        tvd.setText(sss);
        tvday.setText(day+" 日");
        tvyear.setText(oyear);
        tvmonth.setText(omonth+" 月");

       tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar ca = Calendar.getInstance();
                int nian = ca.get(Calendar.YEAR);//获取年份//
                int yue=ca.get(Calendar.MONTH);//获取月份
                int ri=ca.get(Calendar.DATE);//获取日
                int fen=ca.get(Calendar.MINUTE);//分
                int shi=ca.get(Calendar.HOUR_OF_DAY);//小时
                int miao=ca.get(Calendar.SECOND);//秒
                String text=newlyinput.getText().toString();
                String text2=text+" "+Integer.toString(nian)+"年"+Integer.toString(yue+1)+"月"+Integer.toString(ri)+"日"+Integer.toString(shi)+":"+Integer.toString(fen)+":"+Integer.toString(miao);
                newlyinput.setText(text2);
            }
        });

        Button Btn1 = (Button)findViewById(R.id.button_cancle2);//获取按钮资源
        Btn1.setOnClickListener(new Button.OnClickListener() {//创建监听
            public void onClick(View v) {
                finish();
            }
        });


        newlyinput=(EditText) findViewById(R.id.edit_main2);

        Button Btn2 = (Button) findViewById(R.id.button_finish2);
        Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();

                String text1 = newlyinput.getText().toString();

                //String text2 = tvday.getText().toString();
                //String text3 = tvd.getText().toString();

                //String text4 = tvmonth.getText().toString();
                //String text5 = tvyear.getText().toString();

                intent.putExtra("event_return",text1);

                intent.putExtra("day_return",day);
                intent.putExtra("week_return",w);

                intent.putExtra("month_return",omonth);
                intent.putExtra("year_return",oyear);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}
