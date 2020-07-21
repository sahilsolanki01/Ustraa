package com.solanki.sahil.assignment.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solanki.sahil.assignment.R;
import com.solanki.sahil.assignment.models.Model_Product;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerAdapter_Product extends RecyclerView.Adapter<RecyclerAdapter_Product.ViewHolder>{

    private List<Model_Product> arrayList;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private boolean mExpanded;


    public RecyclerAdapter_Product(List<Model_Product> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerAdapter_Product.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        View view = layoutInflater.inflate(R.layout.layout_listitem_products, parent, false);
        //view.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter_Product.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        RequestOptions defaultOption = new RequestOptions()
                .error(R.drawable.ic_launcher_background);

        Glide.with(mContext)
                .setDefaultRequestOptions(defaultOption)
                .asBitmap()
                .load(arrayList.get(position).getImage_url())
                .into(holder.image);

        holder.name.setText(arrayList.get(position).getName());
        holder.weight.setText("{"+arrayList.get(position).getWeight()+arrayList.get(position).getWeight_unit()+"}");
        holder.price.setText("\u20B9"+arrayList.get(position).getPrice());
        holder.final_price.setText("\u20B9"+arrayList.get(position).getFinal_price());

        if(arrayList.get(position).getRating() != 0){
            holder.image_star.setVisibility(View.VISIBLE);
            holder.rating.setText(""+arrayList.get(position).getRating());
        }

        if(!arrayList.get(position).isIn_stock()){
            holder.button.setBackground(holder.button.getContext().getResources().getDrawable(R.drawable.roundbutton_out_of_stock));
        }

    }

    @Override
    public int getItemCount() {
        if(arrayList != null && !arrayList.isEmpty()){
            return mExpanded ? arrayList.size() : 3;
        } else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image, image_star;
        TextView name, weight, rating, price, final_price;
        Button button;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView_product_image);
            image_star = itemView.findViewById(R.id.imageView_product_star);
            name = itemView.findViewById(R.id.textView_product_name);
            weight = itemView.findViewById(R.id.textView_product_weight);
            rating = itemView.findViewById(R.id.textView_product_rating);
            price = itemView.findViewById(R.id.textView_product_price);
            final_price = itemView.findViewById(R.id.textView_product_final_price);
            button = itemView.findViewById(R.id.button_product);
        }
    }

    public void setExpanded(boolean expanded) {
        mExpanded = expanded;
        notifyDataSetChanged();
    }
}
