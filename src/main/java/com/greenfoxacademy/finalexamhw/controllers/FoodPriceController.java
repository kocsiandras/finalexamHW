package com.greenfoxacademy.finalexamhw.controllers;

import com.greenfoxacademy.finalexamhw.models.User;
import com.greenfoxacademy.finalexamhw.services.FoodPriceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodPriceController {

  final
  FoodPriceServiceImpl foodPriceService;

  public FoodPriceController(FoodPriceServiceImpl foodPriceService) {
    this.foodPriceService = foodPriceService;
  }

  @GetMapping(path = "/actfoodprice")
  public ResponseEntity<?> getActualFoodPrice() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User authenticatedUser = (User) auth.getPrincipal();
    if (authenticatedUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } else {
      return ResponseEntity.status(HttpStatus.OK).body(foodPriceService.getActualFoodPrice().getFoodPrice());
    }
  }

  @GetMapping(path = "/foodpricestory")
  public ResponseEntity<?> getFoodPriceStory() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User authenticatedUser = (User) auth.getPrincipal();
    if (authenticatedUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } else {
      return ResponseEntity.status(HttpStatus.OK).body(foodPriceService.getFoodPriceList());
    }
  }
}
