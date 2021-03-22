package com.greenfoxacademy.finalexamhw.controllers;

import com.greenfoxacademy.finalexamhw.dtos.NewFoxDTO;
import com.greenfoxacademy.finalexamhw.dtos.NewFoxName;
import com.greenfoxacademy.finalexamhw.models.*;
import com.greenfoxacademy.finalexamhw.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class FoxController {

  final
  FoodService foodService;

  final
  FoxService foxService;

  final
  UserService userService;

  final
  FoxTypeCharacteristicsService foxTypeCharacteristicsService;

  public FoxController(FoxTypeCharacteristicsService foxTypeCharacteristicsService, UserService userService, FoxService foxService, FoodService foodService) {
    this.foxTypeCharacteristicsService = foxTypeCharacteristicsService;
    this.userService = userService;
    this.foxService = foxService;
    this.foodService = foodService;
  }

  @PostMapping(path = "/fox/newfox")
  public ResponseEntity<?> getNewFox(@RequestBody NewFoxDTO newFoxDTO) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User authenticatedUser = (User) auth.getPrincipal();
    Fox fox = foxService.getNewFox(newFoxDTO);
    if (authenticatedUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } else if (!foxService.isValidType(newFoxDTO.getFoxType())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Not existing type"));
    } else if (authenticatedUser.getMoney() < fox.getFoxPrice()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Not enough money to buy"));
    } else {
      authenticatedUser.getFoxList().add(fox);
      authenticatedUser.setMoney(authenticatedUser.getMoney() - fox.getFoxPrice());
      foxService.saveFox(fox);
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

  @PostMapping(path = "/fox/feed/{foxId}/{foodId}")
  public ResponseEntity<?> feedFox(@PathVariable long foxId, @PathVariable long foodId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User authenticatedUser = (User) auth.getPrincipal();
    if (authenticatedUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } else if (!foxService.existsById(foxId)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Not existing fox"));
    } else if (!foodService.existById(foodId)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Not existing food"));
    } else if (!authenticatedUser.getFoxList().contains(foxService.findById(foxId))) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Not your fox"));
    } else if (!authenticatedUser.getFoodList().contains(foodService.findById(foodId))) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Do not have that food"));
    } else {
      foxService.feed(foxId, foodId, authenticatedUser);
      return ResponseEntity.status(HttpStatus.OK).body(foxService.findById(foxId));
    }
  }

  @PatchMapping(path = "/fox/rename/{id}")
  public ResponseEntity<?> renameFox(@PathVariable long id, @RequestBody NewFoxName newFoxName) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User authenticatedUser = (User) auth.getPrincipal();
    Fox fox = foxService.findById(id);
    if (authenticatedUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } else if (!authenticatedUser.getFoxList().contains(fox)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Not your fox!"));
    } else if (!foxService.existsById(id)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("This fox does not exist"));
    } else {
      fox.setFoxName(newFoxName.getNewFoxName());
      foxService.saveFox(fox);
      return ResponseEntity.status(HttpStatus.OK).body(fox);
    }
  }

  @DeleteMapping(path = "/fox/delete/{id}")
  public ResponseEntity<?> deleteFox(@PathVariable long id) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User authenticatedUser = (User) auth.getPrincipal();
    Fox fox = foxService.findById(id);
    if (authenticatedUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } else if (!foxService.existsById(id)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("This fox does not exist"));
    } else if (!authenticatedUser.getFoxList().contains(fox)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Not your fox!"));
    } else {
      foxService.deleteFox(id);
      return ResponseEntity.status(HttpStatus.OK).build();
    }
  }
}
