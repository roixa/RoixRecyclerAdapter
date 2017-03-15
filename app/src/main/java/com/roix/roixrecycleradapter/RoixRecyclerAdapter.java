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
    private List<ItemRenderer> itemRenderers;
    private List<Object> items;
    private Context context;
    private EventListener eventListener;


    public RoixRecyclerAdapter(Context context,EventListener eventListener){
        this.context=context;
        this.eventListener=eventListener;
    }


    @Override
    public RoixViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemRenderer itemRenderer=itemRenderers.get(viewType);
        Log.d("RoixRecyclerAdapter","onCreateViewHolder "+viewType);

        View view = LayoutInflater.from(context).inflate(itemRenderer.getResID(), parent, false);

        RoixViewHolder holder=new RoixViewHolder(view,eventListener);
        itemRenderer.create(view,holder);
        holder.setItemRenderer(itemRenderer);
        return holder;


    }

    @Override
    public void onBindViewHolder(RoixViewHolder holder, int position) {
        Log.d("RoixRecyclerAdapter","onBindViewHolder "+position);
        Object item=items.get(position);
        holder.getItemRenderer().bind(item);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public  void addItems(List<Object>list,ItemRenderer itemRenderer )  {
        if(items==null)items=new ArrayList<>();
        if(itemRenderers==null)itemRenderers=new ArrayList<>();
        int startedSize=items.size();
        for(int i=0;i<list.size();i++){
            Log.d("RoixRecyclerAdapter","addItems "+startedSize+" "+i);
            items.add(list.get(i));
            itemRenderers.add(itemRenderer);
        }
        notifyDataSetChanged();
    }

    public void setItems(List<Object>list,ItemRenderer itemRenderer){

    }

    @Override
    public int getItemCount() {
        if(items!=null)
            return items.size();
        return 0;
    }
    public void clearData(){
        if(items!=null)items.clear();
        items=null;
        notifyDataSetChanged();
    }

    public interface EventListener{
        void onEvent(int type,Object content,Object item);
    }

    public interface ItemEventListener{
        void OnEvent(int type,Object content);
    }

    public interface ItemRenderer<T>{
        int getResID();
        void create(View v, ItemEventListener listener);
        void bind(T item);
    }

    public class RoixViewHolder extends RecyclerView.ViewHolder implements ItemEventListener{

        private EventListener listener;
        private ItemRenderer itemRenderer;

        public RoixViewHolder(View view,EventListener listener) {
            super(view);
            this.listener=listener;
        }

        @Override
        public void OnEvent(int type, Object content) {
            listener.onEvent(type,content,items.get(getAdapterPosition()));
        }

        public ItemRenderer getItemRenderer() {
            return itemRenderer;
        }

        public void setItemRenderer(ItemRenderer itemRenderer) {
            this.itemRenderer = itemRenderer;
        }
    }

}
