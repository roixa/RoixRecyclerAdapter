package com.roix.roixrecycleradapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity implements RoixRecyclerAdapter.EventListener{
    RoixRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycle_view);
        adapter=new RoixRecyclerAdapter(this,this);
        adapter.bindToRecyclerView(recyclerView);

        List<Object> pojos=generate();
        try {
            adapter.addItems( pojos,RecyclerItem.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View view, Object item) {
        ItemPojo pojo=(ItemPojo) item;

        Log.d("MainActivity","onClick "+pojo.getText1());
        //adapter.clearData();

    }

    @Override
    public void onScrolled(int size) {
        Log.d("MainActivity","onScrolled "+size);

        android.os.Handler handler=new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    adapter.addItems(generate(), RecyclerItem2.class);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }

            }
        },1000);
    }

    private int count=0;
    private List<Object> generate(){
        List<Object> list=new ArrayList<>();
        for(int i=0;i<10;i++){

            Log.d("MainActivity","generate "+count);
            ItemPojo pojo=new ItemPojo(i+""," some text "+count+" "+i);
            list.add(pojo);
            count++;
        }
        return list;
    }

    public static class ItemPojo{
        private String text1;
        private String text2;

        public ItemPojo(String text1, String text2) {
            this.text1 = text1;
            this.text2 = text2;
        }

        public String getText1() {
            return text1;
        }

        public String getText2() {
            return text2;
        }
    }
    public static class RecyclerItem extends RoixRecyclerAdapter.ItemView{

        private TextView textView;
        private TextView textView2;
        private Button button;
        private Button button2;

        @Override
        public int getResID() {
            return R.layout.recycler_item;
        }

        @Override
        public void create(View v, View.OnClickListener listener) {

            textView=(TextView)v.findViewById(R.id.textView);
            textView2=(TextView)v.findViewById(R.id.textView2);
            button=(Button)v.findViewById(R.id.button);
            button2=(Button)v.findViewById(R.id.button2);
            button.setOnClickListener(listener);
            button2.setOnClickListener(listener);
        }

        @Override
        public void bind(Object item) {

            ItemPojo pojo=(ItemPojo) item;
            Log.d("MainActivity","bind "+pojo.getText1());

            textView.setText(pojo.getText1());
            textView2.setText(pojo.getText2());
        }

    }
    public static class RecyclerItem2 extends RoixRecyclerAdapter.ItemView{
        private TextView textView;
        private TextView textView2;
        private Button button;
        private Button button2;


        @Override
        public int getResID() {
            return R.layout.recycler_item_2;
        }

        @Override
        public void create(View v, View.OnClickListener listener) {
            textView=(TextView)v.findViewById(R.id.textView3);
            textView2=(TextView)v.findViewById(R.id.textView4);
            button=(Button)v.findViewById(R.id.button3);
            button2=(Button)v.findViewById(R.id.button4);
            button.setOnClickListener(listener);
            button2.setOnClickListener(listener);

        }

        @Override
        public void bind(Object item) {
            ItemPojo pojo=(ItemPojo) item;
            Log.d("MainActivity","bind "+pojo.getText1());

            textView.setText(pojo.getText1());
            textView2.setText(pojo.getText2());

        }
    }
}
