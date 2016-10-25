package com.example.michael.maplocapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Michael on 2016/10/22.
 */
public class ShowndAdapter extends BaseAdapter {

    private int i=0;

    private static final int type_1=0;

    private static final int view_type=1;//the number of items shown on the ListView

    private Context context;

    private ArrayList<Object> newobject=new ArrayList<Object>();

    public ShowndAdapter(Context context,ArrayList<Object> newobject)
    {
        this.context=context;
        this.newobject=newobject;
    }

    @Override
    public int getItemViewType(int position) {
        return type_1;
    }

    @Override
    public int getViewTypeCount() {
        return view_type;
    }

    @Override
    public int getCount() {
        return newobject.size();
    }

    @Override
    public Object getItem(int position) {
        return newobject.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder1{
        TextView name;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {

        //创建viewHolder变量
        ViewHolder1 holder1 = null;
        //根据position获得View的type
        int type = getItemViewType(position);
        //if(convertView==null) {
        holder1 = new ViewHolder1();
        //根据不同的type 来inflate不同的item layout
        //然后设置不同的tag
        //这里的tag设置是用的资源ID作为Key
        convertView = View.inflate(context, R.layout.enemies_list_item, null);

//        Button bt = (Button)convertView.findViewById(R.id.delete_button_cell);
//        //响应删除事件
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, DelFriActivity.class);
//                Friend c = (Friend)newobject.get(position);
//                intent.putExtra("numberdel",c.getPhonenum());
//                context.startActivity(intent);
//            }
//        });

        holder1.name = (TextView) convertView.findViewById(R.id.name_cell);
        Object o = newobject.get(position);
        //根据不同的type设置数据
        Enemy a = (Enemy) o;
        holder1.name.setText(a.getName());
        return convertView;
    }
}
