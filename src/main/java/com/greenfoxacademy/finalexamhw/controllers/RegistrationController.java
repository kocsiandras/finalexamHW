package com.greenfoxacademy.finalexamhw.controllers;

import com.greenfoxacademy.finalexamhw.dtos.RegistrationDTO;
import com.greenfoxacademy.finalexamhw.models.ResponseError;
import com.greenfoxacademy.finalexamhw.models.Role;
import com.greenfoxacademy.finalexamhw.models.User;
import com.greenfoxacademy.finalexamhw.services.RoleServiceImpl;
import com.greenfoxacademy.finalexamhw.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

  final
  RoleServiceImpl roleService;

  private final String startingMoney = System.getenv("STARTING_MONEY");

  final
  BCryptPasswordEncoder bCryptPasswordEncoder;

  final
  UserServiceImpl userService;

  public RegistrationController(UserServiceImpl userService, BCryptPasswordEncoder bCryptPasswordEncoder, RoleServiceImpl roleService) {
    this.userService = userService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.roleService = roleService;
  }

  @PostMapping(path = "/register")
  public ResponseEntity<?> register(@RequestBody RegistrationDTO registrationDTO) {
    if (registrationDTO.getUsername() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Please give a username"));
    } else if (registrationDTO.getPassword() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Please give a password"));
    } else if (registrationDTO.getPassword() == null && registrationDTO.getUsername() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Please give a password and an username"));
    } else if (userService.existsByUsername(registrationDTO.getUsername())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Username already exists"));
    } else {
      Role role = Role.builder()
          .name("user")
          .build();
      roleService.saveRole(role);
      User user = User.builder()
          .username(registrationDTO.getUsername())
          .password(bCryptPasswordEncoder.encode(registrationDTO.getPassword()))
          .money(Integer.parseInt(startingMoney))
          .build();
      user.addToRoles(role);
      userService.saveUser(user);
      return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
  }
}
