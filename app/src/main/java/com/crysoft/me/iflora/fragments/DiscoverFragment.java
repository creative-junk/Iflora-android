package com.crysoft.me.iflora.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.crysoft.me.iflora.ProductDetails;
import com.crysoft.me.iflora.R;
import com.crysoft.me.iflora.adapters.DirectAdapter;
import com.crysoft.me.iflora.adapters.LocalMainCategoryAdapter;
import com.crysoft.me.iflora.adapters.ProductsAdapter;
import com.crysoft.me.iflora.helpers.PrefManager;
import com.crysoft.me.iflora.models.CategoryModel;
import com.crysoft.me.iflora.models.ProductsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ArrayList<ProductsModel> productList;
    private GridView gridView;
    private GridView mainGridView;
    private RecyclerView recyclerView;
    private RelativeLayout rlLoading;
    private ProductsAdapter productsAdapter;
    private ViewFlipper mViewFlipper;
    private LocalMainCategoryAdapter localMainCategoryAdapter;

    private ArrayList<CategoryModel> categoryList;
    private DirectAdapter directAdapter;

    public DiscoverFragment(){
        //Required Empty Constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainGridView = (GridView) getActivity().findViewById(R.id.mainCategoryGrid);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);

        rlLoading = (RelativeLayout) getActivity().findViewById(R.id.loadingPanel);
        productList = new ArrayList<ProductsModel>();
        mViewFlipper = (ViewFlipper) getActivity().findViewById(R.id.viewFlipper);

        mViewFlipper.setAutoStart(true);
        mViewFlipper.startFlipping();

        categoryList = new ArrayList<>();

        CategoryModel directMarket = new CategoryModel();
        directMarket.setCategoryName("Direct Market");
        directMarket.setCategoryImage(getResources().getString(R.string.imageurl)+"image06.jpg");
        directMarket.setCategoryType("direct");
        categoryList.add(directMarket);

        CategoryModel auctionMarket = new CategoryModel();
        auctionMarket.setCategoryName("Auction Market");
        auctionMarket.setCategoryImage(getResources().getString(R.string.imageurl)+"image07.jpg");
        auctionMarket.setCategoryType("auction");
        categoryList.add(auctionMarket);

        localMainCategoryAdapter = new LocalMainCategoryAdapter(getActivity().getLayoutInflater(),categoryList,getActivity());
        mainGridView.setAdapter(localMainCategoryAdapter);
        productList = new ArrayList<>();


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1,dpToPx(10),true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        new ProductList().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class ProductList extends AsyncTask<Void,Void,Void> {
        InputStream inputStream = null;
        String result = "";
        PrefManager prefManager;
        String token;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            gridView.setVisibility(View.GONE);
            rlLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                URL hp=null;

                productList.clear();

                hp = new URL(getString(R.string.liveurl)+ "/grower/seedlings");
                Log.i("url",hp.toString());
                HttpURLConnection hpCon = (HttpURLConnection) hp.openConnection();
                SharedPreferences preferences = getActivity().getSharedPreferences("iflora",Context.MODE_PRIVATE);
                token = preferences.getString("token","");
                String auth = "Bearer "+ token;
                Log.i("Token",auth);

                hpCon.setRequestProperty("Authorization",auth);
                Log.i("Request",hpCon.toString());
                hpCon.connect();

                int responseCode = hpCon.getResponseCode();
                String errMessage = hpCon.getResponseMessage();

                Log.i("Code",errMessage);

                if (responseCode == 200){
                    inputStream = hpCon.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();

                    String line =null;
                    while((line = bufferedReader.readLine())!=null){
                        stringBuilder.append(line + '\n');
                    }
                    inputStream.close();
                    result = stringBuilder.toString();
                    Log.i("Result", result);
                    //Get Product List data
                    JSONObject productsObject = new JSONObject(result);
                    JSONArray products = productsObject.getJSONArray("seedlings");
                    //JSONArray products = new JSONArray(result);
                    //JSONArray products = new JSONArray(seedlingList.get(0));
                    Log.i("seedlings",products.toString());
                    int length = products.length();
                    Log.i("Length",String.valueOf(length));
                    for (int i=0;i < products.length();i++){
                        JSONObject seedling = products.getJSONObject(i);
                        ProductsModel productDetails = new ProductsModel();
                        productDetails.setProductId(seedling.getString("productId"));
                        productDetails.setProductTitle(seedling.getString("productName"));
                        productDetails.setImageURL(getResources().getString(R.string.imageurl)+seedling.getString("productImage"));
                        productDetails.setPricePerStem(seedling.getString("pricePerStem"));
                        productDetails.setColor(seedling.getString("color"));
                        productDetails.setStemLength(seedling.getString("stemLength"));
                        productDetails.setQuality(seedling.getString("quality"));
                        productDetails.setNumberOfStems(seedling.getInt("stock"));
                        productDetails.setStemsPerBox(seedling.getInt("packing"));
                        productDetails.setGrowerName(seedling.getString("grower"));
                        productDetails.setGrowerId(seedling.getString("growerId"));
                        productList.add(productDetails);
                    }


                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
            gridView.setVisibility(View.VISIBLE);
            rlLoading.setVisibility(View.GONE);

            //list_detail.setVisibility(View.VISIBLE);
            DirectAdapter productAdapter = new DirectAdapter(getActivity(),productList,new DirectAdapter.OnItemClickListener(){

                @Override
                public void onItemClick(ProductsModel product) {

                    Intent i = new Intent(getActivity(), ProductDetails.class);

                    //Push the Parceable Model through the intent
                    i.putExtra("productId", product.getProductId());
                    startActivity(i);
                }
            });
            recyclerView.setAdapter(productAdapter);

            productAdapter.notifyDataSetChanged();
        }
    }

    private int dpToPx(int dp){
        Resources r = getResources();

        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,r.getDisplayMetrics()));
    }


    private class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount=spanCount;
            this.spacing=spacing;
            this.includeEdge=includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge){
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount){
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            }else{
                outRect.left = column * spacing / spanCount;
                outRect.right =spacing - (column + 1) * spacing/ spanCount;
                if (position >= spanCount){
                    outRect.top = spacing;
                }
            }

        }

    }
}
