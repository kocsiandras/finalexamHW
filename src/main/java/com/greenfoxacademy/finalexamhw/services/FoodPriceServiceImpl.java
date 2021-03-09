package com.greenfoxacademy.finalexamhw.services;

import com.greenfoxacademy.finalexamhw.dtos.Quote;
import com.greenfoxacademy.finalexamhw.dtos.StockChange;
import com.greenfoxacademy.finalexamhw.models.FoodPrice;
import com.greenfoxacademy.finalexamhw.repositories.FoodPriceRepository;
import com.greenfoxacademy.finalexamhw.retrofit.FoodPriceApi;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

@Service
public class FoodPriceServiceImpl implements FoodPriceService {

  final
  FoodPriceRepository foodPriceRepository;

  private final String apiKey = System.getenv("APIKEY");
  private final String baseURL = "https://apidojo-yahoo-finance-v1.p.rapidapi.com";
  private final String host = "apidojo-yahoo-finance-v1.p.rapidapi.com";

  Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(baseURL)
      .addConverterFactory(GsonConverterFactory.create())
      .build();

  FoodPriceApi getPriceChange = retrofit.create(FoodPriceApi.class);

  public FoodPriceServiceImpl(FoodPriceRepository foodPriceRepository) {
    this.foodPriceRepository = foodPriceRepository;
  }


  @Override
  public StockChange getFoodPriceChange() throws IOException {
    Call<Quote> fetch = getPriceChange.getStockChange(
        apiKey,
        host,
        "US",
        "TSN"
    );
    Response<Quote> response = fetch.execute();
    assert response.body() != null;
    return response.body().quoteResponse.getResult().get(0);
  }

  @Override
  public FoodPrice getActualFoodPrice() {
    return foodPriceRepository.findTopByOrderByIdDesc();
  }

  @Override
  public List<FoodPrice> getFoodPriceList() {
    return (List<FoodPrice>) foodPriceRepository.findAll();
  }

  @Scheduled(cron = "0 12 * * * ?")
  public void updateFoodPrice() throws IOException {
    double changePercent = getFoodPriceChange().getRegularMarketChangePercent();
    FoodPrice foodPrice = FoodPrice.builder()
        .foodPrice(getActualFoodPrice().getFoodPrice())
        .build();
    if (changePercent > 0) {
      foodPrice.setFoodPrice((int) (foodPrice.getFoodPrice() * (1 + changePercent / 100)));
      foodPriceRepository.save(foodPrice);
    } else if (changePercent < 0) {
      foodPrice.setFoodPrice((int) (foodPrice.getFoodPrice() * (1 - changePercent / 100)));
      foodPriceRepository.save(foodPrice);
    } else {
      foodPriceRepository.save(foodPrice);
    }
  }
}
