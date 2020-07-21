package com.solanki.sahil.assignment.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solanki.sahil.assignment.R;
import com.solanki.sahil.assignment.models.Model_Tab;

import java.util.HashMap;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerAdapter_Tab extends RecyclerView.Adapter<RecyclerAdapter_Tab.ViewHolder> {

    private List<Model_Tab> arrayList;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private OnTabListener mOnTabListener;
    private HashMap<Integer,ViewHolder> holderlist;
    private int selected_position = -1;



    public RecyclerAdapter_Tab(List<Model_Tab> arrayList, Context mContext, OnTabListener onTabListener) {
        this.arrayList = arrayList;
        this.mContext = mContext;
        this.mOnTabListener = onTabListener;
        holderlist = new HashMap<>();
    }

    @NonNull
    @Override
    public RecyclerAdapter_Tab.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        View view = layoutInflater.inflate(R.layout.layout_listitem_tabs, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mOnTabListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapter_Tab.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        RequestOptions defaultOption = new RequestOptions()
                .error(R.drawable.ic_launcher_background);

        Glide.with(mContext)
                .setDefaultRequestOptions(defaultOption)
                .asBitmap()
                .load(arrayList.get(position).getImage_url())
                .into(holder.image);

        holder.name.setText(arrayList.get(position).getName());

        if(selected_position == -1 && position == 0){
            holder.view.setVisibility(View.VISIBLE);
            holder.name.setTextAppearance(R.style.Bold);
        }else if(selected_position == position){
            holder.view.setVisibility(View.VISIBLE);
            holder.name.setTextAppearance(R.style.Bold);
        }else {
            holder.view.setVisibility(View.INVISIBLE);
            holder.name.setTextAppearance(R.style.Normal);
        }

    }

    @Override
    public int getItemCount() {
        if(arrayList != null && !arrayList.isEmpty())
        return arrayList.size();
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView image;
        TextView name;
        View view;
        OnTabListener onTabListener;

        public ViewHolder(View itemView, OnTabListener onTabListener) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.textView);
            view = itemView.findViewById(R.id.tab_line);

            this.onTabListener = onTabListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            onTabListener.onTabClick(getAdapterPosition());
        }
    }

    public interface OnTabListener{
        void onTabClick(int position);
    }


    public void setSelectedPosition(int position) {
        selected_position = position;
        notifyDataSetChanged();
    }
}
