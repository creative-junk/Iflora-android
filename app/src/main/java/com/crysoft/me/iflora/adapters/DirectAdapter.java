package com.crysoft.me.iflora.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crysoft.me.iflora.R;
import com.crysoft.me.iflora.models.ProductsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Maxx on 9/20/2017.
 */

public class DirectAdapter extends RecyclerView.Adapter<DirectAdapter.MyViewHolder> {
    public interface OnItemClickListener{
        void onItemClick(ProductsModel product);
    }
    private Context context;
    private List<ProductsModel> products;
    private OnItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;

        TextView productTitle,pricePerStem,color,stemLength,stock,season,quality,packing,vendor;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.productImage);
            productTitle = (TextView) itemView.findViewById(R.id.productTitle);
            pricePerStem = (TextView) itemView.findViewById(R.id.pricePerStem);
            color = (TextView) itemView.findViewById(R.id.color);
            stemLength = (TextView) itemView.findViewById(R.id.stemLength);
            stock = (TextView) itemView.findViewById(R.id.stock);
            season = (TextView) itemView.findViewById(R.id.season);
            quality = (TextView) itemView.findViewById(R.id.quality);
            packing = (TextView) itemView.findViewById(R.id.packing);
            vendor = (TextView) itemView.findViewById(R.id.grower);

        }
        public void bind(final ProductsModel product, final OnItemClickListener listener){
            productTitle.setText(product.getProductTitle());
            pricePerStem.setText(product.getPricePerStem());
            color.setText(product.getColor());
            stemLength.setText(product.getStemLength());
            stock.setText(Integer.toString(product.getNumberOfStems()));
            season.setText(product.getSeason());
            quality.setText(product.getQuality());
            packing.setText(Integer.toString(product.getStemsPerBox()));
            vendor.setText(product.getGrowerName());

            Picasso.with(context).load(product.getImageURL()).into(imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(product);
                }
            });
        }
    }
    public DirectAdapter(Context mContext, List<ProductsModel> productList,OnItemClickListener listener){
        this.context = mContext;
        this.products = productList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.direct_product,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(products.get(position),listener);

    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
