package com.roix.roixrecycleradapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by roix on 06.03.2017.
 */

public class RoixRecyclerAdapter extends RecyclerView.Adapter<RoixRecyclerAdapter.RoixViewHolder> {
    private List<ItemView> itemTypes;
    private List<Object> items;
    private Context context;
    private EventListener eventListener;


    public RoixRecyclerAdapter(Context context,EventListener eventListener){
        this.context=context;
        this.eventListener=eventListener;
    }
    public void bindToRecyclerView(RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(-1)) {
                    //onScrolledToTop();
                } else if (!recyclerView.canScrollVertically(1)) {
                    eventListener.onScrolled(items.size());
                    //onScrolledToBottom();
                } else if (dy < 0) {
                    //onScrolledUp();
                } else if (dy > 0) {
                    //onScrolledDown();
                }
            }
        });
    }

    @Override
    public RoixViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemView itemView=itemTypes.get(viewType);
        Log.d("RoixRecyclerAdapter","onCreateViewHolder "+viewType);

        View view = LayoutInflater.from(context).inflate(itemView.getResID(), parent, false);

        RoixViewHolder holder=new RoixViewHolder(view,eventListener);
        itemView.create(view,holder);
        holder.setItemView(itemView);
        return holder;


    }

    @Override
    public void onBindViewHolder(RoixViewHolder holder, int position) {
        Log.d("RoixRecyclerAdapter","onBindViewHolder "+position);

        Object item=items.get(position);
        holder.getItemView().bind(item);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public <T  extends ItemView> void addItems(List<Object>list, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        if(items==null)items=new ArrayList<>();
        if(itemTypes==null)itemTypes=new ArrayList<>();
        int startedSize=items.size();
        for(int i=0;i<list.size();i++){
            Log.d("RoixRecyclerAdapter","addItems "+startedSize+" "+i);
            items.add(list.get(i));
            itemTypes.add(clazz.newInstance());

        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(items!=null)
            return items.size();
        return 0;
    }
    public void clearData(){
        items=null;
        notifyDataSetChanged();
    }

    public interface EventListener{
        void onClick(View view,Object item);
        void onScrolled(int size);
    }

    public static abstract class ItemView{
        public abstract int getResID();
        public abstract void create(View v, View.OnClickListener listener);
        public abstract void bind(Object item);
    }

    public class RoixViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private EventListener listener;
        private ItemView itemView;

        public RoixViewHolder(View view,EventListener listener) {
            super(view);
            this.listener=listener;
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v,items.get(getAdapterPosition()));
        }

        public ItemView getItemView() {
            return itemView;
        }

        public void setItemView(ItemView itemView) {
            this.itemView = itemView;
        }
    }

}
