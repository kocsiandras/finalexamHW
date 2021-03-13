package com.greenfoxacademy.finalexamhw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.finalexamhw.models.User;
import com.greenfoxacademy.finalexamhw.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.HashSet;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LoginControllerTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  public void successfulLoginTest() throws Exception {
    User newUser = User.builder()
        .password("pw")
        .username("username")
        .build();
    userRepository.save(newUser);
    mockMvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("username", is("username")))
        .andExpect(jsonPath("token").exists());
  }

  @Test
  public void noPassword() throws Exception {
    User newUser = User.builder()
        .password(null)
        .username("username")
        .build();
    mockMvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("message", is("Please add a password")));
  }
}