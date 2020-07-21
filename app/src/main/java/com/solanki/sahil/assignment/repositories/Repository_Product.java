package com.solanki.sahil.assignment.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.solanki.sahil.assignment.models.Model_Product;
import com.solanki.sahil.assignment.network.Api;
import com.solanki.sahil.assignment.network.RetrofitInstance;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Repository_Product {
    private static Repository_Product instance;
    private ArrayList<Model_Product> dataSet = new ArrayList<>();

    public static Repository_Product getInstance(){
        if(instance == null){
            instance = new Repository_Product();
        }
        return instance;
    }

    public interface RepositoryCallback {
        void onSuccess_product(LiveData<List<Model_Product>> list);

        //void onError();
    }


    public MutableLiveData<List<Model_Product>> repoData_product(final Repository_Product.RepositoryCallback callback, int id) {
        final MutableLiveData<List<Model_Product>> data = new MutableLiveData<>();

        Api api = new RetrofitInstance().getApi();
        Call<ResponseBody> call = api.searchProducts(id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        Log.e(TAG, "onResponse: "+result);

                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray itemsData = jsonObject.getJSONArray("products");
                        for (int i = 0; i < itemsData.length(); i++) {
                            JSONObject item = itemsData.getJSONObject(i);
                            String name = item.getString("name");
                            String weight_unit = item.getString("weight_unit");
                            int weight = item.getInt("weight");
                            int price = item.getInt("price");
                            int final_price = item.getInt("final_price");
                            boolean is_in_stock = item.getBoolean("is_in_stock");
                            String image_urls = item.getString("image_urls");
                            JSONObject object = new JSONObject(image_urls);
                            String image = object.getString("x120");
                            float rating = 0f;
                            if (item.has("rating")) {
                                //get Value of video
                                rating = (float) item.optDouble("rating");
                                Log.e(TAG, "onResponse: "+rating);
                            }
                                Log.e(TAG, "onResponse: "+rating);

                            Model_Product model = new Model_Product(name, image, weight, weight_unit, price, final_price, rating, is_in_stock);
                            dataSet.add(model);
                        }

                        data.setValue(dataSet);
                        callback.onSuccess_product(data);
                    } catch (Exception e) {
                        //callback.onError();
                    }
                } else {
                    // callback.onError();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //callback.onError();
            }
        });

        return data;
    }
}
