package com.crysoft.me.iflora.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crysoft.me.iflora.R;
import com.crysoft.me.iflora.models.ProductsModel;

import java.util.ArrayList;

/**
 * Created by Maxx on 10/11/2017.
 */

public class ProductsAdapter extends BaseAdapter {
    private ArrayList<ProductsModel> products;
    private LayoutInflater mInflater;

    public ProductsAdapter(Context context, ArrayList<ProductsModel> results){
        products = results;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.single_product,null);
      //  ImageView imageView = (ImageView) convertView.findViewById(R.id.productImage);
        TextView productTitle = (TextView) convertView.findViewById(R.id.productTitle);
        TextView pricePerStem = (TextView) convertView.findViewById(R.id.pricePerStem);
       /* TextView color = (TextView) convertView.findViewById(R.id.color);
        TextView stemLength = (TextView) convertView.findViewById(R.id.stemLength);
        TextView stock = (TextView) convertView.findViewById(R.id.availableStock);
        TextView season = (TextView) convertView.findViewById(R.id.season);
        TextView quality = (TextView) convertView.findViewById(R.id.quality);
        TextView packing = (TextView) convertView.findViewById(R.id.packing);
        TextView vendor = (TextView) convertView.findViewById(R.id.grower);
*/
        productTitle.setText(products.get(position).getProductTitle());
        pricePerStem.setText(products.get(position).getPricePerStem());
       /* color.setText(products.get(position).getColor());
        stemLength.setText(products.get(position).getStemLength());
        stock.setText(Integer.toString(products.get(position).getNumberOfStems()));
        season.setText(products.get(position).getSeason());
        quality.setText(products.get(position).getQuality());
        packing.setText(Integer.toString(products.get(position).getStemsPerBox()));
        vendor.setText(products.get(position).getGrowerName());*/
        return convertView;
    }
}
