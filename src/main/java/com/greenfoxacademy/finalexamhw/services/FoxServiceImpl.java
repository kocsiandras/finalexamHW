package com.greenfoxacademy.finalexamhw.services;

import com.greenfoxacademy.finalexamhw.models.Food;
import com.greenfoxacademy.finalexamhw.models.Fox;
import com.greenfoxacademy.finalexamhw.models.User;
import com.greenfoxacademy.finalexamhw.repositories.FoodRepository;
import com.greenfoxacademy.finalexamhw.repositories.FoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FoxServiceImpl implements FoxService {

  final
  UserServiceImpl userService;

  final
  FoodRepository foodRepository;

  final
  FoxRepository foxRepository;

  public FoxServiceImpl(FoxRepository foxRepository, FoodRepository foodRepository, UserServiceImpl userService) {
    this.foxRepository = foxRepository;
    this.foodRepository = foodRepository;
    this.userService = userService;
  }

  @Override
  public void saveFox(Fox fox) {
    foxRepository.save(fox);
  }

  @Override
  public boolean isValidType(String foxType) {
    List<String> foxTypeList = new ArrayList<>(Arrays.asList("Arcticfox", "Bengalfox", "Redfox"));
    return foxTypeList.contains(foxType);
  }

  @Override
  public boolean existsById(long id) {
    return foxRepository.existsById(id);
  }

  @Override
  public Fox findById(long id) {
    return foxRepository.findById(id);
  }

  @Override
  public void feed(long foxId, long foodId, User user) {
    Fox fox = foxRepository.findById(foxId);
    Food food = foodRepository.findById(foodId);
    if (fox.getFavFood().equals(food.getFoodName())) {
      fox.setHappinessLevel(fox.getHappinessLevel() + 2);
      fox.setHungerLevel(fox.getHungerLevel() + 1);
    } else {
      fox.setHappinessLevel(fox.getHappinessLevel() + 1);
      fox.setHungerLevel(fox.getHungerLevel() + 1);
    }
    user.getFoodList().remove(food);
    userService.saveUser(user);
    foxRepository.save(fox);

  }
}
