package com.solanki.sahil.assignment.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.solanki.sahil.assignment.models.Model_Tab;
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

public class Repository_Tab {
    private static Repository_Tab instance;
    private ArrayList<Model_Tab> dataSet = new ArrayList<>();

    public static Repository_Tab getInstance(){
        if(instance == null){
            instance = new Repository_Tab();
        }
        return instance;
    }

    public interface RepositoryCallback {
        void onSuccess_tab(LiveData<List<Model_Tab>> list);

        //void onError();
    }


    public MutableLiveData<List<Model_Tab>> repoData_tab(final RepositoryCallback callback) {
        final MutableLiveData<List<Model_Tab>> data = new MutableLiveData<>();

        Api api = new RetrofitInstance().getApi();
        Call<ResponseBody> call = api.searchTabs();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();

                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray itemsData = jsonObject.getJSONArray("category_list");
                        for (int i = 0; i < itemsData.length(); i++) {
                            JSONObject item = itemsData.getJSONObject(i);
                            String name = item.getString("category_name");
                            String image = item.getString("category_image");
                            String id = item.getString("category_id");
                            Model_Tab model = new Model_Tab(name, image, id);
                            dataSet.add(model);
                        }

                        data.setValue(dataSet);
                        callback.onSuccess_tab(data);
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
