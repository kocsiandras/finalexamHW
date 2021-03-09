package com.greenfoxacademy.finalexamhw.controllers;

import com.greenfoxacademy.finalexamhw.dtos.NewFoxDTO;
import com.greenfoxacademy.finalexamhw.models.*;
import com.greenfoxacademy.finalexamhw.services.FoxServiceImpl;
import com.greenfoxacademy.finalexamhw.services.FoxTypeCharacteristicsServiceImpl;
import com.greenfoxacademy.finalexamhw.services.UserServiceImpl;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class FoxController {

  final
  FoxServiceImpl foxService;

  final
  UserServiceImpl userService;

  final
  FoxTypeCharacteristicsServiceImpl foxTypeCharacteristicsService;

  public FoxController(FoxTypeCharacteristicsServiceImpl foxTypeCharacteristicsService, UserServiceImpl userService, FoxServiceImpl foxService) {
    this.foxTypeCharacteristicsService = foxTypeCharacteristicsService;
    this.userService = userService;
    this.foxService = foxService;
  }

  @PostMapping(path = "/fox/newfox")
  public ResponseEntity<?> getNewFox(@RequestBody NewFoxDTO newFoxDTO) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User authenticatedUser = (User) auth.getPrincipal();
    FoxTypeCharacteristics foxTypeCharacteristics =
        foxTypeCharacteristicsService.findByFoxType(newFoxDTO.getFoxType());
    Fox fox = Fox.builder()
        .foxPrice(foxTypeCharacteristics.getPrice())
        .favFood(foxTypeCharacteristics.getFavFood())
        .favDrink(foxTypeCharacteristics.getFavDrink())
        .foxName(newFoxDTO.getFoxName())
        .foxType(newFoxDTO.getFoxType())
        .foxPrice(foxTypeCharacteristics.getPrice())
        .happinessLevel(foxTypeCharacteristics.getStartingHappinessLevel())
        .hungerLevel(10)
        .thirstLevel(10)
        .build();
    if (authenticatedUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } else if (!foxService.isValidType(newFoxDTO.getFoxType())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Not existing type"));
    } else if (authenticatedUser.getMoney() < fox.getFoxPrice()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Not enough money to buy"));
    } else {
      foxService.saveFox(fox);
      authenticatedUser.getFoxList().add(fox);
      authenticatedUser.setMoney(authenticatedUser.getMoney() - fox.getFoxPrice());
      userService.saveUser(authenticatedUser);
      return ResponseEntity.status(HttpStatus.CREATED).body(fox);
    }
  }

  @GetMapping(path = "/fox/{id}")
  public ResponseEntity<?> getFoxStats(@PathVariable long id) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User authenticatedUser = (User) auth.getPrincipal();
    if (authenticatedUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } else if (!foxService.existsById(id)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Not existing fox"));
    } else {
      return ResponseEntity.status(HttpStatus.OK).body(foxService.findById(id));
    }
  }
}
