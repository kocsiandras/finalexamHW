package com.greenfoxacademy.finalexamhw.services;

import com.greenfoxacademy.finalexamhw.dtos.LoggedInUserDTO;
import com.greenfoxacademy.finalexamhw.dtos.RegistrationDTO;
import com.greenfoxacademy.finalexamhw.models.Role;
import com.greenfoxacademy.finalexamhw.models.User;

public interface UserService {
  boolean existsByUsername(String username);
  User saveUser(User user);
  LoggedInUserDTO validateUser(RegistrationDTO registrationDTO);
  User findByUsername(String username);
  User findById(long id);
  boolean existsById(long id);
  void deleteUser (long id);
  boolean isAdmin(User user);
}
