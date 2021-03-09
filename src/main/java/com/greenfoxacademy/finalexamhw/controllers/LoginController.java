package com.greenfoxacademy.finalexamhw.controllers;

import com.greenfoxacademy.finalexamhw.dtos.LoggedInUserDTO;
import com.greenfoxacademy.finalexamhw.dtos.RegistrationDTO;
import com.greenfoxacademy.finalexamhw.models.ResponseError;
import com.greenfoxacademy.finalexamhw.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  final
  UserServiceImpl userService;

  public LoginController(UserServiceImpl userService) {
    this.userService = userService;
  }

  @PostMapping(path = "/login")
  public ResponseEntity<?> login(@RequestBody RegistrationDTO registrationDTO) {
    if (registrationDTO.getUsername() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Please add a username"));
    } else if (registrationDTO.getPassword() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Please add a password"));
    } else if (registrationDTO.getPassword() == null && registrationDTO.getUsername() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Please add a password and a username"));
    } else if (!userService.existsByUsername(registrationDTO.getUsername())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("This username does not exist"));
    } else {
      LoggedInUserDTO loggedInUserDTO = userService.validateUser(registrationDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(loggedInUserDTO);
    }
  }
}
