package com.greenfoxacademy.finalexamhw.controllers;

import com.greenfoxacademy.finalexamhw.models.ResponseError;
import com.greenfoxacademy.finalexamhw.models.User;
import com.greenfoxacademy.finalexamhw.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  final
  UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(path = "/user/stats/{id}")
  public ResponseEntity<?> getStats(@PathVariable long id) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User authenticatedUser = (User) auth.getPrincipal();
    User user = userService.findById(id);
    if (authenticatedUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } else if (user == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("User does not exist"));
    } else {
      return ResponseEntity.status(HttpStatus.OK).body(user);
    }
  }

  @DeleteMapping(path = "/user/delete/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable long id) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User authenticatedUser = (User) auth.getPrincipal();
    if (authenticatedUser == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } else if (!userService.existsById(id)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("User does not exist"));
    } else if (!userService.isAdmin(authenticatedUser)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseError("No authority for this"));
    } else {
      userService.deleteUser(id);
      return ResponseEntity.status(HttpStatus.OK).build();
    }
  }
}
