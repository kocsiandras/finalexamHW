package com.greenfoxacademy.finalexamhw.retrofit;

import com.greenfoxacademy.finalexamhw.dtos.Quote;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface FoodPriceApi {

  @GET("market/v2/get-quotes")
  Call<Quote> getStockChange(@Header("x-rapidapi-key") String apiKey,
                             @Header("x-rapidapi-host") String host,
                             @Query("region") String region,
                             @Query("symbols") String symbol);
}
