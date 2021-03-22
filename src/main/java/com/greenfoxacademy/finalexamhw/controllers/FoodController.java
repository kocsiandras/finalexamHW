package com.greenfoxacademy.finalexamhw.controllers;

import com.greenfoxacademy.finalexamhw.models.Food;
import com.greenfoxacademy.finalexamhw.models.ResponseError;
import com.greenfoxacademy.finalexamhw.models.User;
import com.greenfoxacademy.finalexamhw.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class  FoodController {

  final
  FoodPriceService foodPriceService;

  final
  UserService userService;

  final
  FoodService foodService;

  public FoodController(FoodService foodService, UserService userService, FoodPriceService foodPriceService) {
    this.foodService = foodService;
    this.userService = userService;
    this.foodPriceService = foodPriceService;
  }

  @GetMapping(path = "/newfood")
  public ResponseEntity<?> buyFood() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User authenticatedUser = (User) auth.getPrincipal();
    int foodPrice = (int) foodPriceService.getActualFoodPrice().getFoodPrice();
    Food food = Food.builder()
        .foodName(foodService.getRandomFood())
        .foodPrice(foodPrice)
        .build();
    if (authenticatedUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } else if (authenticatedUser.getMoney() < food.getFoodPrice()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("not enough money!"));
    } else {
      authenticatedUser.setMoney(authenticatedUser.getMoney() - foodPrice);
      authenticatedUser.getFoodList().add(food);
      foodService.saveFood(food);
      userService.saveUser(authenticatedUser);
      return ResponseEntity.status(HttpStatus.CREATED).body(authenticatedUser.getFoodList());
    }
  }
}
