package com.greenfoxacademy.finalexamhw.controllers;

import com.greenfoxacademy.finalexamhw.models.Role;
import com.greenfoxacademy.finalexamhw.models.User;
import com.greenfoxacademy.finalexamhw.repositories.UserRepository;
import com.greenfoxacademy.finalexamhw.services.JWTServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  UserRepository userRepository;

  @Autowired
  JWTServiceImpl jwtService;

  @Test
  public void successfulDeleteUserById() throws Exception {
    User newUser = User.builder()
        .password("pw")
        .username("user")
        .roles(new HashSet<Role>())
        .build();
    Role role = Role.builder()
        .name("admin")
        .build();
    newUser.getRoles().add(role);
    userRepository.save(newUser);
    String token = jwtService.createToken("user");
    mockMvc.perform(delete("/user/delete/" + newUser.getId())
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk());
  }

  @Test
  public void successfulDeleteUserByIdIfNotAdmin() throws Exception {
    User newUser = User.builder()
        .password("pw")
        .username("user")
        .roles(new HashSet<Role>())
        .build();
    Role role = Role.builder()
        .name("user")
        .build();
    newUser.getRoles().add(role);
    userRepository.save(newUser);
    String token = jwtService.createToken("user");
    mockMvc.perform(delete("/user/delete/" + newUser.getId())
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isBadRequest());
  }
}