package com.greenfoxacademy.finalexamhw.services;

import com.greenfoxacademy.finalexamhw.models.Food;
import com.greenfoxacademy.finalexamhw.repositories.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class FoodServiceImpl implements FoodService{

  final
  FoodRepository foodRepository;

  public FoodServiceImpl(FoodRepository foodRepository) {
    this.foodRepository = foodRepository;
  }

  @Override
  public String getRandomFood() {
    List<String> foodList = new ArrayList<>(Arrays.asList("carrot", "rabbitmeat", "burger", "broccoli"));
    Random random = new Random();
    return foodList.get(random.nextInt(foodList.size()));
  }

  @Override
  public void saveFood(Food food) {
    foodRepository.save(food);
  }

  @Override
  public Food findById(long id) {
    return foodRepository.findById(id);
  }

  @Override
  public boolean existById(long id) {
    return foodRepository.existsById(id);
  }
}
