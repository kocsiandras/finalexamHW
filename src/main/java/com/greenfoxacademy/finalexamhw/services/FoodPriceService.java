package com.greenfoxacademy.finalexamhw.services;

import com.greenfoxacademy.finalexamhw.dtos.StockChange;
import com.greenfoxacademy.finalexamhw.models.FoodPrice;

import java.io.IOException;
import java.util.List;

public interface FoodPriceService {
  StockChange getFoodPriceChange() throws IOException;

  FoodPrice getActualFoodPrice();

  List<FoodPrice> getFoodPriceList();
}
