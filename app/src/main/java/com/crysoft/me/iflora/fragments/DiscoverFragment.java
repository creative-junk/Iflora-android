package com.crysoft.me.iflora.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.crysoft.me.iflora.R;
import com.crysoft.me.iflora.helpers.PrefManager;
import com.crysoft.me.iflora.models.ProductsModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DiscoverFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ArrayList<ProductsModel> productList;
    private GridView gridView;
    private RelativeLayout rlLoading;

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

        gridView = (GridView) getActivity().findViewById(R.id.directMarketGrid);
        rlLoading = (RelativeLayout) getActivity().findViewById(R.id.loadingPanel);
        productList = new ArrayList<ProductsModel>();
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
                    JSONArray productsArray = new JSONArray(result);
                    Log.i("URL", "" + productsArray.toString());

                   /* ProductsModel productDetails = new ProductsModel();
                    productDetails.setProductTitle();
                    productDetails.setImageURL();
                    productDetails.setColor();
                    productDetails.setSeason();
                    productDetails.setQuality();
                    productDetails.setGrowerId();
                    productDetails.setGrowerName();
                    productDetails.setNumberOfStems();
                    productDetails.setPricePerStem();
                    productDetails.setStemsPerBox();*/

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
        }
    }
}
