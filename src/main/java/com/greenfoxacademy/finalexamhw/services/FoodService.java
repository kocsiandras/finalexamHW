package com.greenfoxacademy.finalexamhw.services;

import com.greenfoxacademy.finalexamhw.models.Food;

public interface FoodService {
  String getRandomFood();
  void saveFood(Food food);
}
