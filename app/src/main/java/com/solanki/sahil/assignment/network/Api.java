package com.solanki.sahil.assignment.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("homemenucategories/v1.0.1?device_type=mob")
    Call<ResponseBody> searchTabs();

    @GET("catalog/v1.0.1?category_id")
    Call<ResponseBody> searchProducts(@Query("category_id") int category_id);
}
