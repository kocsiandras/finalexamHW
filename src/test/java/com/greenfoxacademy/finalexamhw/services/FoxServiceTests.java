package com.greenfoxacademy.finalexamhw.services;

import com.greenfoxacademy.finalexamhw.models.Food;
import com.greenfoxacademy.finalexamhw.models.Fox;
import com.greenfoxacademy.finalexamhw.models.User;
import com.greenfoxacademy.finalexamhw.repositories.FoodRepository;
import com.greenfoxacademy.finalexamhw.repositories.FoxRepository;
import com.greenfoxacademy.finalexamhw.repositories.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class FoxServiceTests {

  private final UserRepository userRepository = Mockito.mock(UserRepository.class);
  private final UserServiceImpl userService = Mockito.mock(UserServiceImpl.class);
  private final FoxRepository foxRepository = Mockito.mock(FoxRepository.class);
  private final FoodRepository foodRepository = Mockito.mock(FoodRepository.class);
  private final FoxTypeCharacteristicsServiceImpl foxTypeCharacteristicsService = Mockito.mock(FoxTypeCharacteristicsServiceImpl.class);
  FoxServiceImpl foxService = new FoxServiceImpl(foxRepository, foodRepository, userService,foxTypeCharacteristicsService);

  @Test
  public void feedShouldRemoveFoodFromFoodList() {
    Fox fox = Fox.builder()
        .favFood("burger")
        .build();
    Food food = Food.builder()
        .foodName("burger")
        .build();
    User user = new User();
    user.getFoodList().add(food);
    Mockito.when(foxRepository.findById(fox.getId())).thenReturn(fox);
    Mockito.when(foodRepository.findById(food.getId())).thenReturn(food);
    Mockito.when(userRepository.save(user)).thenReturn(user);
    foxService.feed(fox.getId(), food.getId(), user);
    Assert.assertEquals(0, user.getFoodList().size());
  }

  @Test
  public void feedShouldIncreaseHappinessLevelBy2() {
    Fox fox = Fox.builder()
        .favFood("broccoli")
        .happinessLevel(0)
        .build();
    Food food = Food.builder()
        .foodName("broccoli")
        .build();
    User user = new User();
    user.getFoodList().add(food);
    Mockito.when(foxRepository.findById(fox.getId())).thenReturn(fox);
    Mockito.when(foodRepository.findById(food.getId())).thenReturn(food);
    Mockito.when(userRepository.save(user)).thenReturn(user);
    foxService.feed(fox.getId(), food.getId(), user);
    Assert.assertEquals(2, fox.getHappinessLevel());
  }

  @Test
  public void feedShouldDecreaseHungerLevelBy1() {
    Fox fox = Fox.builder()
        .hungerLevel(5)
        .favFood("burger")
        .build();
    Food food = Food.builder()
        .foodName("broccoli")
        .build();
    User user = new User();
    user.getFoodList().add(food);
    Mockito.when(foxRepository.findById(fox.getId())).thenReturn(fox);
    Mockito.when(foodRepository.findById(food.getId())).thenReturn(food);
    Mockito.when(userRepository.save(user)).thenReturn(user);
    foxService.feed(fox.getId(), food.getId(), user);
    Assert.assertEquals(4, fox.getHungerLevel());
  }
}
