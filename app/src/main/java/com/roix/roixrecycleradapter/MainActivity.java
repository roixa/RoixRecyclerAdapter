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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.addItems(generate(),new RecyclerItem());

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

    @Override
    public void onEvent(int type, Object content, Object item) {
        ItemPojo pojo= (ItemPojo) item;
        Log.d("MainActivity","onEvent "+content+" "+type+" "+pojo.getText1()+" "+pojo.getText2() );

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
    public static class RecyclerItem implements RoixRecyclerAdapter.ItemRenderer<ItemPojo> {

        private TextView textView;
        private TextView textView2;
        private Button button;
        private Button button2;
        private int EVENT_ON_CLICK=1;
        private int EVENT_ON_LONG_CLICK=2;
        @Override
        public int getResID() {
            return R.layout.recycler_item;
        }

        @Override
        public void create(View v, final RoixRecyclerAdapter.ItemEventListener listener) {
            textView=(TextView)v.findViewById(R.id.textView);
            textView2=(TextView)v.findViewById(R.id.textView2);
            button=(Button)v.findViewById(R.id.button);
            button2=(Button)v.findViewById(R.id.button2);
            View.OnClickListener clickListener=new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnEvent(EVENT_ON_CLICK,"EVENT_ON_CLICK");
                }
            };
            button.setOnClickListener(clickListener);
            button2.setOnClickListener(clickListener);
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.OnEvent(EVENT_ON_LONG_CLICK,"EVENT_ON_LONG_CLICK");
                    return false;
                }
            });

        }

        @Override
        public void bind(ItemPojo pojo) {
            Log.d("MainActivity","bind "+pojo.getText1());
            textView.setText(pojo.getText1());
            textView2.setText(pojo.getText2());
        }


    }
}
