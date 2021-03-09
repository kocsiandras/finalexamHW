package com.greenfoxacademy.finalexamhw.controllers;

import com.greenfoxacademy.finalexamhw.models.ResponseError;
import com.greenfoxacademy.finalexamhw.models.User;
import com.greenfoxacademy.finalexamhw.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  final
  UserServiceImpl userService;

  public UserController(UserServiceImpl userService) {
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
}
