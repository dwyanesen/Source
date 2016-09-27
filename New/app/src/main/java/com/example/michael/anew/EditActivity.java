package com.example.michael.anew;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;

/**
 * Created by Michael on 2016/9/19.
 */
public class EditActivity extends Activity {



    private EditText newlyinput;
    private TextView tvd;
    private TextView tvday;

    private TextView tt;

    private TextView tvyear;
    private TextView tvmonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_edit);

        Intent intent = getIntent();

        final String w = intent.getStringExtra("empty_week");
        final String day = intent.getStringExtra("empty_day");


        final String oyear=intent.getStringExtra("empty_year");
        final String omonth=intent.getStringExtra("empty_month");
        //以上代码为接收从MainActivity中传过来的数据

        newlyinput=(EditText) findViewById(R.id.edit_main);

        tvd=(TextView)findViewById(R.id.edit_title_week);
        tvday=(TextView)findViewById(R.id.edit_title_day);


        tt=(TextView)findViewById(R.id.t1);
        tvyear=(TextView)findViewById(R.id.edit_title_year);
        tvmonth=(TextView)findViewById(R.id.edit_title_month);
        //以上代码为获得相对应的id

        String text1 = newlyinput.getText().toString();

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

        newlyinput.setText(text1);


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
        //以上代码为将得到的字符串显示在相对应的位置上

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


        Button Btn1 = (Button)findViewById(R.id.button_cancle);//获取按钮资源
        Btn1.setOnClickListener(new Button.OnClickListener() {//创建监听
            public void onClick(View v) {
                finish();
            }
        });

        newlyinput=(EditText) findViewById(R.id.edit_main);

        Button Btn2 = (Button) findViewById(R.id.button_finish);
        Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();

                String text1 = newlyinput.getText().toString();

                String text2 = day;
                String text3 = w;

                String text4 = omonth;
                String text5 = oyear;

                intent.putExtra("event_return",text1);

                intent.putExtra("day_return",text2);
                intent.putExtra("week_return",text3);
                intent.putExtra("month_return",text4);
                intent.putExtra("year_return",text5);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        }
}
