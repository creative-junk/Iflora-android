package com.crysoft.me.iflora.viewadapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.crysoft.me.iflora.R;
import com.crysoft.me.iflora.models.ProductsModel;

import java.util.ArrayList;

/**
 * Created by Maxx on 10/6/2017.
 */

public class MarketRecyclerViewAdapter extends RecyclerView.Adapter<MarketRecyclerViewAdapter.DataObjectHolder> {
    private ArrayList<ProductsModel> product;
    Activity main;

    private static MyClickListener myClickListener;
    private Context context;


    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView title;
        TextView pricePerStem;
        TextView rating;
        ImageView fav1,fav2;
        RatingBar ratingBar;

        public DataObjectHolder(View itemView){
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            pricePerStem = (TextView) itemView.findViewById(R.id.price);
            rating = (TextView) itemView.findViewById(R.id.ratingtext);

            /********************RATING BAR ******************************/
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingbar);
            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.parseColor("f8d64e"), PorterDuff.Mode.SRC_ATOP);

            fav1 = (ImageView) itemView.findViewById(R.id.fav1);
            fav2 = (ImageView) itemView.findViewById(R.id.fav2);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }
    public void setOnItemClickListener(MyClickListener myClickListener){
        this.myClickListener = myClickListener;
    }
    public MarketRecyclerViewAdapter(Activity activity, Context context,ArrayList<ProductsModel> product){
        this.main = activity;
        this.context = context;
        this.product = product;
    }
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list,parent,false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        //holder.image.setImageResource(product.get(position).getImageURL());
        holder.title.setText(product.get(position).getProductTitle());
        holder.pricePerStem.setText(product.get(position).getPricePerStem());
        holder.rating.setText(product.get(position).getRating());


    }

    @Override
    public int getItemCount() {
        return 0;
    }


    private static class MyClickListener {
        public void onItemClick(int position,View v){};
    }
}
