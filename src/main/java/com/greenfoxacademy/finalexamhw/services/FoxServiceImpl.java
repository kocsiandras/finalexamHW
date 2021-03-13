package com.greenfoxacademy.finalexamhw.services;

import com.greenfoxacademy.finalexamhw.dtos.NewFoxDTO;
import com.greenfoxacademy.finalexamhw.models.Food;
import com.greenfoxacademy.finalexamhw.models.Fox;
import com.greenfoxacademy.finalexamhw.models.FoxTypeCharacteristics;
import com.greenfoxacademy.finalexamhw.models.User;
import com.greenfoxacademy.finalexamhw.repositories.FoodRepository;
import com.greenfoxacademy.finalexamhw.repositories.FoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FoxServiceImpl implements FoxService {

  final
  FoxTypeCharacteristicsServiceImpl foxTypeCharacteristicsService;

  final
  UserServiceImpl userService;

  final
  FoodRepository foodRepository;

  final
  FoxRepository foxRepository;

  public FoxServiceImpl(FoxRepository foxRepository, FoodRepository foodRepository, UserServiceImpl userService, FoxTypeCharacteristicsServiceImpl foxTypeCharacteristicsService) {
    this.foxRepository = foxRepository;
    this.foodRepository = foodRepository;
    this.userService = userService;
    this.foxTypeCharacteristicsService = foxTypeCharacteristicsService;
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
      fox.setHungerLevel(fox.getHungerLevel() - 1);
    } else {
      fox.setHappinessLevel(fox.getHappinessLevel() + 1);
      fox.setHungerLevel(fox.getHungerLevel() - 1);
    }
    user.getFoodList().remove(food);
    userService.saveUser(user);
    foxRepository.save(fox);
  }

  @Override
  public Fox getNewFox(NewFoxDTO newFoxDTO) {
    FoxTypeCharacteristics foxTypeCharacteristics =
        foxTypeCharacteristicsService.findByFoxType(newFoxDTO.getFoxType());
    Fox fox = Fox.builder()
        .foxPrice(foxTypeCharacteristics.getPrice())
        .favFood(foxTypeCharacteristics.getFavFood())
        .foxName(newFoxDTO.getFoxName())
        .foxType(newFoxDTO.getFoxType())
        .foxPrice(foxTypeCharacteristics.getPrice())
        .status(foxTypeCharacteristics.getStatus())
        .happinessLevel(foxTypeCharacteristics.getStartingHappinessLevel())
        .hungerLevel(10)
        .build();
    return fox;
  }

  @Override
  public void deleteFox(long id) {
    foxRepository.deleteById(id);
  }

  @Scheduled(cron = "0 12 * * * ?")
  public void updateFoxStats() {
    List<Fox> foxes = (List<Fox>) foxRepository.findAll();
    foxes.forEach(fox -> {
      fox.setHungerLevel(fox.getHungerLevel() + 1);
      fox.setHappinessLevel(fox.getHappinessLevel() - 1);
      if (fox.getHungerLevel() >= 15) {
        fox.setStatus("DEAD");
      }
    });
  }
}
